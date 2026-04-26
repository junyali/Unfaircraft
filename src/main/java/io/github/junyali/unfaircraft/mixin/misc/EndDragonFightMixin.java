package io.github.junyali.unfaircraft.mixin.misc;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndDragonFight.class)
public abstract class EndDragonFightMixin {
	@Shadow
	@Nullable
	private EnderDragon dragonUUID;

	@Inject(
			method = "tick",
			at = @At("TAIL")
	)
	private void unfaircraft$modifyHealRate(CallbackInfo ci) {
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.END_CRYSTAL.enabled)) {
			return;
		}

		if (this.dragonUUID != null && this.dragonUUID.isAlive()) {
			EnderDragon dragon = this.dragonUUID;
			float currentHealth = dragon.getHealth();
			float maxHealth = dragon.getMaxHealth();
			if (currentHealth < maxHealth) {
				float healMultiplier = UnfairCraftConfig.END_CRYSTAL.healingMultiplier.get().floatValue();
				// ???
			}
		}
	}
}
