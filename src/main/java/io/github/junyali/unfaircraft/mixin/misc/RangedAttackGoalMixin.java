package io.github.junyali.unfaircraft.mixin.misc;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RangedAttackGoal.class)
public class RangedAttackGoalMixin {
	@Shadow
	private int attackTime;

	@Final
	@Shadow
	private Mob mob;

	@Inject(
			method = "tick",
			at = @At("HEAD")
	)
	private void increaseAttackSpeed(CallbackInfo ci) {
		RangedAttackGoal goal = (RangedAttackGoal) (Object) this;

		if (this.mob instanceof AbstractSkeleton) {
			if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.SKELETON.enabled)) {
				return;
			}

			if (this.attackTime > 0) {
				int speedIncrease = UnfairCraftConfig.SKELETON.attackSpeedIncrease.get();
				this.attackTime = Math.max(0, this.attackTime - speedIncrease);
			}
		}
	}
}
