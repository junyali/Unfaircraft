package io.github.junyali.unfaircraft.mixin.entity;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.enchantment.Enchantments;
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
		if (level.isClientSide()) {
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
		}
	}
}
