package io.github.junyali.unfaircraft.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
	private static final double PROJECTILE_DEVIATION = 0.8;

	@Inject(
			method = "releaseUsing",
			at = @At("TAIL")
	)
	private void afterBowRelease(ItemStack stack, Level level, LivingEntity entity, int timeLeft, CallbackInfo ci) {
		if (!level.isClientSide && entity instanceof Player player) {
			if (level.random.nextFloat() < WONKY_PROBABILITY) {
				MinecraftServer server = ((ServerLevel) level).getServer();
				for (int delay = 1; delay <= 5; delay++) {
					final int tickDelay = delay;
					server.tell(new net.minecraft.server.TickTask(tickDelay, () -> {
						level.getEntitiesOfClass(AbstractArrow.class, player.getBoundingBox().inflate(10.0f))
								.stream()
								.filter(arrow -> arrow.getOwner() == player)
								.filter(arrow -> arrow.tickCount < 10)
								.forEach(arrow -> {
									Vec3 currentMotion = arrow.getDeltaMovement();
									Vec3 wonkyMotion = new Vec3(
											currentMotion.x + (level.random.nextDouble() - 0.5) * PROJECTILE_DEVIATION,
											currentMotion.y + (level.random.nextDouble() - 0.5) * PROJECTILE_DEVIATION,
											currentMotion.z + (level.random.nextDouble() - 0.5) * PROJECTILE_DEVIATION
									);
									arrow.setDeltaMovement(wonkyMotion);
									arrow.hasImpulse = true;
									if (tickDelay == 1) {
										level.playSound(null, arrow.getX(), arrow.getY(), arrow.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 0.5f, 0.7f);
									}
								});
					}));
				}
			}
		}
	}
}
