package io.github.junyali.unfaircraft.mixin.world;

import io.github.junyali.unfaircraft.UnfairCraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FarmBlock.class)
public class FarmBlockMixin {
	@Inject(
			method = "randomTick",
			at = @At("HEAD")
	)
	private void revertFarmland(BlockState blockState, ServerLevel level, BlockPos blockPos, RandomSource random, CallbackInfo ci) {
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.farmland.enabled())) {
			return;
		}

		if (random.nextFloat() < UnfairCraft.CONFIG.farmland.revertChance()) {
			level.setBlock(blockPos, Blocks.DIRT.defaultBlockState(), 3);

			BlockPos abovePos = blockPos.above();
			BlockState aboveState = level.getBlockState(abovePos);
			if (aboveState.getBlock() instanceof CropBlock) {
				level.destroyBlock(abovePos, false);
			}
		}
	}
}
