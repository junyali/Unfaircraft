package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EnderMan.class)
public abstract class EndermanMixin {
	@Shadow
	public abstract void setTarget(LivingEntity target);

	@Inject(
			method = "aiStep",
			at = @At("HEAD")
	)
	private void proximityAggro(CallbackInfo ci) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_ENDERMAN_MIXIN.get()) {
			return;
		}

		EnderMan enderman = (EnderMan) (Object) this;
		if (enderman.getTarget() == null && !enderman.level().isClientSide) {
			double range = UnfairCraftConfig.ENDERMAN_PROXIMITY_AGGRO_RANGE.get();
			double chance = UnfairCraftConfig.ENDERMAN_PROXIMITY_AGGRO_CHANCE.get();

			List<Player> nearbyPlayers = enderman.level().getEntitiesOfClass(
					Player.class,
					enderman.getBoundingBox().inflate(range),
					player -> !player.isSpectator() && !player.isCreative()
			);

			if (!nearbyPlayers.isEmpty() && enderman.getRandom().nextDouble() < chance) {
				Player targetPlayer = nearbyPlayers.get(enderman.getRandom().nextInt(nearbyPlayers.size()));
				this.setTarget(targetPlayer);
			}
		}
	}
}
