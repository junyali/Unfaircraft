package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
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
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_CREEPER_MIXIN.get()) {
			return;
		}

		Creeper creeper = (Creeper) (Object) this;

		if (this.swell > 0) {
			float fuseSpeedMultiplier = UnfairCraftConfig.CREEPER_FUSE_SPEED_MULTIPLIER.get().floatValue();
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
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_CREEPER_MIXIN.get()) {
			return originalRadius;
		}

		return originalRadius * UnfairCraftConfig.CREEPER_EXPLOSION_RADIUS_MULTIPLIER.get().floatValue();
	}
}
