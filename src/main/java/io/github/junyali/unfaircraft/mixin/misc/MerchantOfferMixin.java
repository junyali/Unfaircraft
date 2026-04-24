package io.github.junyali.unfaircraft.mixin.misc;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(MerchantOffer.class)
public class MerchantOfferMixin {
	@Inject(
			method = "getCostA",
			at = @At("RETURN")
	)
	private void unfaircraft$gougeCostA(CallbackInfoReturnable<ItemStack> cir) {
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.MERCHANT_OFFER.enabled)) {
			return;
		}

		ItemStack cost = cir.getReturnValue();
		if (!cost.isEmpty()) {
			int multiplier = UnfairCraftConfig.MERCHANT_OFFER.multiplier.get();
			int newCount = Math.min(cost.getMaxStackSize(), cost.getCount() * multiplier);
			cost.setCount(newCount);
		}
	}
}
