package io.github.junyali.unfaircraft.mixin.world;

import io.github.junyali.unfaircraft.UnfairCraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SaplingBlock.class)
public class SaplingBlockMixin {
	@Inject(
			method = "randomTick",
			at = @At("HEAD"),
			cancellable = true
	)
	private void killSapling(BlockState blockState, ServerLevel level, BlockPos blockPos, RandomSource random, CallbackInfo ci) {
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.sapling.enabled())) {
			return;
		}

		if (random.nextFloat() < UnfairCraft.CONFIG.sapling.deathChance()) {
			level.setBlock(blockPos, Blocks.DEAD_BUSH.defaultBlockState(), 3);
			ci.cancel();
		}
	}
}
