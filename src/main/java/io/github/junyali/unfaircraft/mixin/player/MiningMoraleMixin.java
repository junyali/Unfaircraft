package io.github.junyali.unfaircraft.mixin.player;

import io.github.junyali.unfaircraft.UnfairCraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class MiningMoraleMixin {
	@Unique
	private int unfaircraft$fatigueTimer = 0;

	@Inject(
			method = "tick",
			at = @At("TAIL")
	)
	private void unfaircraft$miningFatigueWave(CallbackInfo ci) {
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.player.enabled())) {
			return;
		}

		Player self = (Player) (Object) this;

		if (self.level().isClientSide()) {
			return;
		}

		if (self.getY() >= 32) {
			return;
		}

		if (!self.level().dimensionType().natural()) {
			return;
		}

		unfaircraft$fatigueTimer--;
		if (unfaircraft$fatigueTimer > 0) {
			return;
		}

		unfaircraft$fatigueTimer = 600 + self.getRandom().nextInt(1800);
		if (self.hasEffect(MobEffects.DIG_SLOWDOWN)) {
			return;
		}

		if (self.getRandom().nextFloat() < UnfairCraft.CONFIG.player.lowMiningMoraleChance()) {
			self.addEffect(
					new MobEffectInstance(
							MobEffects.DIG_SLOWDOWN,
							30 + self.getRandom().nextInt(30),
							0
					)
			);
			self.playSound(SoundEvents.PLAYER_HURT_DROWN);
		}
	}
}
