package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShieldItem.class)
public class ShieldItemMixin {
	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	private void onShieldUse(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_SHIELD_ITEM_MIXIN.get()) {
			return;
		}

		if (!level.isClientSide) {
			if (level.random.nextFloat() < UnfairCraftConfig.SHIELD_FAIL_CHANCE.get().floatValue()) {
				cir.setReturnValue(InteractionResultHolder.fail(player.getItemInHand(hand)));
			}
		}
	}
}
