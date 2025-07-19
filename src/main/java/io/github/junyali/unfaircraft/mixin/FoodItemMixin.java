package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class FoodItemMixin {
	@Inject(
			method = "finishUsingItem",
			at = @At("HEAD"),
			cancellable = true
	)
	private void onEatFood(ItemStack stack, Level level, LivingEntity entity, CallbackInfoReturnable<ItemStack> cir) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_FOOD_ITEM_MIXIN.get()) {
			return;
		}

		if (stack.getItem().getFoodProperties(stack, entity) != null && entity instanceof Player player) {
			if (level.random.nextFloat() < UnfairCraftConfig.FOOD_FAIL_CHANCE.get()) {
				if (!level.isClientSide) {
					ItemStack result = stack.copy();
					result.shrink(1);
					cir.setReturnValue(result);
				}
			}
		}
	}
}
