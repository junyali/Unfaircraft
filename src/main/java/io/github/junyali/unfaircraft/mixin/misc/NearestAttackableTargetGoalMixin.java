package io.github.junyali.unfaircraft.mixin.misc;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(NearestAttackableTargetGoal.class)
public class NearestAttackableTargetGoalMixin {
	@ModifyArg(
			method = "<init>*",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/entity/ai/goal/target/NearestAttackableTargetGoal;<init>(Lnet/minecraft/world/entity/Mob;Ljava/lang/Class;IZZLjava/util/function/Predicate;)V"
			),
			index = 2
	)
	private static int increaseDetectionRange(int originalRange) {
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.MOB_DETECTION.enabled)) {
			return originalRange;
		}

		return (int) (originalRange * UnfairCraftConfig.MOB_DETECTION.rangeMultiplier.get().floatValue());
	}
}
