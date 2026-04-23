package io.github.junyali.unfaircraft.mixin.entity;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Creeper.class)
public class CreeperMixin {
	@Unique
	private static final boolean unfaircraft$enabled = UnfairCraftConfig.isEnabled(UnfairCraftConfig.CREEPER.enabled);

	@Shadow
	private int swell;

	@Shadow
	private int maxSwell;

	@Inject(
			method = "tick",
			at = @At("HEAD")
	)
	private void reduceFusetime(CallbackInfo ci) {
		if (!unfaircraft$enabled) {
			return;
		}

		Creeper creeper = (Creeper) (Object) this;

		if (this.swell > 0) {
			float fuseSpeedMultiplier = UnfairCraftConfig.CREEPER.fuseSpeedMultiplier.get().floatValue();
			int additionalTicks = (int) Math.ceil(fuseSpeedMultiplier - 1.0f);

			this.swell += additionalTicks;

			if (this.swell >= this.maxSwell) {
				this.swell = this.maxSwell;
			}
		}
	}

	@ModifyVariable(
			method = "explodeCreeper",
			at = @At("STORE"),
			ordinal = 0
	)
	private float increaseExplosionRadius(float originalRadius) {
		if (!unfaircraft$enabled) {
			return originalRadius;
		}

		return originalRadius * UnfairCraftConfig.CREEPER.explosionRadiusMultiplier.get().floatValue();
	}
}
