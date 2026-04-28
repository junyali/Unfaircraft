package io.github.junyali.unfaircraft.mixin.food;

import io.github.junyali.unfaircraft.UnfairCraft;
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
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.foodData.enabled())) {
			instance.addExhaustion(exhaustion);
			return;
		}

		float multiplier = (float) UnfairCraft.CONFIG.foodData.exhaustionMultiplier();
		instance.addExhaustion(exhaustion * multiplier);
	}
}
