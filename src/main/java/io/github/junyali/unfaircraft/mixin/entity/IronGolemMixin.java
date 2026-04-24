package io.github.junyali.unfaircraft.mixin.entity;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IronGolem.class)
public abstract class IronGolemMixin {
	@Redirect(
			method = "createAttributes",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/entity/Mob;createMobAttributes()Lnet/minecraft/world/entity/ai/attributes/AttributeSupplier$Builder;"
			)
	)
	private static AttributeSupplier.Builder unfaircraft$buffGolemAttributes() {
		return IronGolem.createAttributes()
				.add(Attributes.MAX_HEALTH, 200.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.35D)
				.add(Attributes.ATTACK_DAMAGE, 20.0D)
				.add(Attributes.ATTACK_KNOCKBACK, 2.0D)
				.add(Attributes.FOLLOW_RANGE, 32.0D);
	}

	@Inject(
			method = "registerGoals",
			at = @At("TAIL")
	)
	private void unfaircraft$addSwimGoal(CallbackInfo ci) {
		IronGolem self = (IronGolem) (Object) this;
		self.goalSelector.addGoal(0, new FloatGoal(self));
		self.goalSelector.addGoal(1, new MoveTowardsTargetGoal(self, 1.4D, 32.0F));
	}
}
