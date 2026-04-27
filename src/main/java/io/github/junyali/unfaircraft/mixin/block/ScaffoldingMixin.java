package io.github.junyali.unfaircraft.mixin.block;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.BlockPos;
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
import java.util.Map;

@Mixin(BlockBehaviour.class)
public class ScaffoldingMixin {
	@Unique
	private static final Map<BlockPos, Long> unfaircraft$collapsing = new HashMap<>();

	@Inject(
			method = "entityInside",
			at = @At("HEAD")
	)
	private void unfaircraft$scaffoldingCollapse(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo ci) {
		if (state.getBlock() instanceof ScaffoldingBlock scaffoldingBlock) {
			if (UnfairCraftConfig.isEnabled(UnfairCraftConfig.SCAFFOLDING.enabled)) {
				return;
			}

			if (level.isClientSide()) {
				return;
			}

			if (!(entity instanceof Player player)) {
				return;
			}

			if (!player.onGround()) {
				return;
			}

			long now = level.getGameTime();

			if (!unfaircraft$collapsing.containsKey(pos)) {
				if (level.getRandom().nextFloat() < UnfairCraftConfig.SCAFFOLDING.collapseChance.get().floatValue()) {
					unfaircraft$collapsing.put(pos, now + UnfairCraftConfig.SCAFFOLDING.collapseDelay.get());
					level.levelEvent(
							LevelEvent.PARTICLES_DESTROY_BLOCK,
							pos,
							Block.getId(state)
					);
					return;
				}
			}

			Long collapseAt = unfaircraft$collapsing.get(pos);
			if (now >= collapseAt) {
				unfaircraft$collapsing.remove(pos);
				level.destroyBlock(pos, false);
				BlockPos below = pos.below();
				if (level.getBlockState(below).is(Blocks.SCAFFOLDING)) {
					level.destroyBlock(below, false);
				}
			}
		}
	}
}
