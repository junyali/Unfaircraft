package io.github.junyali.unfaircraft.mixin.entity;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerBullet.class)
public abstract class ShulkerBulletMixin {
	@Shadow
	private int flightSteps;

	@Redirect(
			method = "tick",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/entity/projectile/ShulkerBullet;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V"
			)
	)
	private void unfaircraft$modifyBulletMovement(ShulkerBullet instance, Vec3 vec3) {
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.SHULKER.enabled)) {
			instance.setDeltaMovement(vec3);
		} else {
			instance.setDeltaMovement(vec3.scale(UnfairCraftConfig.SHULKER.bulletMovementVector.get().floatValue()));
		}
	}

	@Inject(
			method = "tick",
			at = @At("HEAD")
	)
	private void unfaircraft$extendTracking(CallbackInfo ci) {
		ShulkerBullet self = (ShulkerBullet) (Object) this;
		Entity target = self.getOwner();
		if (target != null && self.distanceTo(target) < UnfairCraftConfig.SHULKER.bulletDistanceTarget.get()) {
			this.flightSteps = 0;
		}
	}
}
