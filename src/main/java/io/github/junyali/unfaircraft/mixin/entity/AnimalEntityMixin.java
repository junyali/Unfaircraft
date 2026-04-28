package io.github.junyali.unfaircraft.mixin.entity;

import io.github.junyali.unfaircraft.UnfairCraft;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class AnimalEntityMixin {
	@Inject(
			method = "hurt",
			at = @At("RETURN")
	)
	private void unfaircraft$passiveRetaliation(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValue()) {
			return;
		}

		LivingEntity self = (LivingEntity) (Object) this;
		if (self instanceof Animal animal) {
			if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.mob.passiveRetaliationEnabled())) {
				return;
			}

			if (animal.level().isClientSide()) {
				return;
			}

			Entity attacker = source.getEntity();
			if (!(attacker instanceof Player player)) {
				return;
			}

			animal.setTarget(player);
			AABB alertBox = self.getBoundingBox().inflate(8.0);
			animal.level().getEntitiesOfClass(animal.getClass(), alertBox, nearby ->
					nearby != self && nearby.getTarget() == null
			).forEach(nearby -> nearby.setTarget(player));
		}
	}

	@Inject(
			method = "createLivingAttributes",
			at = @At("RETURN")
	)
	private static void unfaircraft$addAttackAttribute(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
		cir.getReturnValue().add(Attributes.ATTACK_DAMAGE, 1.0f);
	}
}
