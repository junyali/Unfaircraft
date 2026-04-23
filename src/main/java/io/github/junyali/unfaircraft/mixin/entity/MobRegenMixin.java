package io.github.junyali.unfaircraft.mixin.entity;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class MobRegenMixin {
	private boolean enabled = UnfairCraftConfig.isEnabled(UnfairCraftConfig.MOB_REGEN.enabled);

	@Unique
	private int unfaircraft$ticksSinceLastDamage = 0;

	@Inject(
			method = "tick",
			at = @At("HEAD")
	)
	private void onMobTick(CallbackInfo ci) {
		if (!enabled) {
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

		int regenDelay = UnfairCraftConfig.MOB_REGEN.delay.get();
		if (unfaircraft$ticksSinceLastDamage < regenDelay) {
			return;
		}
		int regenRate = UnfairCraftConfig.MOB_REGEN.rate.get();
		if (unfaircraft$ticksSinceLastDamage % regenRate == 0) {
			float regenAmount = UnfairCraftConfig.MOB_REGEN.amount.get().floatValue();
			entity.heal(regenAmount);
		}
	}

	@Inject(
			method = "actuallyHurt",
			at = @At("HEAD")
	)
	private void onMobHurt(DamageSource source, float amount, CallbackInfo ci) {
		if (!enabled) {
			return;
		}

		LivingEntity entity = (LivingEntity) (Object) this;

		if (entity instanceof Enemy) {
			unfaircraft$ticksSinceLastDamage = 0;
		}
	}
}
