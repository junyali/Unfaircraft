package io.github.junyali.unfaircraft.mixin.food;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FoodData.class)
public class FoodDataMixin {
	private boolean enabled = UnfairCraftConfig.isEnabled(UnfairCraftConfig.FOOD_DATA.enabled);

	@Redirect(
			method = "tick",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/food/FoodData;addExhaustion(F)V"
			)
	)
	private void increaseHungerExhaustion(FoodData instance, float exhaustion) {
		if (!enabled) {
			instance.addExhaustion(exhaustion);
			return;
		}

		float multiplier = UnfairCraftConfig.FOOD_DATA.exhaustionMultiplier.get().floatValue();
		instance.addExhaustion(exhaustion * multiplier);
	}
}
