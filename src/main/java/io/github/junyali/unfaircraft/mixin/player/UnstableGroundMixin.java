package io.github.junyali.unfaircraft.mixin.player;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class UnstableGroundMixin {
	@Inject(
			method = "tick",
			at = @At("TAIL")
	)
	private void unfaircraft$unstableGround(CallbackInfo ci) {
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.PLAYER.enabled)) {
			return;
		}

		Player self = (Player) (Object) this;
		if (self.level().isClientSide()) {
			return;
		}

		if (!self.onGround()) {
			return;
		}

		double horizontalSpeed = self.getDeltaMovement().horizontalDistanceSqr();
		if (horizontalSpeed < 0.0001) {
			return;
		}

		if (self.getRandom().nextFloat() < UnfairCraftConfig.PLAYER.groundBreakChance.get().floatValue()) {
			BlockPos below = self.blockPosition().below();
			BlockState state = self.level().getBlockState(below);

			if (state.isAir()) {
				return;
			}

			if (state.getFluidState().isEmpty()) {
				return;
			}

			if (state.getDestroySpeed(self.level(), below) < 0) {
				return;
			}

			self.level().destroyBlock(below, true);
		}
	}
}
