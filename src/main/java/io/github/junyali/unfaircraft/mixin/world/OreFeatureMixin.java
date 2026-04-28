package io.github.junyali.unfaircraft.mixin.world;

import io.github.junyali.unfaircraft.UnfairCraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OreFeature.class)
public class OreFeatureMixin {
	@Unique
	private static Block unfaircraft$getReplacement(Block block) {
		if (block == Blocks.DIAMOND_ORE) return Blocks.COAL_ORE;
		if (block == Blocks.DEEPSLATE_DIAMOND_ORE) return Blocks.DEEPSLATE_COAL_ORE;

		if (block == Blocks.IRON_ORE) return Blocks.COAL_ORE;
		if (block == Blocks.DEEPSLATE_IRON_ORE) return Blocks.IRON_ORE;

		if (block == Blocks.GOLD_ORE) return Blocks.COAL_ORE;
		if (block == Blocks.DEEPSLATE_GOLD_ORE) return Blocks.DEEPSLATE_COAL_ORE;

		if (block == Blocks.COPPER_ORE) return Blocks.COAL_ORE;
		if (block == Blocks.DEEPSLATE_COPPER_ORE) return Blocks.DEEPSLATE_COAL_ORE;

		if (block == Blocks.REDSTONE_ORE) return Blocks.COAL_ORE;
		if (block == Blocks.DEEPSLATE_REDSTONE_ORE) return Blocks.DEEPSLATE_COAL_ORE;


		if (block == Blocks.LAPIS_ORE) return Blocks.COAL_ORE;
		if (block == Blocks.DEEPSLATE_LAPIS_ORE) return Blocks.DEEPSLATE_COAL_ORE;

		if (block == Blocks.EMERALD_ORE) return Blocks.COAL_ORE;
		if (block == Blocks.DEEPSLATE_EMERALD_ORE) return Blocks.DEEPSLATE_COAL_ORE;

		if (block == Blocks.ANCIENT_DEBRIS) return Blocks.MAGMA_BLOCK;

		return null;
	}

	@Unique
	private float unfaircraft$getReplacementChance(Block block) {
		if (block == Blocks.ANCIENT_DEBRIS) {
			return (float) UnfairCraft.CONFIG.ore.ancientDebrisReplacementChance();
		}
		else if (block == Blocks.DIAMOND_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE) {
			return (float) UnfairCraft.CONFIG.ore.diamondReplacementChance();
		}
		else if (block== Blocks.EMERALD_ORE || block == Blocks.DEEPSLATE_EMERALD_ORE) {
			return (float) UnfairCraft.CONFIG.ore.emeraldReplacementChance();
		}
		else if (block == Blocks.GOLD_ORE || block == Blocks.DEEPSLATE_GOLD_ORE) {
			return (float) UnfairCraft.CONFIG.ore.goldReplacementChance();
		}
		else {
			return (float) UnfairCraft.CONFIG.ore.defaultReplacementChance();
		}
	}

	@Inject(
			method = "place",
			at = @At("RETURN")
	)
	private void afterOrePlace(FeaturePlaceContext<OreConfiguration> context, CallbackInfoReturnable<Boolean> cir) {
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.ore.enabled())) {
			return;
		}

		if (!cir.getReturnValue()) return;

		BlockPos origin = context.origin();
		WorldGenLevel level = context.level();
		RandomSource random = context.random();

		int searchRadius = 4;
		for (int x = -searchRadius; x <= searchRadius; x++) {
			for (int y = -searchRadius; y <= searchRadius; y++) {
				for (int z = -searchRadius; z <= searchRadius; z++) {
					BlockPos blockPos = origin.offset(x, y, z);
					BlockState blockState = level.getBlockState(blockPos);
					Block block = blockState.getBlock();

					Block replacement = unfaircraft$getReplacement(block);
					if (replacement != null) {
						float replacementChance = unfaircraft$getReplacementChance(block);

						if (random.nextFloat() < replacementChance) {
							level.setBlock(blockPos, replacement.defaultBlockState(), 2);
						}
					}
				}
			}
		}
	}
}
