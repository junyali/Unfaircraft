package io.github.junyali.unfaircraft.mixin.misc;

import io.github.junyali.unfaircraft.UnfairCraft;
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
	@Shadow
	protected ServerLevel level;

	@Inject(
			method = "destroyBlock",
			at = @At("HEAD"),
			cancellable = true
	)
	private void fakeLagDestroyBlock(BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.blockInteraction.enabled())) {
			return;
		}

		if (level.random.nextFloat() < UnfairCraft.CONFIG.blockInteraction.breakFailChance()) {
			cir.setReturnValue(false);
		}
	}
}
