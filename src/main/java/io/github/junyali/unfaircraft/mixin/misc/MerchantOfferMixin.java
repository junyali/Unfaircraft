package io.github.junyali.unfaircraft.mixin.misc;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(MerchantOffer.class)
public class MerchantOfferMixin {
	@Inject(
			method = "<init>(Lnet/minecraft/world/item/trading/ItemCost;Ljava/util/Optional;Lnet/minecraft/world/item/ItemStack;IIIF)V",
			at = @At("RETURN")
	)
	private void unfaircraft$gougePrice(ItemCost baseCostA, Optional costB, ItemStack result, int _uses, int maxUses, int xp, float priceMultiplier, CallbackInfo ci) {
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.MERCHANT_OFFER.enabled)) {
			return;
		}

		MerchantOffer self = (MerchantOffer) (Object) this;

		int multiplier = UnfairCraftConfig.MERCHANT_OFFER.multiplier.get();

		ItemStack costA = self.getCostA();
		if (!costA.isEmpty()) {
			int newCount = Math.min(costA.getMaxStackSize(), costA.getCount() * multiplier);
			costA.setCount(newCount);
		}

		ItemStack costBStack = self.getCostB();
		if (!costBStack.isEmpty()) {
			int newCount = Math.min(costBStack.getMaxStackSize(), costBStack.getCount() * multiplier);
			costBStack.setCount(newCount);
		}
	}
}
