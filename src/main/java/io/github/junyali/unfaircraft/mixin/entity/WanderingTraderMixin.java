package io.github.junyali.unfaircraft.mixin.entity;

import io.github.junyali.unfaircraft.UnfairCraft;
import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Optional;

@Mixin(WanderingTrader.class)
public abstract class WanderingTraderMixin {
	@Unique
	private static final Map<Item, String> unfaircraft$scam_items = Map.of(
			Items.GRAVEL, "Best Gravel",
			Items.DIRT, "Premium Dirt",
			Items.COBBLESTONE, "Ancient Stone",
			Items.ROTTEN_FLESH, "Exotic Meat",
			Items.SAND, "Golden Sand",
			Items.POISONOUS_POTATO, "Rare Delicacy",
			Items.DEAD_BUSH, "Magical Herb"
	);

	@Inject(
			method = "updateTrades",
			at = @At("RETURN")
	)
	private void unfaircraft$scamTrades(CallbackInfo ci) {
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.merchantOffer.wanderingTraderScamEnabled())) {
			return;
		}

		WanderingTrader self = (WanderingTrader) (Object) this;
		MerchantOffers offers = self.getOffers();

		if (offers.isEmpty()) {
			return;
		}

		RandomSource random = self.getRandom();
		MerchantOffers modifiedOffers = new MerchantOffers();

		Item[] scamItems = unfaircraft$scam_items.keySet().toArray(new Item[0]);
		float scamChance = (float) UnfairCraft.CONFIG.merchantOffer.wanderingTraderScamChance();

		for (MerchantOffer original : offers) {
			if (random.nextFloat() < scamChance) {
				ItemCost costA = original.getItemCostA();
				Optional<ItemCost> costB = original.getItemCostB();

				Item scamItem = scamItems[random.nextInt(scamItems.length)];
				String fakeName = unfaircraft$scam_items.get(scamItem);

				ItemStack scamResult = new ItemStack(scamItem, original.getResult().getCount());
				scamResult.set(DataComponents.CUSTOM_NAME, Component.literal(fakeName));
				scamResult.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
				modifiedOffers.add(new MerchantOffer(
						costA,
						costB,
						scamResult,
						original.getUses(),
						original.getMaxUses(),
						original.getXp(),
						original.getPriceMultiplier(),
						original.getDemand()
				));
			} else {
				modifiedOffers.add(original);
			}
		}

		offers.clear();
		offers.addAll(modifiedOffers);
	}
}
