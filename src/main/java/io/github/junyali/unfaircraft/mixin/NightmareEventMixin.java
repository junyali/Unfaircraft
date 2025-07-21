package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerLevel.class)
public class NightmareEventMixin {
	@Inject(
			method = "tick",
			at = @At("TAIL")
	)
	private void triggerNightmareEvent(BooleanSupplier hasTimeLeft, CallbackInfo ci) {
		if (!UnfairCraftConfig.ENABLE_NIGHTMARE_MODE.get()) {
			return;
		}

		ServerLevel level = (ServerLevel) (Object) this;

		if (level.getGameTime() % 20 != 0 ) return;

		if (level.random.nextFloat() < UnfairCraftConfig.NIGHTMARE_EVENT_CHANCE.get().floatValue()) {
			// trigger random event idk
		}
	}
}
