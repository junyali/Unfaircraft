package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(AbstractSkeleton.class)
public class SkeletonMixin {
	@ModifyVariable(
			method = "performRangedAttack",
			at = @At("STORE"),
			ordinal = 0
	)
	private double improveAccuracyX(double inaccuracy) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_SKELETON_MIXIN.get()) {
			return inaccuracy;
		}

		return inaccuracy * UnfairCraftConfig.SKELETON_ACCURACY_MULTIPLIER.get().floatValue();
	}

	@ModifyVariable(
			method = "performRangedAttack",
			at = @At("STORE"),
			ordinal = 1
	)
	private double improveAccuracyZ(double inaccuracy) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_SKELETON_MIXIN.get()) {
			return inaccuracy;
		}

		return inaccuracy * UnfairCraftConfig.SKELETON_ACCURACY_MULTIPLIER.get().floatValue();
	}

	@ModifyVariable(
			method = "performRangedAttack",
			at = @At("STORE"),
			ordinal = 2
	)
	private double improveAccuracyY(double inaccuracy) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_SKELETON_MIXIN.get()) {
			return inaccuracy;
		}

		return inaccuracy * UnfairCraftConfig.SKELETON_ACCURACY_MULTIPLIER.get().floatValue();
	}
}
