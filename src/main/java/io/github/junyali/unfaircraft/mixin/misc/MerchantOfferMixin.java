package io.github.junyali.unfaircraft.mixin.misc;

import io.github.junyali.unfaircraft.UnfairCraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MerchantOffer.class)
public class MerchantOfferMixin {
	@Inject(
			method = "getCostA",
			at = @At("RETURN")
	)
	private void unfaircraft$gougeCostA(CallbackInfoReturnable<ItemStack> cir) {
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.merchantOffer.enabled())) {
			return;
		}

		ItemStack cost = cir.getReturnValue();
		if (!cost.isEmpty()) {
			int multiplier = UnfairCraft.CONFIG.merchantOffer.multiplier();
			int newCount = Math.min(cost.getMaxStackSize(), cost.getCount() * multiplier);
			cost.setCount(newCount);
		}
	}
}
