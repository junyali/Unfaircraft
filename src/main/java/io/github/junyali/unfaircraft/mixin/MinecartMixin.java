package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
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
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_MINECART_MIXIN.get()) {
			return velocity;
		}

		double slowdownFactor = UnfairCraftConfig.MINECART_SLOWDOWN_FACTOR.get();
		return velocity.multiply(slowdownFactor, 1.0, slowdownFactor);
	}

	@Inject(
			method = "tick",
			at = @At("TAIL")
	)
	private void onMinecartTick(CallbackInfo ci) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_MINECART_MIXIN.get()) {
			return;
		}

		if (!level().isClientSide && Math.random() < UnfairCraftConfig.MINECART_STOP_CHANCE.get().floatValue()) {
			setDeltaMovement(0, getDeltaMovement().y, 0);
		}

		if (!level().isClientSide && Math.random() < UnfairCraftConfig.MINECART_REVERSE_CHANCE.get().floatValue()) {
			Vec3 motion = getDeltaMovement();
			setDeltaMovement(motion.multiply(-1.0, 1.0, -1.0));
		}
	}
}
