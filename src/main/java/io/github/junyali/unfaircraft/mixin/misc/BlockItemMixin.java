package io.github.junyali.unfaircraft.mixin.misc;

import io.github.junyali.unfaircraft.UnfairCraft;
import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin {
	@Inject(
			method = "place",
			at = @At("HEAD"),
			cancellable = true
	)
	private void fakeLagPlacement(BlockPlaceContext context, CallbackInfoReturnable<InteractionResult> cir) {
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.blockInteraction.enabled())) {
			return;
		}

		if (context.getLevel().random.nextFloat() < UnfairCraft.CONFIG.blockInteraction.placeFailChance()) {
			cir.setReturnValue(InteractionResult.FAIL);
		}
	}
}
