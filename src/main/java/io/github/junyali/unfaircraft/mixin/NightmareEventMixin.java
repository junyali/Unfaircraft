package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.BooleanSupplier;

@Mixin(ServerLevel.class)
public class NightmareEventMixin {
	@Unique
	private void unfaircraft$triggerRandomEvent(ServerLevel level) {
		List<ServerPlayer> players = level.players();
		if (players.isEmpty()) return;

		Player randomPlayer = players.get(level.random.nextInt(players.size()));
		int eventType = level.random.nextInt(0);

		switch (eventType) {
			case 0 -> unfaircraft$tntRain(level, randomPlayer);
		}
	}

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
			unfaircraft$triggerRandomEvent(level);
		}
	}

	@Unique
	private void unfaircraft$tntRain(ServerLevel level, Player player) {
		BlockPos playerPos = player.blockPosition();

		for (int i = 0; i < 8 + level.random.nextInt(8); i++) {
			BlockPos tntPos = playerPos.offset(
					level.random.nextInt(32) - 16,
					16 + level.random.nextInt(16),
					level.random.nextInt(32) - 16
			);

			PrimedTnt tnt = new PrimedTnt(level, tntPos.getX(), tntPos.getY(), tntPos.getZ(), null);
			tnt.setFuse(20 + level.random.nextInt(40));
			level.addFreshEntity(tnt);
		}
	}
}
