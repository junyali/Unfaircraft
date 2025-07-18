package io.github.junyali.unfaircraft.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class UnfairCraftConfig {
	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_UNFAIR_MODE = BUILDER
			.comment("Master toggle for UnfairCraft")
			.define("general.enable_unfair_mode", true);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_BED_BLOCK_MIXIN = BUILDER
			.comment("Enable Bed Block mixin")
			.define("bed_block_mixin", true);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_SHIELD_ITEM_MIXIN = BUILDER
			.comment("Enable Shield Item mixin")
			.define("shield_item_mixin", true);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_PLAYER_MIXIN = BUILDER
			.comment("Enable Player mixin")
			.define("player_mixin", true);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_ITEM_STACK_MIXIN = BUILDER
			.comment("Enable Item Stack mixin")
			.define("item_stack_mixin", true);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_BOW_ITEM_MIXIN = BUILDER
			.comment("Enable Bow Item mixin")
			.define("bow_item_mixin", true);

	public static final ModConfigSpec SPEC = BUILDER.build();
}
