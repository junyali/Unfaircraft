package io.github.junyali.unfaircraft.mixin.vehicle;

import io.github.junyali.unfaircraft.UnfairCraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractMinecart.class)
public abstract class MinecartMixin extends Entity {
	public MinecartMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	@ModifyVariable(
			method = "moveAlongTrack",
			at = @At("STORE"),
			ordinal = 0
	)
	private Vec3 modifyMinecartVelocity(Vec3 velocity) {
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.minecart.enabled())) {
			return velocity;
		}

		double slowdownFactor = UnfairCraft.CONFIG.minecart.slowdownFactor();
		return velocity.multiply(slowdownFactor, 1.0, slowdownFactor);
	}

	@Inject(
			method = "tick",
			at = @At("TAIL")
	)
	private void onMinecartTick(CallbackInfo ci) {
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.minecart.enabled())) {
			return;
		}

		if (!level().isClientSide && Math.random() < UnfairCraft.CONFIG.minecart.stopChance()) {
			setDeltaMovement(0, getDeltaMovement().y, 0);
		}

		if (!level().isClientSide && Math.random() < UnfairCraft.CONFIG.minecart.reverseChance()) {
			Vec3 motion = getDeltaMovement();
			setDeltaMovement(motion.multiply(-1.0, 1.0, -1.0));
		}
	}
}
