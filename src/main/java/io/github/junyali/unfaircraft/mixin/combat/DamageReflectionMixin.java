package io.github.junyali.unfaircraft.mixin.combat;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class DamageReflectionMixin {
	@Unique
	private static final boolean unfaircraft$enabled = UnfairCraftConfig.isEnabled(UnfairCraftConfig.DAMAGE_REFLECTION.enabled);

	@Inject(
			method = "actuallyHurt",
			at = @At("HEAD")
	)
	private void reflectDamageToAttacker(DamageSource damageSource, float damageAmount, CallbackInfo ci) {
		if (!unfaircraft$enabled) {
			return;
		}

		LivingEntity entity = (LivingEntity) (Object) this;

		if (damageSource.getEntity() instanceof Player player) {
			if (entity.level().random.nextFloat() < UnfairCraftConfig.DAMAGE_REFLECTION.chance.get().floatValue()) {
				float reflectionPercentage = UnfairCraftConfig.DAMAGE_REFLECTION.percentageMin.get().floatValue() +
						entity.level().random.nextFloat() * (UnfairCraftConfig.DAMAGE_REFLECTION.percentageMax.get().floatValue() -
								UnfairCraftConfig.DAMAGE_REFLECTION.percentageMin.get().floatValue());

				float reflectedDamage = damageAmount * reflectionPercentage;

				if (UnfairCraftConfig.DAMAGE_REFLECTION.ignoreThorns.get()) {
					player.hurt(damageSource, reflectedDamage);
				} else {
					player.hurt(player.damageSources().thorns(entity), reflectedDamage);
				}
			}
		}
	}
}
