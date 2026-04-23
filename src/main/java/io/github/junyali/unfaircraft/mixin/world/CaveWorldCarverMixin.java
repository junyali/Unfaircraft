package io.github.junyali.unfaircraft.mixin.world;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Mixin(CaveWorldCarver.class)
public class CaveWorldCarverMixin {
	private boolean enabled = UnfairCraftConfig.isEnabled(UnfairCraftConfig.CAVE_CARVER.enabled);

	@Unique
	private boolean unfaircraft$hasAdjacentSolidBlock(ChunkAccess chunkAccess, BlockPos blockPos) {
		BlockPos[] adjacentPositions = {
				blockPos.above(),
				blockPos.below(),
				blockPos.north(),
				blockPos.south(),
				blockPos.east(),
				blockPos.west()
		};

		for (BlockPos adjacentPos : adjacentPositions) {
			if (chunkAccess.getBlockState(adjacentPos).isSolid()) {
				return true;
			}
		}

		return false;
	}

	@Inject(
			method = "carve*",
			at = @At("RETURN")
	)
	private void addMoreLava(
			CarvingContext context, CaveCarverConfiguration config, ChunkAccess chunkAccess,
			Function<BlockPos, Holder<Biome>> biomeAccessor, RandomSource random,
			Aquifer aquifer, ChunkPos chunkPos, CarvingMask carvingMask, CallbackInfoReturnable<Boolean> cir
	) {
		if (!enabled) {
			return;
		}

		if (random.nextFloat() < UnfairCraftConfig.CAVE_CARVER.extraLavaPocketChance.get().floatValue()) {
			int x = chunkPos.getMinBlockX() + random.nextInt(16);
			int z = chunkPos.getMinBlockZ() + random.nextInt(16);
			int y = random.nextInt(80) - 50;

			BlockPos lavaPos = new BlockPos(x, y, z);
			if (chunkAccess.getBlockState(lavaPos).isAir() && unfaircraft$hasAdjacentSolidBlock(chunkAccess, lavaPos)) {
				BlockState flowingLava = Blocks.LAVA.defaultBlockState().setValue(LiquidBlock.LEVEL, 0);
				chunkAccess.setBlockState(lavaPos, flowingLava, false);
			}
		}
	}
}
