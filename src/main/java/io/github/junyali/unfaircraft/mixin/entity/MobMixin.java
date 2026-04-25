package io.github.junyali.unfaircraft.mixin.entity;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.IronGolem;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobMixin {
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
}
