package io.github.junyali.unfaircraft.mixin.misc;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayerGameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {
	private boolean enabled = UnfairCraftConfig.isEnabled(UnfairCraftConfig.BLOCK_INTERACTION.enabled);

	@Shadow
	protected ServerLevel level;

	@Inject(
			method = "destroyBlock",
			at = @At("HEAD"),
			cancellable = true
	)
	private void fakeLagDestroyBlock(BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
		if (!enabled) {
			return;
		}

		if (level.random.nextFloat() < UnfairCraftConfig.BLOCK_INTERACTION.breakFailChance.get().floatValue()) {
			cir.setReturnValue(false);
		}
	}
}
