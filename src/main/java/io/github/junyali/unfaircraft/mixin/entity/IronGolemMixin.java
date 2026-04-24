package io.github.junyali.unfaircraft.mixin.entity;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IronGolem.class)
public abstract class IronGolemMixin {
	@Inject(
			method = "createAttributes",
			at = @At("RETURN"),
			cancellable = true
	)
	private static void unfaircraft$buffGolemAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.IRON_GOLEM.enabled)) {
			return;
		}

		AttributeSupplier.Builder builder = cir.getReturnValue();
		builder.add(Attributes.MAX_HEALTH, 200.0D)
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
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.IRON_GOLEM.enabled)) {
			return;
		}

		IronGolem self = (IronGolem) (Object) this;
		self.goalSelector.addGoal(0, new FloatGoal(self));
		self.goalSelector.addGoal(1, new MoveTowardsTargetGoal(self, 1.4D, 32.0F));
	}
}
