package io.github.junyali.unfaircraft.mixin.world;

import io.github.junyali.unfaircraft.UnfairCraft;
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
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.sapling.enabled())) {
			return;
		}

		if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
			if (level.random.nextFloat() < UnfairCraft.CONFIG.bed.explosionChance()) {
				level.explode(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, (float) UnfairCraft.CONFIG.bed.explosionRadius(), Level.ExplosionInteraction.BLOCK);

				if (level.random.nextFloat() < UnfairCraft.CONFIG.bed.fireChance()) {
					serverPlayer.setRemainingFireTicks(UnfairCraft.CONFIG.bed.fireDuration());
				}

				serverPlayer.hurt(level.damageSources().badRespawnPointExplosion(pos.getCenter()), Float.MAX_VALUE);
				cir.setReturnValue(InteractionResult.SUCCESS);
			}
		}
	}
}
