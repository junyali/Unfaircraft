package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Inject(
			method = "jumpFromGround",
			at = @At("TAIL")
	)
	private void modifyJumpPower(CallbackInfo ci) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_LIVING_ENTITY_MIXIN.get()) {
			return;
		}

		LivingEntity entity = (LivingEntity) (Object) this;

		if (entity instanceof Player) {

			Vec3 motion = entity.getDeltaMovement();
			float jumpModification = 1.0f;

			int random = entity.level().random.nextInt(5);
			switch (random) {
				case 1 -> jumpModification = 0.3f;
				case 2 -> jumpModification = 0.8f;
				case 3 -> jumpModification = 1.2f;
				case 4 -> jumpModification = 1.5f;
				case 5 -> jumpModification = 2.0f;
			}

			entity.setDeltaMovement(motion.x, motion.y * jumpModification, motion.z);
		}
	}

	@ModifyVariable(
			method = "getDamageAfterArmorAbsorb",
			at = @At("HEAD"),
			argsOnly = true,
			ordinal = 0
	)
	private float modifyArmorProtection(float damage, DamageSource source) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_ARMOR_MIXIN.get()) {
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
			if (!armorPiece.isEmpty() && entity.level().random.nextFloat() < UnfairCraftConfig.ARMOR_PROTECTION_FAIL_CHANCE.get().floatValue()) {
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
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_ARMOR_MIXIN.get()) {
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
			if (!armorPiece.isEmpty() && entity.level().random.nextFloat() < UnfairCraftConfig.ARMOR_DURABILITY_LOSS_CHANCE.get().floatValue()) {
				int extraDamage = UnfairCraftConfig.ARMOR_DURABILITY_DAMAGE_MIN.get() +
						(int) (entity.level().random.nextFloat() * (UnfairCraftConfig.ARMOR_DURABILITY_DAMAGE_MAX.get() -
								UnfairCraftConfig.ARMOR_DURABILITY_DAMAGE_MIN.get() + 1));

				armorPiece.hurtAndBreak(extraDamage, player, slot);
			}
		}
	}

	@Inject(
			method = "checkTotemDeathProtection",
			at = @At("HEAD"),
			cancellable = true
	)
	private void makeTotemFail(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_LIVING_ENTITY_MIXIN.get()) {
			return;
		}

		LivingEntity entity = (LivingEntity) (Object) this;
		if (!(entity instanceof Player)) {
			return;
		}

		if (entity.level().random.nextFloat() < UnfairCraftConfig.TOTEM_FAIL_CHANCE.get().floatValue()) {
			cir.setReturnValue(false);
		}
	}

	@Inject(
			method = "knockback",
			at = @At("HEAD")
	)
	private void knockbackPlayer(double strength, double x, double z, CallbackInfo ci) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_LIVING_ENTITY_MIXIN.get()) {
			return;
		}

		LivingEntity entity = (LivingEntity) (Object) this;

		if (entity.getLastHurtByMob() instanceof Player player) {
			if (entity.level().random.nextFloat() < UnfairCraftConfig.PLAYER_KNOCKBACK_CHANCE.get().floatValue()) {
				ItemStack heldItem = player.getMainHandItem();
				int knockbackLevel = heldItem.getEnchantmentLevel(player.level().registryAccess()
						.registryOrThrow(Registries.ENCHANTMENT)
						.getHolderOrThrow(Enchantments.KNOCKBACK));

				double knockbackMultiplier = 1.0 + (knockbackLevel * 0.5);
				double modifiedStrength = strength * knockbackMultiplier * UnfairCraftConfig.PLAYER_KNOCKBACK_MULTIPLIER.get();

				player.knockback(modifiedStrength, -x, -z);
			}
		}
	}

	@Inject(
			method = "actuallyHurt",
			at = @At("HEAD")
	)
	private void reflectDamageToAttacker(DamageSource damageSource, float damageAmount, CallbackInfo ci) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_LIVING_ENTITY_MIXIN.get()) {
			return;
		}

		LivingEntity entity = (LivingEntity) (Object) this;

		if (damageSource.getEntity() instanceof Player player) {
			if (entity.level().random.nextFloat() < UnfairCraftConfig.DAMAGE_REFLECTION_CHANCE.get().floatValue()) {
				float reflectionPercentage = UnfairCraftConfig.DAMAGE_REFLECTION_PERCENTAGE_MIN.get().floatValue() +
						entity.level().random.nextFloat() * (UnfairCraftConfig.DAMAGE_REFLECTION_PERCENTAGE_MAX.get().floatValue() -
						UnfairCraftConfig.DAMAGE_REFLECTION_PERCENTAGE_MIN.get().floatValue());

				float reflectedDamage = damageAmount * reflectionPercentage;

				if (UnfairCraftConfig.DAMAGE_REFLECTION_IGNORE_THORNS.get()) {
					player.hurt(damageSource, reflectedDamage);
				} else {
					player.hurt(player.damageSources().thorns(entity), reflectedDamage);
				}
			}
		}
	}
}
