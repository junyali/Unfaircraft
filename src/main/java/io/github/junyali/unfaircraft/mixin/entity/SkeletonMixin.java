package io.github.junyali.unfaircraft.mixin.entity;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(AbstractSkeleton.class)
public class SkeletonMixin {
	private boolean enabled = UnfairCraftConfig.isEnabled(UnfairCraftConfig.SKELETON.enabled);

	@ModifyVariable(
			method = "performRangedAttack",
			at = @At("STORE"),
			ordinal = 0
	)
	private double improveAccuracyX(double inaccuracy) {
		if (!enabled) {
			return inaccuracy;
		}

		return inaccuracy / UnfairCraftConfig.SKELETON.accuracyMultiplier.get().floatValue();
	}

	@ModifyVariable(
			method = "performRangedAttack",
			at = @At("STORE"),
			ordinal = 1
	)
	private double improveAccuracyZ(double inaccuracy) {
		if (!enabled) {
			return inaccuracy;
		}

		return inaccuracy / UnfairCraftConfig.SKELETON.accuracyMultiplier.get().floatValue();
	}

	@ModifyVariable(
			method = "performRangedAttack",
			at = @At("STORE"),
			ordinal = 2
	)
	private double improveAccuracyY(double inaccuracy) {
		if (!enabled) {
			return inaccuracy;
		}

		return inaccuracy / UnfairCraftConfig.SKELETON.accuracyMultiplier.get().floatValue();
	}
}
