package io.github.junyali.unfaircraft.mixin.player;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class PlayerTick {
	@Unique
	private static final boolean unfaircraft$enabled = UnfairCraftConfig.isEnabled(UnfairCraftConfig.PLAYER.enabled);

	@Unique
	private void unfaircraft$dropItemAndPlaySound(Player player, ItemStack stack) {
		player.drop(stack, true);
		player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2f, ((player.level().random.nextFloat() - player.level().random.nextFloat()) * 0.7f + 1.0f) * 2.0f);
	}

	@Inject(
			method = "tick",
			at = @At("TAIL")
	)
	private void onPlayerTick(CallbackInfo ci) {
		if (!unfaircraft$enabled) {
			return;
		}

		Player player = (Player) (Object) this;

		if (!player.level().isClientSide() && player.level().random.nextFloat() < UnfairCraftConfig.PLAYER.randomDropChance.get().floatValue()) {
			ItemStack mainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND);
			if (!mainHandItem.isEmpty()) {
				unfaircraft$dropItemAndPlaySound(player, mainHandItem);
				player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
				return;
			}

			ItemStack offHandItem = player.getItemInHand(InteractionHand.OFF_HAND);
			if (!offHandItem.isEmpty()) {
				unfaircraft$dropItemAndPlaySound(player, offHandItem);
				player.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
				return;
			}

			Inventory inventory = player.getInventory();
			for (int i = 0; i < inventory.getContainerSize(); i++) {
				ItemStack stack = inventory.getItem(i);
				if (!stack.isEmpty()) {
					unfaircraft$dropItemAndPlaySound(player, stack.copy());
					inventory.setItem(i, ItemStack.EMPTY);
					return;
				}
			}
		}
	}
}
