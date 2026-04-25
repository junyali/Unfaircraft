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
			BlockState state = chunkAccess.getBlockState(adjacentPos);
			if (state.isSolid() && !state.is(Blocks.BEDROCK)) {
				return true;
			}
		}

		return false;
	}

	@Unique
	private boolean unfaircraft$isValidLavaPosition(ChunkAccess chunkAccess, BlockPos pos) {
		BlockState state = chunkAccess.getBlockState(pos);
		return (state.isAir() || state.canBeReplaced()) && unfaircraft$hasAdjacentSolidBlock(chunkAccess, pos);
	}

	@Unique
	private void unfaircraft$generateLavaPocket(ChunkAccess chunkAccess, BlockPos centre, RandomSource random, int size) {
		BlockState flowingLava = Blocks.LAVA.defaultBlockState().setValue(LiquidBlock.LEVEL, 0);

		int radiusSquared = size * size;
		for (int dx = -size; dx <= size; dx++) {
			for (int dz = -size; dz <= size; dz++) {
				for (int dy = -size; dy <= size; dy++) {
					int distSquared = dx * dx + dz * dz + dy * dy;

					if (distSquared <= radiusSquared && random.nextFloat() < 0.7f) {
						BlockPos lavaPos = centre.offset(dx, dz, dy);
						if (unfaircraft$isValidLavaPosition(chunkAccess, lavaPos)) {
							chunkAccess.setBlockState(lavaPos, flowingLava, false);
						}
					}
				}
			}
		}
	}

	@Inject(
			method = "carve(Lnet/minecraft/world/level/levelgen/carver/CarvingContext;Lnet/minecraft/world/level/levelgen/carver/CaveCarverConfiguration;Lnet/minecraft/world/level/chunk/ChunkAccess;Ljava/util/function/Function;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/levelgen/Aquifer;Lnet/minecraft/world/level/ChunkPos;Lnet/minecraft/world/level/chunk/CarvingMask;)Z",
			at = @At("RETURN")
	)
	private void addMoreLava(
			CarvingContext context, CaveCarverConfiguration config, ChunkAccess chunkAccess,
			Function<BlockPos, Holder<Biome>> biomeAccessor, RandomSource random,
			Aquifer aquifer, ChunkPos chunkPos, CarvingMask carvingMask, CallbackInfoReturnable<Boolean> cir
	) {
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.CAVE_CARVER.enabled)) {
			return;
		}

		// js some random nums lol
		long chunkSeed = (long) chunkPos.x * 123456789L + (long) chunkPos.z * 987654321;
		RandomSource chunkRandom = RandomSource.create(chunkSeed);

		if (chunkRandom.nextFloat() < UnfairCraftConfig.CAVE_CARVER.extraLavaPocketChance.get().floatValue()) {
			int pocketCount = 1;
			if (random.nextFloat() < 0.3f) pocketCount = 2;
			if (random.nextFloat() < 0.1f) pocketCount = 3;

			for (int i = 0; i < pocketCount; i++) {
				int x = chunkPos.getMinBlockX() + random.nextInt(16);
				int z = chunkPos.getMinBlockZ() + random.nextInt(16);
				int y = random.nextInt(60) - 55;
				if (random.nextFloat() < 0.7f) {
					y = random.nextInt(40) - 50;
				}

				BlockPos centrePos = new BlockPos(x, y, z);

				int size;
				float sizeRoll = random.nextFloat();
				if (sizeRoll < 0.6f) {
					size = 1;
				} else if (sizeRoll < 0.9f) {
					size = 2;
				} else {
					size = 3;
				}

				if (unfaircraft$isValidLavaPosition(chunkAccess, centrePos)) {
					unfaircraft$generateLavaPocket(chunkAccess, centrePos, random, size);
				}
			}
		}
	}
}
