package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Zombie.class)
public class ZombieMixin {
	@Inject(
			method = "setBaby",
			at = @At("TAIL")
	)
	private void onSetBaby(boolean baby, CallbackInfo ci) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_ZOMBIE_MIXIN.get()) {
			return;
		}

		if (!baby) {
			return;
		}

		Zombie zombie = (Zombie) (Object) this;

		AttributeInstance attackDamage = zombie.getAttribute(Attributes.ATTACK_DAMAGE);
		if (attackDamage != null) {
			attackDamage.setBaseValue(3.0);
		}

		AttributeInstance movementSpeed = zombie.getAttribute(Attributes.MOVEMENT_SPEED);
		if (movementSpeed != null) {
			double currentSpeed = movementSpeed.getBaseValue();
			movementSpeed.setBaseValue(currentSpeed * 1.5);
		}
	}
}
