package io.github.junyali.unfaircraft.mixin.block;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerGameMode.class)
public abstract class CaveInMixin {
	@Shadow
	protected ServerLevel level;

	@Final
	@Shadow
	protected ServerPlayer player;

	@Unique
	private static final int unfaircraft$scan_height = 16;

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
				int radius = 3;
				for (int x = -radius; x <= radius; x++) {
					for (int z = -radius; z <= radius; z++) {
						for (int y = 1; y <= unfaircraft$scan_height; y++) {
							BlockPos target = pos.offset(x, y, z);
							BlockState state = level.getBlockState(target);
							if (state.isAir() || !state.getFluidState().isEmpty()) continue;
							if (state.getDestroySpeed(level, target) < 0) continue;
							level.removeBlock(target, false);
							FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(level, target, state);
							level.addFreshEntity(fallingBlockEntity);
						}
					}
				}
			}
		}
	}
}
