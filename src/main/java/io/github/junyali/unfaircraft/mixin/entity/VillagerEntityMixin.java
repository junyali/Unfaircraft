package io.github.junyali.unfaircraft.mixin.entity;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class VillagerEntityMixin {
	@Inject(
			method = "hurt",
			at = @At("HEAD")
	)
	private void unfaircraft$villagerHostileOnHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.MERCHANT_OFFER.villageRetaliation)) {
			return;
		}

		LivingEntity self = (LivingEntity) (Object) this;

		if (!(self instanceof Villager villager)) {
			return;
		}

		if (source.getEntity() instanceof Player player) {
			villager.setTarget(player);
		}
	}
}
