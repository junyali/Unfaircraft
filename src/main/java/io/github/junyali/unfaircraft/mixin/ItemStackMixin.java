package io.github.junyali.unfaircraft.mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
	@Unique
	private static final float DURABILITY_LOSS_CHANCE = 0.05f;

	@ModifyVariable(method = "hurtAndBreak(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/server/level/ServerPlayer;Ljava/util/function/Consumer;)V", at = @At("HEAD"), argsOnly = true, ordinal = 0)
	private int modifyDurabilityDamage(int amount) {
		ItemStack stack = (ItemStack) (Object) this;

		if (stack.getItem() instanceof TieredItem) {
			if (Math.random() < DURABILITY_LOSS_CHANCE) {
				return amount + 2;
			}
		}
		return amount;
	}
}
