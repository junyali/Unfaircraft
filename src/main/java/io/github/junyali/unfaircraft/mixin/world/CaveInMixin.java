package io.github.junyali.unfaircraft.mixin.world;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(Block.class)
public abstract class CaveInMixin {
	@Unique
	private static final int unfaircraft$scan_height = 16;

	@Unique
	private static Set<Block> unfaircraft$falling;

	@Unique
	private static Set<Block> unfaircraft$getFalling() {
		if (unfaircraft$falling == null) {
			unfaircraft$falling = Set.of(
					Blocks.GRAVEL,
					Blocks.SAND,
					Blocks.RED_SAND,
					Blocks.SUSPICIOUS_GRAVEL,
					Blocks.SUSPICIOUS_SAND
			);
		}
		return unfaircraft$falling;
	}

	@Inject(
			method = "playerDestroy",
			at = @At("TAIL")
	)
	private void unfaircraft$caveIn(Level level, Player player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack tool, CallbackInfo ci) {
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.ORE.enabled)) {
			return;
		}

		if (level.isClientSide()) {
			return;
		}

		if (!(level instanceof ServerLevel serverLevel)) {
			return;
		}

		if (pos.getY() < 32) {
			if (level.getRandom().nextFloat() < UnfairCraftConfig.ORE.caveInChance.get().floatValue()) {
				for (int i = 1; i <= unfaircraft$scan_height; i++) {
					BlockPos above = pos.above(i);
					BlockState aboveState = level.getBlockState(above);
					if (unfaircraft$getFalling().contains(aboveState.getBlock())) {
						level.removeBlock(above, false);
						FallingBlockEntity falling = FallingBlockEntity.fall(level, above, aboveState);
						level.addFreshEntity(falling);
					}
				}
			}
		}
	}
}
