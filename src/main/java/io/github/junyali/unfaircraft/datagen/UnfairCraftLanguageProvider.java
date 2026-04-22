package io.github.junyali.unfaircraft.datagen;

import io.github.junyali.unfaircraft.UnfairCraft;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class UnfairCraftLanguageProvider extends LanguageProvider {
	public UnfairCraftLanguageProvider(PackOutput output, String locale) {
		super(output, UnfairCraft.MODID, locale);
	}

	@Override
	protected void addTranslations() {

	}
}
