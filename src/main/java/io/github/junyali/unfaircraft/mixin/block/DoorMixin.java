package io.github.junyali.unfaircraft.mixin.block;

import io.github.junyali.unfaircraft.UnfairCraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DoorBlock.class)
public abstract class DoorMixin {
	@Inject(
			method = "useWithoutItem",
			at = @At("HEAD"),
			cancellable = true
	)
	private void unfaircraft$useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.door.enabled())) {
			return;
		}

		if (level.isClientSide()) {
			return;
		}

		if (level.getRandom().nextFloat() < UnfairCraft.CONFIG.door.jamChance()) {
			cir.setReturnValue(InteractionResult.CONSUME);
		}
	}
}
