package io.github.junyali.unfaircraft.mixin.combat;

import io.github.junyali.unfaircraft.UnfairCraft;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class DamageReflectionMixin {
	@Inject(
			method = "actuallyHurt",
			at = @At("HEAD")
	)
	private void reflectDamageToAttacker(DamageSource damageSource, float damageAmount, CallbackInfo ci) {
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.damageReflection.enabled())) {
			return;
		}

		LivingEntity entity = (LivingEntity) (Object) this;

		if (damageSource.getEntity() instanceof Player player) {
			if (entity.level().random.nextFloat() < UnfairCraft.CONFIG.damageReflection.chance()) {
				float reflectionPercentage = UnfairCraft.CONFIG.damageReflection.percentageMin() +
						entity.level().random.nextFloat() * (UnfairCraft.CONFIG.damageReflection.percentageMax() -
								UnfairCraft.CONFIG.damageReflection.percentageMin());

				float reflectedDamage = damageAmount * reflectionPercentage;

				if (UnfairCraft.CONFIG.damageReflection.ignoreThorns()) {
					player.hurt(damageSource, reflectedDamage);
				} else {
					player.hurt(player.damageSources().thorns(entity), reflectedDamage);
				}
			}
		}
	}
}
