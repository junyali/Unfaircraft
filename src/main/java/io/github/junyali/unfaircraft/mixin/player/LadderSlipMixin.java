package io.github.junyali.unfaircraft.mixin.player;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class LadderSlipMixin {
	@Inject(
			method = "tick",
			at = @At("HEAD")
	)
	private void unfaircraft$ladderSlip(CallbackInfo ci) {
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.PLAYER.enabled)) {
			return;
		}

		Player self = (Player) (Object) this;

		if (self.level().isClientSide()) {
			return;
		}

		if (!self.onClimbable()) {
			return;
		}

		if (self.getRandom().nextFloat() < UnfairCraftConfig.PLAYER.ladderSlipChance.get().floatValue()) {
			Vec3 current = self.getDeltaMovement();
			self.setDeltaMovement(current.x, -3.0, current.z);
			self.hurtMarked = true;
		}
	}
}
