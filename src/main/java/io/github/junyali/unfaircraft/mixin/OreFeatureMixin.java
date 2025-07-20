package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
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

import java.util.HashMap;
import java.util.Map;

@Mixin(OreFeature.class)
public class OreFeatureMixin {
	@Unique
	private final Map<Block, Block> unfaircraft$oreReplacements = new HashMap<>();

	@Unique
	private void unfaircraft$initReplacements() {
		unfaircraft$oreReplacements.put(Blocks.DIAMOND_ORE, Blocks.COAL_ORE);
		unfaircraft$oreReplacements.put(Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.DEEPSLATE_COAL_ORE);

		unfaircraft$oreReplacements.put(Blocks.IRON_ORE, Blocks.COAL_ORE);
		unfaircraft$oreReplacements.put(Blocks.DEEPSLATE_IRON_ORE, Blocks.DEEPSLATE_COAL_ORE);

		unfaircraft$oreReplacements.put(Blocks.GOLD_ORE, Blocks.COAL_ORE);
		unfaircraft$oreReplacements.put(Blocks.DEEPSLATE_GOLD_ORE, Blocks.DEEPSLATE_COAL_ORE);

		unfaircraft$oreReplacements.put(Blocks.COPPER_ORE, Blocks.COAL_ORE);
		unfaircraft$oreReplacements.put(Blocks.DEEPSLATE_COPPER_ORE, Blocks.DEEPSLATE_COAL_ORE);

		unfaircraft$oreReplacements.put(Blocks.REDSTONE_ORE, Blocks.COAL_ORE);
		unfaircraft$oreReplacements.put(Blocks.DEEPSLATE_REDSTONE_ORE, Blocks.DEEPSLATE_COAL_ORE);

		unfaircraft$oreReplacements.put(Blocks.LAPIS_ORE, Blocks.COAL_ORE);
		unfaircraft$oreReplacements.put(Blocks.DEEPSLATE_LAPIS_ORE, Blocks.DEEPSLATE_COAL_ORE);

		unfaircraft$oreReplacements.put(Blocks.EMERALD_ORE, Blocks.MOSS_BLOCK);
		unfaircraft$oreReplacements.put(Blocks.DEEPSLATE_EMERALD_ORE, Blocks.MOSS_BLOCK);

		unfaircraft$oreReplacements.put(Blocks.ANCIENT_DEBRIS, Blocks.MAGMA_BLOCK);
	}

	@Unique
	private float unfaircraft$getReplacementChance(Block block) {
		if (block == Blocks.ANCIENT_DEBRIS) {
			return UnfairCraftConfig.ORE_ANCIENT_DEBRIS_REPLACEMENT_CHANCE.get().floatValue();
		}
		else if (block == Blocks.DIAMOND_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE) {
			return UnfairCraftConfig.ORE_DIAMOND_REPLACEMENT_CHANCE.get().floatValue();
		}
		else if (block== Blocks.EMERALD_ORE || block == Blocks.DEEPSLATE_EMERALD_ORE) {
			return UnfairCraftConfig.ORE_EMERALD_REPLACEMENT_CHANCE.get().floatValue();
		}
		else if (block == Blocks.GOLD_ORE || block == Blocks.DEEPSLATE_GOLD_ORE) {
			return UnfairCraftConfig.ORE_GOLD_REPLACEMENT_CHANCE.get().floatValue();
		}
		else {
			return UnfairCraftConfig.ORE_DEFAULT_REPLACEMENT_CHANCE.get().floatValue();
		}
	}

	@Inject(
			method = "place",
			at = @At("RETURN")
	)
	private void afterOrePlace(FeaturePlaceContext<OreConfiguration> context, CallbackInfoReturnable<Boolean> cir) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_ORE_FEATURE_MIXIN.get()) {
			return;
		}

		if (!cir.getReturnValue()) return;

		if (unfaircraft$oreReplacements.isEmpty()) {
			unfaircraft$initReplacements();
		}

		BlockPos origin = context.origin();
		WorldGenLevel level = context.level();
		RandomSource random = context.random();

		int searchRadius = 8;
		for (int x = -searchRadius; x <= searchRadius; x++) {
			for (int y = -searchRadius; y <= searchRadius; y++) {
				for (int z = -searchRadius; z <= searchRadius; z++) {
					BlockPos blockPos = origin.offset(x, y, z);
					BlockState blockState = level.getBlockState(blockPos);
					Block block = blockState.getBlock();

					if (unfaircraft$oreReplacements.containsKey(block)) {
						float replacementChance = unfaircraft$getReplacementChance(block);

						if (random.nextFloat() < replacementChance) {
							Block replacementBlock = unfaircraft$oreReplacements.get(block);
							level.setBlock(blockPos, replacementBlock.defaultBlockState(), 2);
						}
					}
				}
			}
		}
	}
}
