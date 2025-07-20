package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OreFeature.class)
public class OreFeatureMixin {
	@Inject(
			method = "place",
			at = @At("RETURN")
	)
	private void afterOrePlace(FeaturePlaceContext<OreConfiguration> context, CallbackInfoReturnable<Boolean> cir) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_ORE_FEATURE_MIXIN.get()) {
			return;
		}

		if (!cir.getReturnValue()) return;

		BlockPos origin = context.origin();
		WorldGenLevel level = context.level();
		RandomSource random = context.random();

		for (int x = -8; x <= 8; x++) {
			for (int y = -8; y <= 8; y++) {
				for (int z = -8; z <= 8; z++) {
					BlockPos blockPos = origin.offset(x, y, z);
					BlockState blockState = level.getBlockState(blockPos);

					if (blockState.is(Blocks.DIAMOND_ORE)) {
						if (random.nextFloat() < UnfairCraftConfig.ORE_DIAMOND_REPLACEMENT_CHANCE.get().floatValue()) {
							level.setBlock(blockPos, Blocks.DEEPSLATE_COAL_ORE.defaultBlockState(), 2);
						}
					}

					if (blockState.is(Blocks.DEEPSLATE_DIAMOND_ORE)) {
						if (random.nextFloat() < UnfairCraftConfig.ORE_DIAMOND_REPLACEMENT_CHANCE.get().floatValue()) {
							level.setBlock(blockPos, Blocks.DEEPSLATE_COAL_ORE.defaultBlockState(), 2);
						}
					}
				}
			}
		}
	}
}
