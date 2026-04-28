package io.github.junyali.unfaircraft.mixin.block;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

@Mixin(ServerPlayerGameMode.class)
public abstract class VeinCollapseMixin {
	@Shadow
	protected ServerLevel level;

	@Final
	@Shadow
	protected ServerPlayer player;

	@Unique
	private BlockState unfaircraft$veinSavedState;

	@Unique
	private static Set<Block> unfaircraft$ores;

	@Unique
	private static Set<Block> unfaircraft$getOres() {
		if (unfaircraft$ores == null) {
			unfaircraft$ores = Set.of(
					Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE,
					Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE,
					Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE,
					Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE,
					Blocks.REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE,
					Blocks.LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE,
					Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE,
					Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE,
					Blocks.NETHER_GOLD_ORE, Blocks.NETHER_QUARTZ_ORE,
					Blocks.ANCIENT_DEBRIS
			);
		}
		return unfaircraft$ores;
	}

	@Inject(
			method = "destroyBlock",
			at = @At("HEAD")
	)
	private void unfaircraft$captureState(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		unfaircraft$veinSavedState = level.getBlockState(pos);
	}

	@Inject(
			method = "destroyBlock",
			at = @At("RETURN")
	)
	private void unfaircraft$veinCollapse(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValue()) {
			return;
		}

		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.BLOCK.enabled)) {
			return;
		}

		if (!unfaircraft$getOres().contains(unfaircraft$veinSavedState.getBlock())) {
			return;
		}

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
					if (!unfaircraft$getOres().contains(neighbourState.getBlock())) continue;
					level.removeBlock(neighbour, false);
					level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, neighbour, Block.getId(neighbourState));
					level.playSound(null, player.blockPosition(), SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 1.0F, 0.0F);
					queue.add(neighbour);
					destroyed++;
				}
			}
		}
	}
}
