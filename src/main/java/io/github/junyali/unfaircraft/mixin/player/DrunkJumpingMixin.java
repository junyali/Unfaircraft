package io.github.junyali.unfaircraft.mixin.player;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class DrunkJumpingMixin {
	@Inject(
			method = "jumpFromGround",
			at = @At("TAIL")
	)
	private void modifyJumpPower(CallbackInfo ci) {
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.DRUNK_JUMPING.enabled)) {
			return;
		}

		LivingEntity entity = (LivingEntity) (Object) this;

		if (entity instanceof Player) {

			Vec3 motion = entity.getDeltaMovement();
			float jumpModification = 1.0f;

			int random = entity.level().random.nextInt(5);
			switch (random) {
				case 1 -> jumpModification = 0.3f;
				case 2 -> jumpModification = 0.8f;
				case 3 -> jumpModification = 1.2f;
				case 4 -> jumpModification = 1.5f;
				case 5 -> jumpModification = 2.0f;
			}

			entity.setDeltaMovement(motion.x, motion.y * jumpModification, motion.z);
		}
	}
}
