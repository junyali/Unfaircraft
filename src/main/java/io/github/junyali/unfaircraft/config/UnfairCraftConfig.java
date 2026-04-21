package io.github.junyali.unfaircraft.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class UnfairCraftConfig {
	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

	// le master
	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_UNFAIR_MODE = BUILDER
			.comment("Master toggle for UnfairCraft")
			.define("general.enable_unfair_mode", true);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_NIGHTMARE_MODE = BUILDER
			.comment("Master toggle for Nightmare Mode")
			.define("general.enable_nightmare_mode", false);

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

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_FARM_BLOCK_MIXIN = BUILDER
			.comment("Enable Farm Block mixin")
			.define("mixins.farm_block_mixin", true);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_NEAREST_ATTACKABLE_TARGET_GOAL_MIXIN = BUILDER
			.comment("Enable Nearest Attackable Target Goal mixin")
			.define("mixins.nearest_attackable_target_goal_mixin", true);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_BLOCK_ITEM_MIXIN = BUILDER
			.comment("Enable Block Item mixin")
			.define("mixins.block_item_mixin", true);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_BUCKET_ITEM_MIXIN = BUILDER
			.comment("Enable Bucket Item mixin")
			.define("mixins.bucket_item_mixin", true);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_LIVING_ENTITY_MIXIN = BUILDER
			.comment("Enable Living Entity mixin")
			.define("mixins.living_entity_mixin", true);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_ARMOR_MIXIN = BUILDER
			.comment("Enable Armor mixin")
			.define("mixins.armor_mixin", true);

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

	public static final ModConfigSpec.ConfigValue<Double> PLAYER_FALL_DAMAGE_DISTANCE = BUILDER
			.comment("Minimum distance for player to take fall damage")
			.define("player_mixin.player_fall_damage_distance", 1.5);

	public static final ModConfigSpec.ConfigValue<Double> PLAYER_FALL_DAMAGE_MULTIPLIER = BUILDER
			.comment("Multiplier for increased fall damage")
			.define("player_mixin.player_fall_damage_multiplier", 3.0);

	public static final ModConfigSpec.ConfigValue<Double> PLAYER_PICKUP_FAIL_CHANCE = BUILDER
			.comment("Chance for items picked up by the player to vanish")
			.defineInRange("player_mixin.player_pickup_fail_chance", 0.01, 0.0, 1.0);

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

	public static final ModConfigSpec.ConfigValue<Double> FARMLAND_REVERT_CHANCE = BUILDER
			.comment("Chance for farmland blocks to revert every tick")
			.defineInRange("farm_block_mixin.farmland_revert_chance", 0.005, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> MOB_DETECTION_RANGE_MULTIPLIER = BUILDER
			.comment("Multiplier for increased mob detection range")
			.define("nearest_attackable_target_goal_mixin.mob_detection_range_multiplier", 4.0);

	public static final ModConfigSpec.ConfigValue<Double> BLOCK_PLACE_FAIL_CHANCE = BUILDER
			.comment("Chance for block placements to fail")
			.defineInRange("block_item_mixin.block_place_fail_chance", 0.05, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> BLOCK_BREAK_FAIL_CHANCE = BUILDER
			.comment("Chance for block breaking to fail")
			.defineInRange("block_item_mixin.block_break_fail_chance", 0.05, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> WATER_BUCKET_FAIL_CHANCE = BUILDER
			.comment("Chance for water bucket placement to fail")
			.defineInRange("bucket_item_mixin.water_bucket_fail_chance", 0.3, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> ARMOR_PROTECTION_FAIL_CHANCE = BUILDER
			.comment("Chance for armor to provide no protection when hit")
			.defineInRange("armor_mixin.armor_protection_fail_chance", 0.15, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> ARMOR_DURABILITY_LOSS_CHANCE = BUILDER
			.comment("Chance for armor to extra durability damage when hit")
			.defineInRange("armor_mixin.armor_durability_loss_chance", 0.20, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Integer> ARMOR_DURABILITY_DAMAGE_MIN = BUILDER
			.comment("Minimum extra durability damage to armor")
			.defineInRange("armor_mixin.armor_durability_damage_min", 2, 1, 100);

	public static final ModConfigSpec.ConfigValue<Integer> ARMOR_DURABILITY_DAMAGE_MAX = BUILDER
			.comment("Maximum extra durability damage to armor")
			.defineInRange("armor_mixin.armor_durability_damage_max", 5, 1, 100);

	public static final ModConfigSpec.ConfigValue<Double> NIGHTMARE_EVENT_CHANCE = BUILDER
			.comment("Chance for a nightmare event to happen")
			.defineInRange("nightmare_event_mixin.nightmare_event_chance", 0.0001, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> TOTEM_FAIL_CHANCE = BUILDER
			.comment("Chance for totems of undying to fail to save the player")
			.defineInRange("living_entity_mixin.totem_fail_chance", 0.15, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> PLAYER_KNOCKBACK_CHANCE = BUILDER
			.comment("Chance for player to be knocked back when hitting a mob")
			.defineInRange("living_entity_mixin.player_knockback_chance", 0.1, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Double> PLAYER_KNOCKBACK_MULTIPLIER = BUILDER
			.comment("Multiplier for knockback effect on player")
			.defineInRange("living_entity_mixin.player_knockback_multiplier", 1.5, 1.0, 100.0);

	public static final ModConfigSpec.ConfigValue<Double> DAMAGE_REFLECTION_CHANCE = BUILDER
			.comment("Chance for damage to be reflected back to player")
			.defineInRange("living_entity_mixin.damage_reflection_chance", 0.1, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Integer> DAMAGE_REFLECTION_PERCENTAGE_MIN = BUILDER
			.comment("Minimum percentage for damage to be reflected back to player")
			.defineInRange("living_entity_mixin.damage_reflection_percentage_min", 3, 1, 100);

	public static final ModConfigSpec.ConfigValue<Integer> DAMAGE_REFLECTION_PERCENTAGE_MAX = BUILDER
			.comment("Maximum percentage for damage to be reflected back to player")
			.defineInRange("living_entity_mixin.damage_reflection_percentage_max", 5, 1, 100);

	public static final ModConfigSpec.ConfigValue<Boolean> DAMAGE_REFLECTION_IGNORE_THORNS = BUILDER
			.comment("Ignore thorns when reflecting damage back to player")
			.define("living_entity_mixin.damage_reflection_ignore_thorns", true);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_CREEPER_MIXIN = BUILDER
			.comment("Enable Creeper mixin")
			.define("mixins.creeper_mixin", true);

	public static final ModConfigSpec.ConfigValue<Double> CREEPER_FUSE_SPEED_MULTIPLIER = BUILDER
			.comment("Multiplier for creeper fuse speed")
			.defineInRange("creeper_mixin.creeper_fuse_speed_multiplier", 5.0, 1.0, 10.0);

	public static final ModConfigSpec.ConfigValue<Double> CREEPER_EXPLOSION_RADIUS_MULTIPLIER = BUILDER
			.comment("Multiplier for creeper explosion radius")
			.defineInRange("creeper_mixin.creeper_explosion_radius_multiplier", 1.5, 1.0, 5.0);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_SKELETON_MIXIN = BUILDER
			.comment("Enable Skeleton mixin")
			.define("mixins.skeleton_mixin", true);

	public static final ModConfigSpec.ConfigValue<Double> SKELETON_ACCURACY_MULTIPLIER = BUILDER
			.comment("Multiplier for skeleton accuracy")
			.defineInRange("skeleton_mixin.skeleton_accuracy_multiplier", 0.1, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Integer> SKELETON_ATTACK_SPEED_INCREASE = BUILDER
			.comment("Number of ticks to reduce from skeleton attack cooldown")
			.defineInRange("skeleton_mixin.skeleton_attack_speed_increase", 2, 0, 10);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_DRUNK_JUMPING = BUILDER
			.comment("Enable drunk jumping")
			.define("living_entity_mixin.drunk_jumping", false);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_ENDERMAN_MIXIN = BUILDER
			.comment("Enable Enderman mixin")
			.define("mixins.enderman_mixin", true);

	public static final ModConfigSpec.ConfigValue<Double> ENDERMAN_PROXIMITY_AGGRO_RANGE = BUILDER
			.comment("Range in blocks for enderman proximity aggro")
			.defineInRange("enderman_mixin.enderman_proximity_aggro_range", 16.0, 1.0, 64.0);

	public static final ModConfigSpec.ConfigValue<Double> ENDERMAN_PROXIMITY_AGGRO_CHANCE = BUILDER
			.comment("Chance per tick for enderman to become aggressive when player is nearby")
			.defineInRange("enderman_mixin.enderman_proximity_aggro_chance", 1.0, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_POTION_ITEM_MIXIN = BUILDER
			.comment("Enable Potion Item mixin")
			.define("mixins.potion_item", true);

	public static final ModConfigSpec.ConfigValue<Double> POTION_BACKFIRE_CHANCE = BUILDER
			.comment("Chance for potion effects to backfire on player")
			.defineInRange("potion_item_mixin.potion_backfire_chance", 0.1, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_MOB_REGEN_MIXIN = BUILDER
			.comment("Enable Mob Regen mixin")
			.define("mixins.mob_regen_mixin", true);

	public static final ModConfigSpec.ConfigValue<Integer> MOB_REGEN_DELAY = BUILDER
			.comment("Ticks to wait after damage before mob regen starts")
			.defineInRange("mob_regen_mixin.mob_regen_delay", 300, 0, 72000);

	public static final ModConfigSpec.ConfigValue<Integer> MOB_REGEN_RATE = BUILDER
			.comment("How often to regenerate in ticks")
			.defineInRange("mob_regen_mixin.mob_regen_rate", 20, 0, 1200);

	public static final ModConfigSpec.ConfigValue<Double> MOB_REGEN_AMOUNT = BUILDER
			.comment("Health restored per regen tick")
			.defineInRange("mob_regen_mixin.mob_regen_amount", 0.5, 0.0, 10.0);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_SET_FIRE_MIXIN = BUILDER
			.comment("Enable Set Fire mixin")
			.define("mixins.set_fire_mixin", true);

	public static final ModConfigSpec.ConfigValue<Integer> SET_FIRE_RADIUS = BUILDER
			.comment("Radius to check for fire source blocks around the player")
			.defineInRange("set_fire_mixin.set_fire_radius", 3, 1, 16);

	public static final ModConfigSpec.ConfigValue<Double> SET_FIRE_CHANCE = BUILDER
			.comment("Chance per tick for a player to be set on fire near a heat source")
			.defineInRange("set_fire_mixin.set_fire_chance", 0.005, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Integer> SET_FIRE_INITIAL_DURATION = BUILDER
			.comment("Initial Duration for the player to be set on fire")
			.defineInRange("set_fire_mixin.initial_duration", 40, 20, 6000);

	public static final ModConfigSpec.ConfigValue<Integer> SET_FIRE_DURATION_INCREASE = BUILDER
			.comment("How many ticks to add to fire duration if player is already on fire and near a heat source")
			.defineInRange("set_fire_mixin.duration_increase", 20, 1, 6000);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_ZOMBIE_MIXIN = BUILDER
			.comment("Enable Zombie mixin")
			.define("mixins.zombie_mixin", true);

	public static final ModConfigSpec.ConfigValue<Double> ZOMBIE_SUMMON_CHANCE = BUILDER
			.comment("Chance for zombie to summon reinforcements when hit")
			.defineInRange("zombie_mixin.zombie_summon_chance", 0.05, 0.0, 1.0);

	public static final ModConfigSpec.ConfigValue<Integer> ZOMBIE_SUMMON_MIN = BUILDER
			.comment("Minimum number of zombies to summon")
			.define("zombie_mixin.zombie_summon_minimum", 1);

	public static final ModConfigSpec.ConfigValue<Integer> ZOMBIE_SUMMON_MAX = BUILDER
			.comment("Maximum number of zombies to summon")
			.define("zombie_mixin.zombie_summon_maximum", 3);

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_FOOD_DATA_MIXIN = BUILDER
			.comment("Enable Food Data mixin")
			.define("mixins.food_data.mixin", true);

	public static final ModConfigSpec.ConfigValue<Double> FOOD_DATA_EXHAUSTION_MULTIPLIER = BUILDER
			.comment("Multiplier for food exhaustion rate")
			.defineInRange("food_data_mixin.food_data_exhaustion_multiplier", 3.0, 1.0, 10.0);

	public static final ModConfigSpec.ConfigValue<Double> FOOD_DEBUFF_CHANCE = BUILDER
			.comment("Chance for a food item to give you a harmful effect when consumed")
			.defineInRange("living_entity_mixin.food_debuff_chance", 0.15, 0.0, 1.0);

	public static final ModConfigSpec SPEC = BUILDER.build();
}
