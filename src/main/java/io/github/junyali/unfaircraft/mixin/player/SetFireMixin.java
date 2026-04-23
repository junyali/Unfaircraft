package io.github.junyali.unfaircraft.mixin.player;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class SetFireMixin {
	@Inject(
			method = "tick",
			at = @At("TAIL")
	)
	private void setFire(CallbackInfo info) {
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.TOTEM.enabled)) {
			return;
		}

		LivingEntity entity = (LivingEntity) (Object) this;

		if (!(entity instanceof Player player)) {
			return;
		}

		if (player.level().isClientSide() || player.isOnFire() || player.isSpectator() || player.isCreative()) {
			return;
		}

		int radius = UnfairCraftConfig.SET_FIRE.radius.get();
		BlockPos playerPos = player.blockPosition();

		for (BlockPos pos: BlockPos.betweenClosed(playerPos.offset(-radius, -radius, -radius), playerPos.offset(radius, radius, radius))) {
			BlockState blockState = player.level().getBlockState(pos);
			if (blockState.is(Blocks.FIRE) || blockState.is(Blocks.LAVA) || blockState.is(Blocks.MAGMA_BLOCK)) {
				if (player.level().random.nextFloat() < UnfairCraftConfig.SET_FIRE.chance.get()) {
					player.igniteForSeconds(UnfairCraftConfig.SET_FIRE.initialDuration.get());
					return;
				}
			}
		}
	}

	@Inject(
			method = "tick",
			at = @At("TAIL")
	)
	private void onPlayerTickTail(CallbackInfo info) {
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.TOTEM.enabled)) {
			return;
		}

		Player player = (Player) (Object) this;

		if (player.level().isClientSide() || !player.isOnFire() || player.isSpectator() || player.isCreative()) {
			return;
		}

		int radius = UnfairCraftConfig.SET_FIRE.radius.get();
		BlockPos playerPos = player.blockPosition();

		for (BlockPos pos: BlockPos.betweenClosed(playerPos.offset(-radius, -radius, -radius), playerPos.offset(radius, radius, radius))) {
			BlockState blockState = player.level().getBlockState(pos);
			if (blockState.is(Blocks.FIRE) || blockState.is(Blocks.LAVA) || blockState.is(Blocks.MAGMA_BLOCK)) {
				player.setRemainingFireTicks(player.getRemainingFireTicks() + UnfairCraftConfig.SET_FIRE.durationIncrease.get());
				return;
			}
		}
	}
}
