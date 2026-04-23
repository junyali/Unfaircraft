package io.github.junyali.unfaircraft.mixin.player;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class FallDamageMixin {
	private boolean enabled = UnfairCraftConfig.isEnabled(UnfairCraftConfig.PLAYER.enabled);

	@ModifyVariable(
			method = "causeFallDamage",
			at = @At("HEAD"),
			argsOnly = true,
			ordinal = 0
	)
	private float increaseFallDamage(float fallDistance) {
		if (!enabled) {
			return fallDistance;
		}

		if (fallDistance > UnfairCraftConfig.PLAYER.fallDamageDistance.get().floatValue()) {
			return fallDistance * UnfairCraftConfig.PLAYER.fallDamageMultiplier.get().floatValue();
		}

		return fallDistance;
	}
}
