package io.github.junyali.unfaircraft.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Player.class)
public class PlayerMixin {
	@Unique
	private static final float CRIT_FAIL_CHANCE = 0.25f;

	@ModifyVariable(method = "attack", at = @At("HEAD"), argsOnly = true)
	private Player modifyAttackDamage(Player value) {
		Player player = (Player) (Object) this;

		boolean isCritical = player.getAttackStrengthScale(0.5f) > 0.9f &&
							player.fallDistance > 0.0f &&
							!player.onGround() &&
							!player.onClimbable() &&
							!player.isInWater() &&
							!player.isPassenger();

		if (isCritical && player.getMainHandItem().getItem() instanceof SwordItem) {
			if (player.level().random.nextFloat() < CRIT_FAIL_CHANCE) {
				return value;
			}
		}

		return value;
	}
}
