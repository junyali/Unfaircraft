package io.github.junyali.unfaircraft.mixin.block;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(ServerPlayerGameMode.class)
public abstract class CaveInMixin {
	@Shadow
	protected ServerLevel level;

	@Final
	@Shadow
	protected ServerPlayer player;

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
			method = "destroyBlock",
			at = @At("RETURN")
	)
	private void unfaircraft$caveIn(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValue()) {
			return;
		}

		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.BLOCK.enabled)) {
			return;
		}

		if (pos.getY() < 32) {
			if (level.getRandom().nextFloat() < UnfairCraftConfig.BLOCK.caveInChance.get().floatValue()) {
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
