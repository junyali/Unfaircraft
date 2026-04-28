package io.github.junyali.unfaircraft.mixin.block;

import io.github.junyali.unfaircraft.UnfairCraft;
import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(Block.class)
public abstract class GlassMixin {
	@Unique
	private static Set<Block> unfaircraft$glassBlocks;

	@Unique
	private static Set<Block> unfaircraft$getGlassBlocks() {
		if (unfaircraft$glassBlocks == null) {
			// i hate my life..
			unfaircraft$glassBlocks = Set.of(
					Blocks.GLASS,
					Blocks.WHITE_STAINED_GLASS,
					Blocks.ORANGE_STAINED_GLASS,
					Blocks.MAGENTA_STAINED_GLASS,
					Blocks.LIGHT_BLUE_STAINED_GLASS,
					Blocks.YELLOW_STAINED_GLASS,
					Blocks.LIME_STAINED_GLASS,
					Blocks.PINK_STAINED_GLASS,
					Blocks.GRAY_STAINED_GLASS,
					Blocks.LIGHT_GRAY_STAINED_GLASS,
					Blocks.CYAN_STAINED_GLASS,
					Blocks.PURPLE_STAINED_GLASS,
					Blocks.BLUE_STAINED_GLASS,
					Blocks.BROWN_STAINED_GLASS,
					Blocks.GREEN_STAINED_GLASS,
					Blocks.RED_STAINED_GLASS,
					Blocks.BLACK_STAINED_GLASS,
					Blocks.GLASS_PANE,
					Blocks.WHITE_STAINED_GLASS_PANE,
					Blocks.ORANGE_STAINED_GLASS_PANE,
					Blocks.MAGENTA_STAINED_GLASS_PANE,
					Blocks.LIGHT_BLUE_STAINED_GLASS_PANE,
					Blocks.YELLOW_STAINED_GLASS_PANE,
					Blocks.LIME_STAINED_GLASS_PANE,
					Blocks.PINK_STAINED_GLASS_PANE,
					Blocks.GRAY_STAINED_GLASS_PANE,
					Blocks.LIGHT_GRAY_STAINED_GLASS_PANE,
					Blocks.CYAN_STAINED_GLASS_PANE,
					Blocks.PURPLE_STAINED_GLASS_PANE,
					Blocks.BLUE_STAINED_GLASS_PANE,
					Blocks.BROWN_STAINED_GLASS_PANE,
					Blocks.GREEN_STAINED_GLASS_PANE,
					Blocks.RED_STAINED_GLASS_PANE,
					Blocks.BLACK_STAINED_GLASS_PANE,
					Blocks.TINTED_GLASS
			);
		}
		return unfaircraft$glassBlocks;
	}

	@Inject(
			method = "stepOn",
			at = @At("HEAD")
	)
	private void unfaircraft$glassBreak(Level level, BlockPos pos, BlockState state, Entity entity, CallbackInfo ci) {
		if (unfaircraft$getGlassBlocks().contains(state.getBlock())) {
			if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.glass.enabled())) {
				return;
			}

			if (level.isClientSide()) {
				return;
			}

			if (!(entity instanceof LivingEntity livingEntity)) {
				return;
			}

			boolean isSprinting = livingEntity.isSprinting();
			boolean isLanding = livingEntity.fallDistance > 0.5f;

			if (isSprinting || isLanding) {
				if (level.random.nextFloat() < UnfairCraft.CONFIG.glass.breakChance()) {
					level.destroyBlock(pos, false);
					level.playSound(
							null,
							pos,
							SoundEvents.GLASS_BREAK,
							SoundSource.BLOCKS,
							1.0f,
							1.0f
					);
				}
			}
		}
	}
}
