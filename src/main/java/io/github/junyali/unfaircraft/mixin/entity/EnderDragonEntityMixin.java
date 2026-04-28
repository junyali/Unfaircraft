package io.github.junyali.unfaircraft.mixin.entity;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class EnderDragonEntityMixin {
	@Unique
	private float unfaircraft$previousHeath = -1f;

	@Unique
	private boolean unfaircraft$isSettingHealth = false;

	@Inject(
			method = "setHealth",
			at = @At("HEAD"),
			cancellable = true
	)
	private void unfaircraft$modifyHealing(float health, CallbackInfo ci) {
		if (unfaircraft$isSettingHealth) {
			return;
		}

		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.END_CRYSTAL.enabled)) {
			return;
		}

		LivingEntity entity = (LivingEntity) (Object) this;
		if (!(entity instanceof EnderDragon)) {
			return;
		}

		if (unfaircraft$previousHeath >= 0 && health > unfaircraft$previousHeath) {
			float healAmount = health - unfaircraft$previousHeath;
			float modifiedAmount = healAmount * UnfairCraftConfig.END_CRYSTAL.healingMultiplier.get().floatValue();
			float newHealth = unfaircraft$previousHeath + modifiedAmount;

			unfaircraft$isSettingHealth = true;
			entity.setHealth(newHealth);
			unfaircraft$isSettingHealth = false;

			unfaircraft$previousHeath = newHealth;
			ci.cancel();
		} else {
			unfaircraft$previousHeath = health;
		}
	}
}
