package io.github.junyali.unfaircraft.mixin.item;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class ArmorMixin {
	@ModifyVariable(
			method = "getDamageAfterArmorAbsorb",
			at = @At("HEAD"),
			argsOnly = true,
			ordinal = 0
	)
	private float modifyArmorProtection(float damage, DamageSource source) {
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.ARMOUR.enabled)) {
			return damage;
		}

		LivingEntity entity = (LivingEntity) (Object) this;
		if (!(entity instanceof Player player)) {
			return damage;
		}

		for (EquipmentSlot slot : new EquipmentSlot[]{
				EquipmentSlot.HEAD,
				EquipmentSlot.CHEST,
				EquipmentSlot.LEGS,
				EquipmentSlot.FEET
		}) {
			ItemStack armorPiece = player.getItemBySlot(slot);
			if (!armorPiece.isEmpty() && entity.level().random.nextFloat() < UnfairCraftConfig.ARMOUR.protectionFailChance.get().floatValue()) {
				float armorValue = (float) player.getArmorValue();
				if (armorValue > 0) {
					float protectionMultiplier = 1.0f + (armorValue * 0.04f);
					return damage * protectionMultiplier;
				}
			}
		}

		return damage;
	}

	@Inject(
			method = "hurtArmor",
			at = @At("HEAD")
	)
	private void onArmorDamage(DamageSource source, float damage, CallbackInfo ci) {
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.ARMOUR.enabled)) {
			return;
		}

		LivingEntity entity = (LivingEntity) (Object) this;
		if (!(entity instanceof Player player)) {
			return;
		}

		for (EquipmentSlot slot : new EquipmentSlot[]{
				EquipmentSlot.HEAD,
				EquipmentSlot.CHEST,
				EquipmentSlot.LEGS,
				EquipmentSlot.FEET
		}) {
			ItemStack armorPiece = player.getItemBySlot(slot);
			if (!armorPiece.isEmpty() && entity.level().random.nextFloat() < UnfairCraftConfig.ARMOUR.durabilityLossChance.get().floatValue()) {
				int extraDamage = UnfairCraftConfig.ARMOUR.durabilityDamageMin.get() +
						(int) (entity.level().random.nextFloat() * (UnfairCraftConfig.ARMOUR.durabilityDamageMax.get() -
								UnfairCraftConfig.ARMOUR.durabilityDamageMin.get() + 1));

				armorPiece.hurtAndBreak(extraDamage, player, slot);
			}
		}
	}
}
