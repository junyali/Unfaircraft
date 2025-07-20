package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LootTable.class)
public class LootTableMixin {
	@Unique
	private boolean unfaircraft$isStructureLootTable(ResourceLocation lootTableId) {
		String path = lootTableId.getPath();
		return path.contains("chests") ||
				path.contains("stronghold") ||
				path.contains("dungeon") ||
				path.contains("pyramid") ||
				path.contains("mansion") ||
				path.contains("end_city") ||
				path.contains("bastion") ||
				path.contains("shipwreck");
	}

	@Unique
	private List<ItemStack> unfaircraft$createTrollLoot(RandomSource random) {
		List<ItemStack> trollItems = new java.util.ArrayList<>();

		int trollType = random.nextInt(3);

		switch (trollType) {
			case 0:
				for (int i = 0; i < 9; i++) {
					trollItems.add(new ItemStack(Items.COAL, 1));
				}
				break;

			case 1:
				trollItems.add(new ItemStack(Items.DIRT, 1));
				break;

			case 2:
				trollItems.add(new ItemStack(Items.ROTTEN_FLESH, 64));
				break;
		}

		return trollItems;
	}

	@Inject(
			method = "getRandomItems*",
			at = @At("RETURN"),
			cancellable = true
	)
	private void trollLootTable(LootContext context, CallbackInfoReturnable<List<ItemStack>> cir) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_LOOT_TABLE_MIXIN.get()) {
			return;
		}

		ResourceLocation lootTableId = context.getQueriedLootTableId();

		if (lootTableId != null && unfaircraft$isStructureLootTable(lootTableId)) {
			List<ItemStack> originalLoot = cir.getReturnValue();

			if (!originalLoot.isEmpty() && context.getRandom().nextFloat() < UnfairCraftConfig.LOOT_TABLE_TROLL_CHANCE.get().floatValue()) {
				List<ItemStack> trollLoot = unfaircraft$createTrollLoot(context.getRandom());
				cir.setReturnValue(trollLoot);
			}
		}
	}
}
