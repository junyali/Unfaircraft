package io.github.junyali.unfaircraft.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public class PlayerMixin {
	@Unique
	private static final float CRIT_FAIL_CHANCE = 0.25f;

	@Redirect(
			method = "attack",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"
			)
	)
	private boolean redirectHurt(LivingEntity target, DamageSource damageSource, float damage) {
		Player player = (Player) (Object) this;

		boolean isCritical = player.getAttackStrengthScale(0.5f) > 0.9f &&
							player.fallDistance > 0.0f &&
							!player.onGround() &&
							!player.onClimbable() &&
							!player.isInWater() &&
							!player.isPassenger();

		if (isCritical && player.getMainHandItem().getItem() instanceof SwordItem) {
			if (player.level().random.nextFloat() < CRIT_FAIL_CHANCE) {
				return target.hurt(damageSource, 0.0f);
			}
		}

		return target.hurt(damageSource, damage);
	}
}
