package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BowItem.class)
public class BowItemMixin {
	@Inject(
			method = "releaseUsing",
			at = @At("TAIL")
	)
	private void afterBowRelease(ItemStack stack, Level level, LivingEntity entity, int timeLeft, CallbackInfo ci) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_BOW_ITEM_MIXIN.get()) {
			return;
		}

		if (!level.isClientSide && entity instanceof Player player) {
			if (level.random.nextFloat() < UnfairCraftConfig.BOW_WONKY_CHANCE.get().floatValue()) {
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
											currentMotion.x + (level.random.nextDouble() - 0.5) * UnfairCraftConfig.BOW_PROJECTILE_DEVIATION.get(),
											currentMotion.y + (level.random.nextDouble() - 0.5) * UnfairCraftConfig.BOW_PROJECTILE_DEVIATION.get(),
											currentMotion.z + (level.random.nextDouble() - 0.5) * UnfairCraftConfig.BOW_PROJECTILE_DEVIATION.get()
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

	@Inject(
			method = "releaseUsing",
			at = @At("HEAD"),
			cancellable = true
	)
	private void onBowRelease(ItemStack stack, Level level, LivingEntity entity, int timeLeft, CallbackInfo ci) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_BOW_ITEM_MIXIN.get()) {
			return;
		}

		if (!level.isClientSide && entity instanceof Player player) {
			if (level.random.nextFloat() < UnfairCraftConfig.BOW_MISFIRE_CHANCE.get().floatValue()) {
				ci.cancel();

				if (level.random.nextFloat() < UnfairCraftConfig.BOW_BACKFIRE_CHANCE.get().floatValue()) {
					// maths idk
					float damage = UnfairCraftConfig.BOW_BACKFIRE_DAMAGE_MIN.get().floatValue() + level.random.nextFloat() * (UnfairCraftConfig.BOW_BACKFIRE_DAMAGE_MAX.get().floatValue() - UnfairCraftConfig.BOW_BACKFIRE_DAMAGE_MIN.get().floatValue());

					player.hurt(level.damageSources().generic(), damage);
					level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_HURT, SoundSource.PLAYERS, 1.0f, 1.0f);

					player.invulnerableTime = 0;
				}

				level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0f, 0.8f);
			}
		}
	}
}
