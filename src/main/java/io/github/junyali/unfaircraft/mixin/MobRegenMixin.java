package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public class MobRegenMixin {
	@Unique
	private int unfaircraft$ticksSinceLastDamage = 0;

	@Inject(
			method = "tick",
			at = @At("HEAD")
	)
	private void onMobTick(CallbackInfo ci) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_UNFAIR_MODE.get()) {
			return;
		}

		Mob mob = (Mob) (Object) this;

		if (mob.level().isClientSide || (!(mob instanceof Enemy))) {
			return;
		}

		if (mob.getHealth() >= mob.getMaxHealth()) {
			unfaircraft$ticksSinceLastDamage = 0;
			return;
		}

		unfaircraft$ticksSinceLastDamage++;

		int regenDelay = UnfairCraftConfig.MOB_REGEN_DELAY.get();
		if (unfaircraft$ticksSinceLastDamage < regenDelay) {
			return;
		}
		int regenRate = UnfairCraftConfig.MOB_REGEN_RATE.get();
		if (unfaircraft$ticksSinceLastDamage % regenRate == 0) {
			float regenAmount = UnfairCraftConfig.MOB_REGEN_AMOUNT.get().floatValue();
			mob.heal(regenAmount);
		}
	}
}
