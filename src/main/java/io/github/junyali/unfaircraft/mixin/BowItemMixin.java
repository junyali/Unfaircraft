package io.github.junyali.unfaircraft.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BowItem.class)
public class BowItemMixin {
	@Unique
	private static final float WONKY_PROBABILITY = 0.6f;

	@Unique
	private static final double PROJECTILE_DEVIATION = 0.3;

	@Inject(
			method = "releaseUsing",
			at = @At("TAIL")
	)
	private void afterBowRelease(ItemStack stack, Level level, LivingEntity entity, int timeLeft, CallbackInfo ci) {
		if (!level.isClientSide && entity instanceof Player player) {
			if (level.random.nextFloat() < WONKY_PROBABILITY) {
				level.getEntitiesOfClass(AbstractArrow.class, player.getBoundingBox().inflate(5.0f))
						.stream()
						.filter(arrow -> arrow.getOwner() == player)
						.min((a1, a2) -> Integer.compare(a2.tickCount, a1.tickCount))
						.ifPresent(arrow -> {
							Vec3 currentMotion = arrow.getDeltaMovement();

							double deviation = PROJECTILE_DEVIATION;
							Vec3 wonkyMotion = new Vec3(
									currentMotion.x + (level.random.nextDouble() - 0.5) * deviation,
									currentMotion.y + (level.random.nextDouble() - 0.5) * deviation,
									currentMotion.z + (level.random.nextDouble() - 0.5) * deviation
							);
							arrow.setDeltaMovement(wonkyMotion);
						});
			}
		}
	}
}
