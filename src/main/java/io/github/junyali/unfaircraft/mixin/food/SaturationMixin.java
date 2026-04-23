package io.github.junyali.unfaircraft.mixin.food;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.HashMap;
import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class SaturationMixin {
	private boolean enabled = UnfairCraftConfig.isEnabled(UnfairCraftConfig.FOOD.enabled);

	@Unique
	private final Map<ResourceLocation, Integer> unfaircraft$eatCounts = new HashMap<>();

	@ModifyVariable(
			method = "eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/food/FoodProperties;)Lnet/minecraft/world/item/ItemStack;",
			at = @At("HEAD"),
			argsOnly = true,
			ordinal = 0
	)
	private FoodProperties diminishSaturation(FoodProperties foodProperties) {
		if (!enabled) {
			return foodProperties;
		}

		LivingEntity entity = (LivingEntity) (Object) this;
		if (entity.level().isClientSide()) return foodProperties;
		if (!(entity instanceof Player player)) return foodProperties;

		ItemStack stack = player.getUseItem();
		if (stack.isEmpty()) return foodProperties;

		ResourceLocation key = BuiltInRegistries.ITEM.getKey(stack.getItem());
		unfaircraft$eatCounts.merge(key, 1, Integer::sum);
		int count = unfaircraft$eatCounts.get(key);
		float scale = Math.max(0.1f, 1.0f - count * 0.2f);

		return new FoodProperties(
				foodProperties.nutrition(),
				foodProperties.saturation() * scale,
				foodProperties.canAlwaysEat(),
				foodProperties.eatDurationTicks(),
				foodProperties.usingConvertsTo(),
				foodProperties.effects()
		);
	}
}
