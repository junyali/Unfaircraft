package io.github.junyali.unfaircraft.mixin.misc;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerLevel.class)
public abstract class WeatherMixin {
	@Inject(
			method = "tick",
			at = @At("TAIL")
	)
	private void unfaircraft$weatherEscalation(BooleanSupplier hasTimeLeft, CallbackInfo ci) {
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.WEATHER.enabled)) {
			return;
		}

		ServerLevel self = (ServerLevel) (Object) this;
		if (self.isClientSide()) {
			return;
		}

		ServerLevelData serverLevelData = (ServerLevelData) self.getLevelData();
		RandomSource random = self.getRandom();

		if (serverLevelData.isRaining() && !serverLevelData.isThundering()) {
			if (random.nextFloat() < UnfairCraftConfig.WEATHER.escalateThunderChance.get().floatValue()) {
				serverLevelData.setThunderTime(random.nextIntBetweenInclusive(12000, 24000));
				serverLevelData.setThundering(true);
			}
		}

		if (serverLevelData.isThundering()) {
			int currentThunderTime = serverLevelData.getThunderTime();
			if (currentThunderTime < 1000 && random.nextFloat() < UnfairCraftConfig.WEATHER.remainThunderChance.get().floatValue()) {
				serverLevelData.setThunderTime(random.nextIntBetweenInclusive(6000, 12000));
			}
		}
	}
}
