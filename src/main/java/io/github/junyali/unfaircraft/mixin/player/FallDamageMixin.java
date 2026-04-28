package io.github.junyali.unfaircraft.mixin.player;

import io.github.junyali.unfaircraft.UnfairCraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class FallDamageMixin {
	@ModifyVariable(
			method = "causeFallDamage",
			at = @At("HEAD"),
			argsOnly = true,
			ordinal = 0
	)
	private float increaseFallDamage(float fallDistance) {
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.player.enabled())) {
			return fallDistance;
		}

		LivingEntity entity = (LivingEntity) (Object) this;

		if (!(entity instanceof Player player)) {
			return fallDistance;
		}

		if (fallDistance > UnfairCraft.CONFIG.player.fallDamageDistance()) {
			return (float) (fallDistance * UnfairCraft.CONFIG.player.fallDamageMultiplier());
		}

		return fallDistance;
	}
}
