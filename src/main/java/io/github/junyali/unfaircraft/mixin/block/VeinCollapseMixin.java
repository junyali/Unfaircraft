package io.github.junyali.unfaircraft.mixin.block;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

@Mixin(Block.class)
public abstract class VeinCollapseMixin {
	@Inject(
			method = "playerDestroy",
			at = @At("TAIL")
	)
	private void unfaircraft$veinCollapse(Level level, Player player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack tool, CallbackInfo ci) {
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.BLOCK.enabled)) {
			return;
		}

		if (level.isClientSide()) {
			return;
		}

		if (!(level instanceof ServerLevel serverLevel)) {
			return;
		}

		// check if block mined is ore here -->

		if (level.getRandom().nextFloat() < UnfairCraftConfig.BLOCK.veinCollapseChance.get().floatValue()) {
			Queue<BlockPos> queue = new LinkedList<>();
			Set<BlockPos> visited = new HashSet<>();

			queue.add(pos);
			visited.add(pos);
			int destroyed = 0;

			while (!queue.isEmpty() && destroyed < 16) {
				BlockPos current = queue.poll();
				for (Direction direction : Direction.values()) {
					BlockPos neighbour = current.relative(direction);
					if (visited.contains(neighbour)) continue;
					visited.add(neighbour);
					BlockState neighbourState = level.getBlockState(neighbour);


					level.removeBlock(neighbour, false);
					level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, neighbour, Block.getId(neighbourState));
					level.playSound();
					queue.add(neighbour);
					destroyed++;
				}
			}
		}
	}
}
