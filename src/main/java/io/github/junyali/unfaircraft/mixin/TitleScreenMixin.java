package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.UnfairCraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
	@Shadow
	@Final
	@Mutable
	private static ResourceLocation MINECRAFT_LOGO;

	@Unique
	private static final ResourceLocation UNFAIRCRAFT_LOGO = ResourceLocation.fromNamespaceAndPath(UnfairCraft.MODID, "textures/gui/title/minecraft.png");

	@Inject(
			method = "<clinit>",
			at = @At("TAIL")
	)
	private static void replaceMineacraftLogo(CallbackInfo ci) {
		MINECRAFT_LOGO = UNFAIRCRAFT_LOGO;
	}
}
