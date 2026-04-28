package io.github.junyali.unfaircraft.mixin.world;

import io.github.junyali.unfaircraft.UnfairCraft;
import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ChestBlockEntity.class)
public class ChestBlockEntityMixin {
	@Inject(
			method = "startOpen",
			at = @At("TAIL")
	)
	private void onChestOpen(Player player, CallbackInfo ci) {
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.chest.enabled())) {
			return;
		}

		ChestBlockEntity chest = (ChestBlockEntity) (Object) this;
		Level level = chest.getLevel();

		if (level != null && !level.isClientSide() && level.random.nextFloat() < UnfairCraft.CONFIG.chest.eatChance()) {
			List<Integer> nonEmptySlots = new ArrayList<>();
			for (int i = 0; i < chest.getContainerSize(); i++) {
				if (!chest.getItem(i).isEmpty()) {
					nonEmptySlots.add(i);
				}
			}

			if (!nonEmptySlots.isEmpty()) {
				int slotIndex = nonEmptySlots.get(level.random.nextInt(nonEmptySlots.size()));
				ItemStack stack = chest.getItem(slotIndex);

				int minEat = UnfairCraft.CONFIG.chest.eatItemMin();
				int maxEat = Math.min(UnfairCraft.CONFIG.chest.eatItemMax(), stack.getCount());
				int toEat = minEat;

				if (maxEat > minEat) {
					toEat = minEat + level.random.nextInt(maxEat - minEat + 1);
				}

				stack.shrink(toEat);
				chest.setItem(slotIndex, stack);

				BlockPos pos = chest.getBlockPos();
				level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.PLAYER_BURP, SoundSource.BLOCKS, 0.5f, 0.7f + level.random.nextFloat() * 0.6f);
			}
		}
	}
}
