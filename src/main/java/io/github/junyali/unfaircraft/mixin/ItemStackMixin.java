package io.github.junyali.unfaircraft.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ItemStack.class)
public class ItemStackMixin {
	@ModifyArg(method = "hurtAndBreak*", at = @At("HEAD"), index = 0)
	private int modifyDurabilityDamage(int damage, LivingEntity entity) {
		ItemStack stack = (ItemStack) (Object) this;

		if (stack.getItem() instanceof TieredItem && entity != null) {
			if (entity.level().random.nextFloat() < 0.05f) {
				return damage + 2;
			}
		}

		return damage;
	}
}
