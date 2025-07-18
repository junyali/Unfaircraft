package io.github.junyali.unfaircraft.mixin;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {
	@Unique
	private static final float CRIT_FAIL_CHANCE = 0.25f;

	@Unique
	private static final float SELF_ATTACK_CHANCE = 0.05f;

	@Unique
	private static final float EXHAUSTION_CHANCE = 0.2f;

	@Inject(method = "attack", at = @At("HEAD"), cancellable = true)
	private void beforeAttack(Entity target, CallbackInfo ci) {
		Player player = (Player) (Object) this;

		boolean isCritical = player.getAttackStrengthScale(0.5f) > 0.9f &&
				player.fallDistance > 0.0f &&
				!player.onGround() &&
				!player.onClimbable() &&
				!player.isInWater() &&
				!player.isPassenger();

		if (isCritical) {
			if (player.level().random.nextFloat() < CRIT_FAIL_CHANCE) {
				ci.cancel();
			}
		}

		if (player.level().random.nextFloat() < SELF_ATTACK_CHANCE) {
			float damage = 1.0f + player.level().random.nextFloat() * 2.0f;
			player.hurt(player.level().damageSources().generic(), damage);
			player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_HURT, SoundSource.PLAYERS, 1.0f, 1.0f);
		}

		if (player.level().random.nextFloat() < EXHAUSTION_CHANCE) {
			player.causeFoodExhaustion(4.0f);
		}
	}
}
