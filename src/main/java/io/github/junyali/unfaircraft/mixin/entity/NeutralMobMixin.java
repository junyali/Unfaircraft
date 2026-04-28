package io.github.junyali.unfaircraft.mixin.entity;

import io.github.junyali.unfaircraft.UnfairCraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NeutralMob.class)
public interface NeutralMobMixin {
	@Inject(
			method = "isAngryAt",
			at = @At("HEAD"),
			cancellable = true
	)
	private void unfaircraft$alwaysAngry(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.mob.hostileRegardlessEnabled())) {
			return;
		}

		if (target instanceof Player player) {
			cir.setReturnValue(true);
		}
	}
}
