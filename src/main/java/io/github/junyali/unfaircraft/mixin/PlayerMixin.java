package io.github.junyali.unfaircraft.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {
	@Unique
	private static final float CRIT_FAIL_CHANCE = 0.25f;

	@Inject(method = "attack", at = @At("HEAD"), cancellable = true)
	private void beforeAttack(Entity target, CallbackInfo ci) {
		Player player = (Player) (Object) this;

		boolean isCritical = player.getAttackStrengthScale(0.5f) > 0.9f &&
				player.fallDistance > 0.0f &&
				!player.onGround() &&
				!player.onClimbable() &&
				!player.isInWater() &&
				!player.isPassenger();

		if (isCritical && player.getMainHandItem().getItem() instanceof SwordItem) {
			if (player.level().random.nextFloat() < CRIT_FAIL_CHANCE) {
				ci.cancel();
			}
		}
	}
}
