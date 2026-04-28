package io.github.junyali.unfaircraft.mixin.item;

import io.github.junyali.unfaircraft.UnfairCraft;
import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
	@ModifyVariable(
			method = "hurtAndBreak(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/server/level/ServerPlayer;Ljava/util/function/Consumer;)V",
			at = @At("HEAD"),
			argsOnly = true,
			ordinal = 0
	)
	private int modifyDurabilityDamage(int amount) {
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.itemDurability.enabled())) {
			return amount;
		}

		ItemStack stack = (ItemStack) (Object) this;

		if (stack.getItem() instanceof TieredItem) {
			if (Math.random() < UnfairCraft.CONFIG.itemDurability.lossChance()) {
				// more maths owo
				int durabilityLoss = UnfairCraft.CONFIG.itemDurability.damageMin() + (int) (Math.random() * (UnfairCraft.CONFIG.itemDurability.damageMax() - UnfairCraft.CONFIG.itemDurability.damageMin() + 1));

				return amount + durabilityLoss;
			}
		}
		return amount;
	}
}
