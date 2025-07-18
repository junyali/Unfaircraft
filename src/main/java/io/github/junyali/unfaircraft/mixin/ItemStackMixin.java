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

	@Unique
	private static final int DURABILITY_DAMAGE_MIN = 2;

	@Unique
	private static final int DURABILITY_DAMAGE_MAX = 16;

	@ModifyVariable(method = "hurtAndBreak(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/server/level/ServerPlayer;Ljava/util/function/Consumer;)V", at = @At("HEAD"), argsOnly = true, ordinal = 0)
	private int modifyDurabilityDamage(int amount) {
		ItemStack stack = (ItemStack) (Object) this;

		if (stack.getItem() instanceof TieredItem) {
			if (Math.random() < DURABILITY_LOSS_CHANCE) {
				// more maths owo
				int durabilityLoss = DURABILITY_DAMAGE_MIN + (int) (Math.random() * (DURABILITY_DAMAGE_MAX - DURABILITY_DAMAGE_MIN + 1));

				return amount + durabilityLoss;
			}
		}
		return amount;
	}
}
