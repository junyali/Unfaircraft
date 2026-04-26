package io.github.junyali.unfaircraft.mixin.entity;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobMixin {
	@Unique
	private boolean unfaircraft$ironGolemAttributesInitialised = false;

	@Inject(
			method = "finalizeSpawn",
			at = @At("RETURN")
	)
	private void unfaircraft$onMobSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir) {
		if (level.isClientSide() || (!(level instanceof ServerLevel serverLevel))) {
			return;
		}

		Mob mob = (Mob) (Object) this;

		if (mob instanceof IronGolem) {
			if (UnfairCraftConfig.isEnabled(UnfairCraftConfig.IRON_GOLEM.enabled)) {
				ItemStack boots = new ItemStack((Items.LEATHER_BOOTS));
				boots.enchant(
						level.registryAccess()
								.registryOrThrow(Registries.ENCHANTMENT)
								.getHolderOrThrow(Enchantments.DEPTH_STRIDER),
						3
				);
				boots.set(DataComponents.UNBREAKABLE, new Unbreakable(true));
				boots.set(DataComponents.DYED_COLOR, new DyedItemColor(0x00000000, false));
				mob.setItemSlot(EquipmentSlot.FEET, boots);
				mob.setDropChance(EquipmentSlot.FEET, 0.0F);
			}
		} else if (mob instanceof Phantom) {
			if (spawnType != MobSpawnType.NATURAL) {
				return;
			}

			if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.PHANTOM.enabled)) {
				return;
			}

			float summonChance = UnfairCraftConfig.PHANTOM.summonChance.get().floatValue();

			if (serverLevel.random.nextDouble() < summonChance) {
				int minPhantoms = UnfairCraftConfig.PHANTOM.summonMin.get();
				int maxPhantoms = UnfairCraftConfig.PHANTOM.summonMax.get();
				int extras = minPhantoms + serverLevel.random.nextInt(maxPhantoms - minPhantoms + 1);

				for (int i = 0; i < extras; i++) {
					Phantom extra = EntityType.PHANTOM.create(serverLevel);
					if (extra == null) continue;
					double offsetX = mob.getX() + (mob.getRandom().nextDouble() - 0.5) * 10;
					double offsetZ = mob.getZ() + (mob.getRandom().nextDouble() - 0.5) * 10;
					extra.moveTo(offsetX, mob.getY(), offsetZ, mob.getYRot(), 0.0f);
					extra.finalizeSpawn(level, difficulty, MobSpawnType.TRIGGERED, null);
					serverLevel.addFreshEntity(extra);
				}
			}
		} else if (mob instanceof Ghast) {
			if (spawnType != MobSpawnType.NATURAL) {
				return;
			}

			if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.GHAST.enabled)) {
				return;
			}

			float summonChance = UnfairCraftConfig.GHAST.summonChance.get().floatValue();

			if (serverLevel.random.nextDouble() < summonChance) {
				int minGhasts = UnfairCraftConfig.GHAST.summonMin.get();
				int maxGhasts = UnfairCraftConfig.GHAST.summonMax.get();
				int extras = minGhasts + serverLevel.random.nextInt(maxGhasts - minGhasts + 1);

				for (int i = 0; i < extras; i++) {
					Ghast extra = EntityType.GHAST.create(serverLevel);
					if (extra == null) continue;
					double offsetX = mob.getX() + (mob.getRandom().nextDouble() - 0.5) * 10;
					double offsetZ = mob.getZ() + (mob.getRandom().nextDouble() - 0.5) * 10;
					extra.moveTo(offsetX, mob.getY(), offsetZ, mob.getYRot(), 0.0f);
					extra.finalizeSpawn(level, difficulty, MobSpawnType.TRIGGERED, null);
					serverLevel.addFreshEntity(extra);
				}
			}
		}
	}

	@Inject(
			method = "checkSpawnRules",
			at = @At("RETURN"),
			cancellable = true
	)
	private void unfaircraft$daytimeSpawn(LevelAccessor level, MobSpawnType reason, CallbackInfoReturnable<Boolean> cir) {
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.MOB.allowHostileDaylightSpawn)) {
			return;
		}

		if (cir.getReturnValue()) {
			return;
		}

		Mob self = (Mob) (Object) this;

		if (!(self instanceof Monster)) {
			return;
		}

		if (self.getRandom().nextFloat() < UnfairCraftConfig.MOB.hostileDaylightSpawnChance.get().floatValue()) {
			cir.setReturnValue(true);
		}
	}

	@Inject(
			method = "tick",
			at = @At("HEAD")
	)
	private void unfaircraft$initialiseAttributes(CallbackInfo ci) {
		Mob mob = (Mob) (Object) this;

		if (mob instanceof IronGolem) {
			if (!unfaircraft$ironGolemAttributesInitialised) {
				unfaircraft$ironGolemAttributesInitialised = true;

				if (UnfairCraftConfig.isEnabled(UnfairCraftConfig.IRON_GOLEM.enabled)) {
					AttributeInstance maxHealth = mob.getAttribute(Attributes.MAX_HEALTH);
					if (maxHealth != null) {
						maxHealth.setBaseValue(200.0);
						mob.setHealth(200.0f);
					}

					AttributeInstance movementSpeed = mob.getAttribute(Attributes.MOVEMENT_SPEED);
					if (movementSpeed != null) {
						movementSpeed.setBaseValue(0.35);
					}

					AttributeInstance attackDamage = mob.getAttribute(Attributes.ATTACK_DAMAGE);
					if (attackDamage != null) {
						attackDamage.setBaseValue(20.0);
					}

					AttributeInstance attackKnockback = mob.getAttribute(Attributes.ATTACK_KNOCKBACK);
					if (attackKnockback != null) {
						attackKnockback.setBaseValue(2.0);
					}

					AttributeInstance interactionRange = mob.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
					if (interactionRange != null) {
						interactionRange.setBaseValue(6.0);
					}

					AttributeInstance waterMovement = mob.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY);
					if (waterMovement != null) {
						waterMovement.setBaseValue(1.0);
					}

					AttributeInstance followRange = mob.getAttribute(Attributes.FOLLOW_RANGE);
					if (followRange != null) {
						followRange.setBaseValue(32.0);
					}
				}
			}
		}
	}
}
