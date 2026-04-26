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

	@Inject(
			method = "setHealth",
			at = @At("HEAD"),
			cancellable = true
	)
	private void unfaircraft$modifyHealing(float health, CallbackInfo ci) {
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.END_CRYSTAL.enabled)) {
			return;
		}

		LivingEntity entity = (LivingEntity) (Object) this;
		if (!(entity instanceof EnderDragon dragon)) {
			return;
		}

		float currentHealth = dragon.getHealth();

		if (unfaircraft$previousHeath < 0) {
			unfaircraft$previousHeath = currentHealth;
			return;
		}

		if (health > currentHealth) {
			float delta = health - currentHealth;
			float multiplier = UnfairCraftConfig.END_CRYSTAL.healingMultiplier.get().floatValue();
			float boosted = currentHealth + (delta * multiplier);

			ci.cancel();
			dragon.setHealth(Math.min(boosted, dragon.getMaxHealth()));
		}

		unfaircraft$previousHeath = dragon.getHealth();
	}
}
