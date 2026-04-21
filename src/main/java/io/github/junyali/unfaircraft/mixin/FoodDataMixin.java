package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FoodData.class)
public class FoodDataMixin {
	@Redirect(
			method = "tick",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/food/FoodData;addExhaustion(F)V"
			)
	)
	private void increaseHungerExhaustion(FoodData instance, float exhaustion) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_FOOD_DATA_MIXIN.get()) {
			return;
		}

		float multiplier = UnfairCraftConfig.FOOD_DATA_EXHAUSTION_MULTIPLIER.get().floatValue();
		instance.addExhaustion(exhaustion * multiplier);
	}
}
