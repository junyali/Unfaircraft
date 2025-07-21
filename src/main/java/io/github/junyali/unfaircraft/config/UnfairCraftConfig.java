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
			.define("mixins.bed_block_mixin", true);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_SHIELD_ITEM_MIXIN = BUILDER
			.comment("Enable Shield Item mixin")
			.define("mixins.shield_item_mixin", true);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_PLAYER_MIXIN = BUILDER
			.comment("Enable Player mixin")
			.define("mixins.player_mixin", true);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_ITEM_STACK_MIXIN = BUILDER
			.comment("Enable Item Stack mixin")
			.define("mixins.item_stack_mixin", true);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_BOW_ITEM_MIXIN = BUILDER
			.comment("Enable Bow Item mixin")
			.define("mixins.bow_item_mixin", true);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_CHEST_BLOCK_MIXIN = BUILDER
			.comment("Enable Chest Block mixin")
			.define("mixins.chest_block_mixin", true);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_ANVIL_MENU_MIXIN = BUILDER
			.comment("Enable Anvil Menu mixin")
			.define("mixins.anvil_menu_mixin", true);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_MINECART_MIXIN = BUILDER
			.comment("Enable Minecart Mixin")
			.define("mixins.minecat_mixin", true);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_FOOD_ITEM_MIXIN = BUILDER
			.comment("Enable Food Item mixin")
			.define("mixins.food_item_mixin", true);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_CAVE_WORLD_CARVER_MIXIN = BUILDER
			.comment("Enable Cave World Carver mixin")
			.define("mixins.cave_world_carver_mixin", true);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_ORE_FEATURE_MIXIN = BUILDER
			.comment("Enable Ore Feature mixin")
			.define("mixins.ore_feature_mixin", true);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_LOOT_TABLE_MIXIN = BUILDER
			.comment("Enable Loot Table mixin [DOESN'T WORK]")
			.define("mixins.loot_table_mixin", false);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_SAPLING_BLOCK_MIXIN = BUILDER
			.comment("Enable Sapling Block mixin")
			.define("mixins.sapling_block_mixin", true);

	// oh boy here we go...
	public static final ModConfigSpec.ConfigValue<Double> BED_EXPLOSION_CHANCE = BUILDER
			.comment("Chance for beds to explode on interaction")
			.defineInRange("bed_block_mixin.bed_explosion_chance", 0.25, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> BED_EXPLOSION_RADIUS = BUILDER
			.comment("Radius for bed explosion")
			.define("bed_block_mixin.bed_explosion_radius", 5.0);

	public static final ModConfigSpec.ConfigValue<Double> BED_FIRE_CHANCE = BUILDER
			.comment("Chance for beds to set player on fire after interaction resulting in explosion")
			.defineInRange("bed_block_mixin.bed_fire_chance", 0.5, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Integer> BED_FIRE_DURATION = BUILDER
			.comment("Duration of bed fire in ticks")
			.define("bed_block_mixin.bed_fire_duration", 200);

	public static final ModConfigSpec.ConfigValue<Double> SHIELD_FAIL_CHANCE = BUILDER
			.comment("Chance for shields to fail to block attacks")
			.defineInRange("shield_item_mixin.shield_fail_chance", 0.10, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> PLAYER_CRIT_FAIL_CHANCE = BUILDER
			.comment("Chance for player crit attacks to fail")
			.defineInRange("player_mixin.player_crit_fail_chance", 0.25, 0.0, 1.);

	public static final ModConfigSpec.ConfigValue<Double> PLAYER_SELF_ATTACK_CHANCE = BUILDER
			.comment("Chance for player attacks to inflict upon themself")
			.defineInRange("player_mixin.player_self_attack_chance", 0.05, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> PLAYER_ATTACK_EXHAUSTION_CHANCE = BUILDER
			.comment("Chance for player attacks to cause exhaustion upon themself")
			.defineInRange("player_mixin.player_attack_exhaustion_chance", 0.2, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> PLAYER_RANDOM_DROP_CHANCE = BUILDER
			.comment("Chance per tick for a player to randomly drop their held item")
			.defineInRange("player_mixin.player_random_drop_chance", 0.0000005, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> ITEM_DURABILITY_LOSS_CHANCE = BUILDER
			.comment("Chance for tiered items to lose extra durability")
			.defineInRange("item_stack_mixin.item_durability_loss_chance", 0.05, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Integer> ITEM_DURABILITY_DAMAGE_MIN = BUILDER
			.comment("Minimum additional durability damage on items")
			.define("item_stack_mixin.item_durability_damage_min", 2);

	public static final ModConfigSpec.ConfigValue<Integer> ITEM_DURABILITY_DAMAGE_MAX = BUILDER
			.comment("Maximum additional durability damage on items")
			.define("item_stack_mixin.item_durability_damage_max", 20);

	public static final ModConfigSpec.ConfigValue<Double> BOW_WONKY_CHANCE = BUILDER
			.comment("Chance for player arrows fired from bows to deviate in direction")
			.defineInRange("bow_item_mixin.bow_wonky_chance", 0.25, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> BOW_PROJECTILE_DEVIATION = BUILDER
			.comment("How much player-fired arrows can deviate from their intended path")
			.define("bow_item_mixin.bow_projectile_deviation", 0.8);

	public static final ModConfigSpec.ConfigValue<Double> BOW_MISFIRE_CHANCE = BUILDER
			.comment("Chance for player-fired bows to not shoot")
			.defineInRange("bow_item_mixin.bow_misfire_chance", 0.5, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> BOW_BACKFIRE_CHANCE = BUILDER
			.comment("Chance for player-fired bows to damage themself when misfiring")
			.defineInRange("bow_item_mixin.bow_backfire_chance", 0.4, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> BOW_BACKFIRE_DAMAGE_MIN = BUILDER
			.comment("Minimum damage from bow backfire")
			.define("bow_item_mixin.bow_backfire_damage_min", 2.0);

	public static final ModConfigSpec.ConfigValue<Double> BOW_BACKFIRE_DAMAGE_MAX = BUILDER
			.comment("Maximum damage from bow backfire")
			.define("bow_item_mixin.bow_backfire_damage_max", 20.0);

	public static final ModConfigSpec.ConfigValue<Double> CHEST_EAT_CHANCE = BUILDER
			.comment("Chance for a chest to eat items when opened")
			.defineInRange("chest_block_entity_mixin.chest_eat_chance", 0.15, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Integer> CHEST_EAT_ITEM_MIN = BUILDER
			.comment("Minimum number of items a chest will eat at once")
			.define("chest_block_entity_mixin.chest_eat_item_min", 1);

	public static final ModConfigSpec.ConfigValue<Integer> CHEST_EAT_ITEM_MAX = BUILDER
			.comment("Maximum number of items a chest will eat at once")
			.define("chest_block_entity_mixin.chest_eat_item_max", 32);

	public static final ModConfigSpec.ConfigValue<Double> ANVIL_INSTANT_BREAK_CHANCE = BUILDER
			.comment("Chance for anvils to break completely in one use")
			.defineInRange("anvil_menu_mixin.anvil_instance_break_chance", 0.1, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> ANVIL_COST_INCREASE_CHANCE = BUILDER
			.comment("Chance for anvil repair costs to be multiplied")
			.defineInRange("anvil_menu_mixin.anvil_cost_increase_chance", 0.5, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> ANVIL_COST_MULTIPLIER_MIN = BUILDER
			.comment("Minimum multiplier for increased anvil repair costs")
			.define("anvil_menu_mixin.anvil_cost_multiplier_min", 2.0);

	public static final ModConfigSpec.ConfigValue<Double> ANVIL_COST_MULTIPLIER_MAX = BUILDER
			.comment("Maximum multiplier for increased anvil repair costs")
			.define("anvil_menu_mixin.anvil_cost_multiplier_max", 6.0);

	public static final ModConfigSpec.ConfigValue<Double> MINECART_SLOWDOWN_FACTOR = BUILDER
			.comment("Factor to reduce minecart speed, lower = slow")
			.defineInRange("minecart_mixin.minecart_slowdown_factor", 0.5, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> MINECART_STOP_CHANCE = BUILDER
			.comment("Chance for minecarts to randomly stop each tick")
			.defineInRange("minecart_mixin.minecart_stop_chance", 0.05, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> MINECART_REVERSE_CHANCE = BUILDER
			.comment("Chance for minecarts to randomly reverse direction each tick")
			.defineInRange("minecart_mixin.minecart_reverse_chance", 0.01, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> FOOD_FAIL_CHANCE = BUILDER
			.comment("Chance for food to completely fail to restore hunger")
			.defineInRange("food_item_mixin.food_fail_chance", 0.05, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> EXTRA_LAVA_POCKET_CHANCE = BUILDER
			.comment("Chance for extra lava pockets to generate in caves")
			.defineInRange("cave_world_carver_mixin.extra_lava_pocket_chance", 0.8, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> ORE_DEFAULT_REPLACEMENT_CHANCE = BUILDER
			.comment("Chance to replace ores in ore veins by default")
			.defineInRange("ore_feature_mixin.ore_default_replacement_chance", 0.3, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> ORE_GOLD_REPLACEMENT_CHANCE = BUILDER
			.comment("Chance to replace ores in gold ore veins")
			.defineInRange("ore_feature_mixin.ore_gold_replacement_chance", 0.4, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> ORE_EMERALD_REPLACEMENT_CHANCE = BUILDER
			.comment("Chance to replace ores in emerald ore veins")
			.defineInRange("ore_feature_mixin.ore_emerald_replacement_chance", 0.5, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> ORE_DIAMOND_REPLACEMENT_CHANCE = BUILDER
			.comment("Chance to replace ores in diamond ore veins")
			.defineInRange("ore_feature_mixin.ore_diamond_replacement_chance", 0.6, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> ORE_ANCIENT_DEBRIS_REPLACEMENT_CHANCE = BUILDER
			.comment("Chance to replace ores in ancient debris ore veins")
			.defineInRange("ore_feature_mixin.ore_ancient_debris_replacement_chance", 0.5, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> LOOT_TABLE_TROLL_CHANCE = BUILDER
			.comment("Chance for loot tables to be replaced with troll loot")
			.defineInRange("loot_table_mixin.loot_table_troll_chance", 0.9, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> SAPLING_DEATH_CHANCE = BUILDER
			.comment("Chance for saplings to 'die' every tick")
			.defineInRange("sapling_block_mixin.sapling_death_chance", 0.005, 0.0, 1.0);

	public static final ModConfigSpec SPEC = BUILDER.build();
}
