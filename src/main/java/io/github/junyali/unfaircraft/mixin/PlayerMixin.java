package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {
	@Inject(method = "attack", at = @At("HEAD"), cancellable = true)
	private void beforeAttack(Entity target, CallbackInfo ci) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_PLAYER_MIXIN.get()) {
			return;
		}

		Player player = (Player) (Object) this;

		boolean isCritical = player.getAttackStrengthScale(0.5f) > 0.9f &&
				player.fallDistance > 0.0f &&
				!player.onGround() &&
				!player.onClimbable() &&
				!player.isInWater() &&
				!player.isPassenger();

		if (isCritical) {
			if (player.level().random.nextFloat() < UnfairCraftConfig.PLAYER_CRIT_FAIL_CHANCE.get().floatValue()) {
				ci.cancel();
			}
		}

		if (player.level().random.nextFloat() < UnfairCraftConfig.PLAYER_SELF_ATTACK_CHANCE.get().floatValue()) {
			float damage = 1.0f + player.level().random.nextFloat() * 2.0f;
			player.hurt(player.level().damageSources().generic(), damage);
			player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_HURT, SoundSource.PLAYERS, 1.0f, 1.0f);
		}

		if (player.level().random.nextFloat() < UnfairCraftConfig.PLAYER_ATTACK_EXHAUSTION_CHANCE.get().floatValue()) {
			player.causeFoodExhaustion(4.0f);
		}
	}
}
