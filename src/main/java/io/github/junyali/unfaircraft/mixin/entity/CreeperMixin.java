package io.github.junyali.unfaircraft.mixin.entity;

import io.github.junyali.unfaircraft.UnfairCraft;
import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Creeper.class)
public class CreeperMixin {
	@Shadow
	private int swell;

	@Shadow
	private int maxSwell;

	@Inject(
			method = "tick",
			at = @At("HEAD")
	)
	private void reduceFusetime(CallbackInfo ci) {
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.creeper.enabled())) {
			return;
		}

		Creeper creeper = (Creeper) (Object) this;

		if (this.swell > 0) {
			float fuseSpeedMultiplier = (float) UnfairCraft.CONFIG.creeper.fuseSpeedMultiplier();
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
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.creeper.enabled())) {
			return originalRadius;
		}

		return (float) (originalRadius * UnfairCraft.CONFIG.creeper.explosionRadiusMultiplier());
	}
}
