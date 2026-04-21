package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Inject(
			method = "jumpFromGround",
			at = @At("TAIL")
	)
	private void modifyJumpPower(CallbackInfo ci) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_LIVING_ENTITY_MIXIN.get() || !UnfairCraftConfig.ENABLE_DRUNK_JUMPING.get()) {
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
		if (!(entity instanceof Player player)) {
			return;
		}

		ItemStack totemStack = null;
		for (InteractionHand hand : InteractionHand.values()) {
			ItemStack stack = player.getItemInHand(hand);
			if (stack.is(Items.TOTEM_OF_UNDYING)) {
				totemStack = stack;
				break;
			}
		}

		if (totemStack != null && entity.level().random.nextFloat() < UnfairCraftConfig.TOTEM_FAIL_CHANCE.get().floatValue()) {
			totemStack.shrink(1);
			entity.level().playSound(null, entity.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1.0F, 0.5F + entity.level().random.nextFloat() * 0.2F);
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

	@Unique
	private int unfaircraft$ticksSinceLastDamage = 0;

	@Inject(
			method = "tick",
			at = @At("HEAD")
	)
	private void onMobTick(CallbackInfo ci) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_MOB_REGEN_MIXIN.get()) {
			return;
		}

		LivingEntity entity = (LivingEntity) (Object) this;

		if (entity.level().isClientSide || (!(entity instanceof Enemy))) {
			return;
		}

		if (entity.getHealth() >= entity.getMaxHealth()) {
			unfaircraft$ticksSinceLastDamage = 0;
			return;
		}

		unfaircraft$ticksSinceLastDamage++;

		int regenDelay = UnfairCraftConfig.MOB_REGEN_DELAY.get();
		if (unfaircraft$ticksSinceLastDamage < regenDelay) {
			return;
		}
		int regenRate = UnfairCraftConfig.MOB_REGEN_RATE.get();
		if (unfaircraft$ticksSinceLastDamage % regenRate == 0) {
			float regenAmount = UnfairCraftConfig.MOB_REGEN_AMOUNT.get().floatValue();
			entity.heal(regenAmount);
		}
	}

	@Inject(
			method = "actuallyHurt",
			at = @At("HEAD")
	)
	private void onMobHurt(DamageSource source, float amount, CallbackInfo ci) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_MOB_REGEN_MIXIN.get()) {
			return;
		}

		LivingEntity entity = (LivingEntity) (Object) this;

		if (entity instanceof Enemy) {
			unfaircraft$ticksSinceLastDamage = 0;
		}
	}

	@Inject(
			method = "tick",
			at = @At("TAIL")
	)
	private void setFire(CallbackInfo info) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_SET_FIRE_MIXIN.get()) {
			return;
		}

		LivingEntity entity = (LivingEntity) (Object) this;

		if (!(entity instanceof Player player)) {
			return;
		}

		if (player.level().isClientSide() || player.isOnFire() || player.isSpectator() || player.isCreative()) {
			return;
		}

		int radius = UnfairCraftConfig.SET_FIRE_RADIUS.get();
		BlockPos playerPos = player.blockPosition();

		for (BlockPos pos: BlockPos.betweenClosed(playerPos.offset(-radius, -radius, -radius), playerPos.offset(radius, radius, radius))) {
			BlockState blockState = player.level().getBlockState(pos);
			if (blockState.is(Blocks.FIRE) || blockState.is(Blocks.LAVA) || blockState.is(Blocks.MAGMA_BLOCK)) {
				if (player.level().random.nextFloat() < UnfairCraftConfig.SET_FIRE_CHANCE.get()) {
					player.igniteForSeconds(UnfairCraftConfig.SET_FIRE_INITIAL_DURATION.get());
					return;
				}
			}
		}
	}

	@Inject(
			method = "eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/food/FoodProperties;)Lnet/minecraft/world/item/ItemStack;",
			at = @At("RETURN")
	)
	private void onEat(Level level, ItemStack stack, FoodProperties foodProperties, CallbackInfoReturnable<ItemStack> cir) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_LIVING_ENTITY_MIXIN.get()) {
			return;
		}

		if (level.isClientSide()) {
			return;
		}

		Item item = stack.getItem();

		boolean isCooked =
				item == Items.COOKED_BEEF       ||
				item == Items.COOKED_CHICKEN    ||
				item == Items.COOKED_COD        ||
				item == Items.COOKED_MUTTON     ||
				item == Items.COOKED_RABBIT     ||
				item == Items.COOKED_PORKCHOP   ||
				item == Items.COOKED_SALMON     ||
				item == Items.BAKED_POTATO      ||
				item == Items.BREAD             ||
				item == Items.CAKE              ||
				item == Items.COOKIE            ||
				item == Items.PUMPKIN_PIE;

		if (!isCooked) {
			return;
		}

		LivingEntity entity = (LivingEntity) (Object) this;
		if (!(entity instanceof Player player)) {
			if (entity.level().random.nextFloat() < UnfairCraftConfig.FOOD_DEBUFF_CHANCE.get().floatValue()) {
				boolean givePoison = entity.level().random.nextBoolean();
				if (givePoison) {
					entity.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0));
				} else {
					entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 200, 1));
				}
			}

			if (unfaircraft$hungerBeforeEat < 0) return;

			int nutrition = foodProperties.nutrition();
			int hungerBefore = unfaircraft$hungerBeforeEat;
			int maxHunger = 20;
			int overflow = Math.max(0, (hungerBefore + nutrition) - maxHunger);
			if (overflow > 0) {
				int amplifier = Math.min(2, (overflow - 1) / 2);
				entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200 + overflow * 20, amplifier));
			}
			unfaircraft$hungerBeforeEat = -1;
		}
	}

	@Unique
	private final Map<ResourceLocation, Integer> unfaircraft$eatCounts = new HashMap<>();

	@Unique
	private ResourceLocation unfaircraft$lastEatenKey;

	@Unique
	private int unfaircraft$hungerBeforeEat = -1;

	@Inject(
			method = "eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/food/FoodProperties;)Lnet/minecraft/world/item/ItemStack;",
			at = @At("HEAD")
	)
	private void onEatHead(Level level, ItemStack stack, FoodProperties foodProperties, CallbackInfoReturnable<ItemStack> cir) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_LIVING_ENTITY_MIXIN.get()) {
			return;
		}

		if (level.isClientSide()) {
			return;
		}

		LivingEntity entity = (LivingEntity) (Object) this;

		if (entity instanceof Player player) {
			unfaircraft$hungerBeforeEat = player.getFoodData().getFoodLevel();
			ResourceLocation key = BuiltInRegistries.ITEM.getKey(stack.getItem());
			unfaircraft$lastEatenKey = key;
			unfaircraft$eatCounts.merge(key, 1, Integer::sum);
		}
	}

	@ModifyVariable(
			method = "eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/food/FoodProperties;)Lnet/minecraft/world/item/ItemStack;",
			at = @At("HEAD"),
			argsOnly = true,
			ordinal = 0
	)
	private FoodProperties diminishSaturation(FoodProperties foodProperties) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_LIVING_ENTITY_MIXIN.get()) {
			return foodProperties;
		}

		LivingEntity entity = (LivingEntity) (Object) this;

		if (entity.level().isClientSide()) return foodProperties;

		int count = unfaircraft$eatCounts.getOrDefault(unfaircraft$lastEatenKey, 0);
		float scale = Math.max(0.1f, 1.0f - count * 0.2f);

		return new FoodProperties(
				foodProperties.nutrition(),
				foodProperties.saturation() * scale,
				foodProperties.canAlwaysEat(),
				foodProperties.eatDurationTicks(),
				foodProperties.usingConvertsTo(),
				foodProperties.effects()
		);
	}
}
