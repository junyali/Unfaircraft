package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BedBlock.class)
public class BedBlockMixin {
	@Inject(
			method = "useWithoutItem",
			at = @At("HEAD"),
			cancellable = true
	)
	private void onBedUse(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_BED_BLOCK_MIXIN.get()) {
			return;
		}

		if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
			if (level.random.nextFloat() < UnfairCraftConfig.BED_EXPLOSION_CHANCE.get().floatValue()) {
				level.explode(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, UnfairCraftConfig.BED_EXPLOSION_RADIUS.get().floatValue(), Level.ExplosionInteraction.BLOCK);

				if (level.random.nextFloat() < UnfairCraftConfig.BED_FIRE_CHANCE.get().floatValue()) {
					serverPlayer.setRemainingFireTicks(UnfairCraftConfig.BED_FIRE_DURATION.get());
				}

				serverPlayer.hurt(level.damageSources().badRespawnPointExplosion(pos.getCenter()), Float.MAX_VALUE);
				cir.setReturnValue(InteractionResult.SUCCESS);
			}
		}
	}
}
