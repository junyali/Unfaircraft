package io.github.junyali.unfaircraft;

import io.github.junyali.unfaircraft.config.UnfaircraftConfig;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnfairCraft implements ModInitializer {
	public static final String MOD_ID = "unfaircraft";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final UnfaircraftConfig CONFIG = UnfaircraftConfig.createAndLoad();

	@Override
	public void onInitialize() {
	}
}