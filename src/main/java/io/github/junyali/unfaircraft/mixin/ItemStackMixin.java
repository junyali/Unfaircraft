package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
	@ModifyVariable(method = "hurtAndBreak(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/server/level/ServerPlayer;Ljava/util/function/Consumer;)V", at = @At("HEAD"), argsOnly = true, ordinal = 0)
	private int modifyDurabilityDamage(int amount) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_ITEM_STACK_MIXIN.get()) {
			return amount;
		}

		ItemStack stack = (ItemStack) (Object) this;

		if (stack.getItem() instanceof TieredItem) {
			if (Math.random() < UnfairCraftConfig.ITEM_DURABILITY_LOSS_CHANCE.get().floatValue()) {
				// more maths owo
				int durabilityLoss = UnfairCraftConfig.ITEM_DURABILITY_DAMAGE_MIN.get() + (int) (Math.random() * (UnfairCraftConfig.ITEM_DURABILITY_DAMAGE_MAX.get() - UnfairCraftConfig.ITEM_DURABILITY_DAMAGE_MIN.get() + 1));

				return amount + durabilityLoss;
			}
		}
		return amount;
	}
}
