package io.github.junyali.unfaircraft.mixin.block;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mixin(BlockBehaviour.class)
public class ScaffoldingMixin {
	@Unique
	private static final Map<BlockPos, Long> unfaircraft$collapsing = new HashMap<>();

	@Unique
	private static final Set<BlockPos> unfaircraft$checked = new HashSet<>();

	@Inject(
			method = "entityInside",
			at = @At("HEAD")
	)
	private void unfaircraft$scaffoldingCollapse(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo ci) {
		if (state.getBlock() instanceof ScaffoldingBlock scaffoldingBlock) {
			if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.SCAFFOLDING.enabled)) {
				return;
			}

			if (level.isClientSide()) {
				return;
			}

			if (!(entity instanceof Player player)) {
				return;
			}

			long now = level.getGameTime();

			if (unfaircraft$collapsing.containsKey(pos)) {
				Long collapseAt = unfaircraft$collapsing.get(pos);
				if (collapseAt != null && now >= collapseAt) {
					unfaircraft$collapsing.remove(pos);
					unfaircraft$checked.remove(pos);
					level.destroyBlock(pos, false);
					level.playSound(
							null,
							pos,
							SoundEvents.SCAFFOLDING_BREAK,
							SoundSource.BLOCKS,
							1.0f,
							1.0f
					);
					BlockPos below = pos.below();
					if (level.getBlockState(below).is(Blocks.SCAFFOLDING)) {
						unfaircraft$collapsing.put(below, now + 5);
					}
				}
				return;
			}

			if (unfaircraft$checked.contains(pos)) {
				return;
			}

			if (level.getRandom().nextFloat() < UnfairCraftConfig.SCAFFOLDING.collapseChance.get().floatValue()) {
				unfaircraft$collapsing.put(pos, now + UnfairCraftConfig.SCAFFOLDING.collapseDelay.get());
				unfaircraft$checked.add(pos);
				level.levelEvent(
						LevelEvent.PARTICLES_DESTROY_BLOCK,
						pos,
						Block.getId(state)
				);
			} else {
				unfaircraft$checked.add(pos);
			}
		}
	}
}
