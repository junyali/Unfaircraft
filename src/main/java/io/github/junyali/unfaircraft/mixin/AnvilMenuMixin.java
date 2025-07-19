package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin {
	@Final
	@Shadow
	private ContainerLevelAccess access;

	@ModifyVariable(
			method = "createResult",
			at = @At("STORE"),
			ordinal = 0
	)
	private int modifyRepairCost(int originalCost) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_ANVIL_MENU_MIXIN.get()) {
			return originalCost;
		}

		if (originalCost > 0 && Math.random() < UnfairCraftConfig.ANVIL_COST_INCREASE_CHANCE.get().floatValue()) {
			int multiplier = (int) (UnfairCraftConfig.ANVIL_COST_MULTIPLIER_MIN.get().floatValue() + (float) (Math.random() * (UnfairCraftConfig.ANVIL_COST_MULTIPLIER_MAX.get().floatValue() - UnfairCraftConfig.ANVIL_COST_MULTIPLIER_MIN.get().floatValue() + 1)));
			return originalCost * multiplier;
		}

		return originalCost;
	}

	@Inject(
			method = "onTake",
			at = @At("HEAD")
	)
	private void onAnvilUse(Player player, ItemStack stack, CallbackInfo ci) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_ANVIL_MENU_MIXIN.get()) {
			return;
		}

		if (Math.random() < UnfairCraftConfig.ANVIL_INSTANT_BREAK_CHANCE.get().floatValue()) {
			access.execute((level, pos) -> {
				if (level instanceof ServerLevel) {
					BlockState state = level.getBlockState(pos);
					if (state.getBlock() instanceof AnvilBlock) {
						level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
						level.playSound(null, pos, SoundEvents.ANVIL_DESTROY, SoundSource.BLOCKS, 1.0f, level.random.nextFloat() * 0.1f + 0.9f);

					}
				}
			});
		}
	}
}
