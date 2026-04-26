package io.github.junyali.unfaircraft.mixin.entity;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndCrystal.class)
public abstract class EndCrystalMixin {
	@Inject(
			method = "hurt",
			at = @At("RETURN")
	)
	private void unfaircraft$modifyCrystalExplosion(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValue()) {
			return;
		}

		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.END_CRYSTAL.enabled)) {
			return;
		}

		EndCrystal self = (EndCrystal) (Object) this;
		if (self.level().isClientSide()) {
			return;
		}

		self.level().explode(
				self,
				self.getX(),
				self.getY(),
				self.getZ(),
				UnfairCraftConfig.END_CRYSTAL.explosionPower.get().floatValue(),
				Level.ExplosionInteraction.BLOCK
		);
	}
}
