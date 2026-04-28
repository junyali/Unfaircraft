package io.github.junyali.unfaircraft.mixin.misc;

import io.github.junyali.unfaircraft.UnfairCraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin {
	@ModifyVariable(
			method = "createResult",
			at = @At("STORE"),
			ordinal = 0
	)
	private int modifyRepairCost(int originalCost) {
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.anvil.enabled())) {
			return originalCost;
		}

		if (originalCost > 0 && Math.random() < UnfairCraft.CONFIG.anvil.costIncreaseChance()) {
			int multiplier = (int) (UnfairCraft.CONFIG.anvil.costMultiplierMin() + (float) (Math.random() * (UnfairCraft.CONFIG.anvil.costMultiplierMax() - UnfairCraft.CONFIG.anvil.costMultiplierMin() + 1)));
			return originalCost * multiplier;
		}

		return originalCost;
	}

	@Inject(
			method = "onTake",
			at = @At("HEAD")
	)
	private void onAnvilUse(Player player, ItemStack stack, CallbackInfo ci) {
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.anvil.enabled())) {
			return;
		}

		if (Math.random() < UnfairCraft.CONFIG.anvil.instantBreakChance()) {
			if (player.level() instanceof ServerLevel serverLevel) {
				BlockPos playerPos = player.blockPosition();
				for (int x = -2; x <= 2; x++) {
					for (int y = -1; y <= 1; y++) {
						for (int z = -2; z <= 2; z++) {
							BlockPos checkPos = playerPos.offset(x, y, z);
							BlockState state = serverLevel.getBlockState(checkPos);

							if (state.getBlock() instanceof AnvilBlock) {
								serverLevel.setBlockAndUpdate(checkPos, Blocks.AIR.defaultBlockState());
								serverLevel.playSound(null, checkPos, SoundEvents.ANVIL_DESTROY, SoundSource.BLOCKS, 1.0f, serverLevel.random.nextFloat() * 0.1f + 0.9f);
							return;
							}
						}
					}
				}
			}
		}
	}
}
