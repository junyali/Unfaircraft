package io.github.junyali.unfaircraft.mixin.player;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class FoodRotMixin {
	@Unique
	private static final String unfaircraft$rot_tag = "unfaircraft_rot";

	@Inject(
			method = "tick",
			at = @At("TAIL")
	)
	private void rotInventory(CallbackInfo ci) {
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.FOOD.enabled)) {
			return;
		}

		Player player = (Player) (Object) this;
		if (player.level().isClientSide()) {
			return;
		}
		if (player.level().getGameTime() % 20 != 0) {
			return;
		}

		Inventory inventory = player.getInventory();
		for (int i = 0; i < inventory.getContainerSize(); i++) {
			ItemStack stack = inventory.getItem(i);
			if (stack.isEmpty()) continue;

			Item item = stack.getItem();

			if (item == Items.ROTTEN_FLESH) continue;
			if (stack.getFoodProperties(player) == null) continue;

			CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
			int rot = tag.getInt(unfaircraft$rot_tag) + 20;
			// 24000 ticks is a full mc day fyi
			// if only fridges existed in minecraft.. :loll:
			if (rot >= 24000) {
				inventory.setItem(i, new ItemStack(Items.ROTTEN_FLESH, stack.getCount()));
			} else {
				tag.putInt(unfaircraft$rot_tag, rot);
				stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
			}
		}
	}
}
