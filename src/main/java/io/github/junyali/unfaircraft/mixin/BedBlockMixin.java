package io.github.junyali.unfaircraft.mixin;

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
	private static final float EXPLOSION_CHANCE = 0.05f;
	private static final float EXPLOSION_RADIUS = 5.0f;

	@Inject(method = "useWithoutItem", at = @At("HEAD"), cancellable = true)
	private void onBedUse(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
		if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
			if (level.random.nextFloat() < EXPLOSION_CHANCE) {
				level.explode(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, EXPLOSION_RADIUS, Level.ExplosionInteraction.BLOCK);
				serverPlayer.hurt(level.damageSources().badRespawnPointExplosion(pos.getCenter()), Float.MAX_VALUE);
				cir.setReturnValue(InteractionResult.SUCCESS);
			}
		}
	}
}
