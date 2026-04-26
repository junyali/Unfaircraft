package io.github.junyali.unfaircraft.mixin.entity;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EndCrystal.class)
public abstract class EndCrystalMixin {
	@Redirect(
			method = "hurt",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;)Lnet/minecraft/world/level/Explosion;"
			)
	)
	private Explosion unfaircraft$modifyCrystalExplosion(Level instance, Entity source, DamageSource damageSource, ExplosionDamageCalculator damageCalculator, double x, double y, double z, float radius, boolean fire, Level.ExplosionInteraction explosionInteraction) {
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.END_CRYSTAL.enabled)) {
			return instance.explode(
					source,
					x,
					y,
					z,
					radius,
					explosionInteraction
			);
		}

		float modifiedPower = UnfairCraftConfig.END_CRYSTAL.explosionRadius.get().floatValue();
		return instance.explode(
				source,
				x,
				y,
				z,
				modifiedPower,
				explosionInteraction
		);
	}
}
