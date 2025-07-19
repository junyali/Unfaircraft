package io.github.junyali.unfaircraft.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class UnfairCraftConfig {
	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

	// le master
	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_UNFAIR_MODE = BUILDER
			.comment("Master toggle for UnfairCraft")
			.define("general.enable_unfair_mode", true);

	// individual mixin toggles
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

	// oh boy here we go...
	public static final ModConfigSpec.ConfigValue<Double> BED_EXPLOSION_CHANCE = BUILDER
			.comment("Chance for beds to explode on interaction (default: 0.25; range: 0.0 to 1.0)")
			.defineInRange("bed_explosion_chance", 0.25, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> BED_EXPLOSION_RADIUS = BUILDER
			.comment("Radius for bed explosion (default: 5.0)")
			.define("bed_explosion_radius", 5.0);

	public static final ModConfigSpec.ConfigValue<Double> BED_FIRE_CHANCE = BUILDER
			.comment("Chance for beds to set player on fire after interaction resulting in explosion (default: 0.5; range: 0.0 to 1.0)")
			.defineInRange("bed_fire_chance", 0.5, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Integer> BED_FIRE_DURATION = BUILDER
			.comment("Duration of bed fire in ticks (default: 200)")
			.define("bed_fire_duration", 200);

	public static final ModConfigSpec.ConfigValue<Double> SHIELD_FAIL_CHANCE = BUILDER
			.comment("Chance for shields to fail to block attacks (default: 0.10; range: 0.0 to 1.0)")
			.defineInRange("shield_fail_chance", 0.10, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> PLAYER_CRIT_FAIL_CHANCE = BUILDER
			.comment("Chance for player crit attacks to fail (default: 0.25; range: 0.0 to 1.0)")
			.defineInRange("player_crit_fail_chance", 0.25, 0.0, 1.);

	public static final ModConfigSpec.ConfigValue<Double> PLAYER_SELF_ATTACK_CHANCE = BUILDER
			.comment("Chance for player attacks to inflict upon themself (default: 0.05; range: 0.0 to 1.0)")
			.defineInRange("player_self_attack_chance", 0.05, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> PLAYER_ATTACK_EXHAUSTION_CHANCE = BUILDER
			.comment("Chance for player attacks to cause exhaustion upon themself (default: 0.2; range: 0.0 to 1.0)")
			.defineInRange("player_attack_exhaustion_chance", 0.2, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> ITEM_DURABILITY_LOSS_CHANCE = BUILDER
			.comment("Chance for tiered items to lose extra durability (default: 0.05; range: 0.0 to 1.0)")
			.defineInRange("item_durability_loss_chance", 0.05, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Integer> ITEM_DURABILITY_DAMAGE_MIN = BUILDER
			.comment("Minimum additional durability damage on items (default: 2)")
			.define("item_durability_damage_min", 2);

	public static final ModConfigSpec.ConfigValue<Integer> ITEM_DURABILITY_DAMAGE_MAX = BUILDER
			.comment("Maximum additional durability damage on items (default: 20)")
			.define("item_durability_damage_max", 20);

	public static final ModConfigSpec.ConfigValue<Double> BOW_WONKY_CHANCE = BUILDER
			.comment("Chance for player arrows fired from bows to deviate in direction (default: 0.25; range: 0.0 to 1.0)")
			.defineInRange("bow_wonky_chance", 0.25, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> BOW_PROJECTILE_DEVIATION = BUILDER
			.comment("How much player-fired arrows can deviate from their intended path (default: 0.8)")
			.define("bow_projectile_deviation", 0.8);

	public static final ModConfigSpec.ConfigValue<Double> BOW_MISFIRE_CHANCE = BUILDER
			.comment("Chance for player-fired bows to not shoot (default: 0.5; range: 0.0 to 1.0)")
			.defineInRange("bow_misfire_chance", 0.5, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> BOW_BACKFIRE_CHANCE = BUILDER
			.comment("Chance for player-fired bows to damage themself when misfiring (default: 0.4; range: 0.0 to 1.0)")
			.defineInRange("bow_backfire_chance", 0.4, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> BOW_BACKFIRE_DAMAGE_MIN = BUILDER
			.comment("Minimum damage from bow backfire (default: 2.0")
			.define("bow_backfire_damage_min", 2.0);

	public static final ModConfigSpec.ConfigValue<Double> BOW_BACKFIRE_DAMAGE_MAX = BUILDER
			.comment("Maximum damage from bow backfire (default: 20.0")
			.define("bow_backfire_damage_max", 20.0);

	public static final ModConfigSpec SPEC = BUILDER.build();
}
