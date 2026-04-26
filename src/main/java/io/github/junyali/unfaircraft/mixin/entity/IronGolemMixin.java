package io.github.junyali.unfaircraft.mixin.entity;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IronGolem.class)
public abstract class IronGolemMixin {
	@Inject(
			method = "createAttributes",
			at = @At("RETURN")
	)
	private static void unfaircraft$buffGolemAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
		cir.getReturnValue().add(Attributes.WATER_MOVEMENT_EFFICIENCY, 0.0);
	}

	@Inject(
			method = "registerGoals",
			at = @At("TAIL")
	)
	private void unfaircraft$addSwimGoal(CallbackInfo ci) {
		try {
			if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.IRON_GOLEM.enabled)) {
				return;
			}
		} catch (Exception e) {
			return;
		}

		IronGolem self = (IronGolem) (Object) this;
		self.goalSelector.addGoal(0, new FloatGoal(self));
		self.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(self, Player.class, true));
	}
}
