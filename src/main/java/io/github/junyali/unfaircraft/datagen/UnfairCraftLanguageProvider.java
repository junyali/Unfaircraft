package io.github.junyali.unfaircraft.datagen;

import io.github.junyali.unfaircraft.UnfairCraft;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class UnfairCraftLanguageProvider extends LanguageProvider {
	private String currentSection;

	public UnfairCraftLanguageProvider(PackOutput output, String locale) {
		super(output, UnfairCraft.MODID, locale);
	}

	private void section(String section) {
		this.currentSection = section;
	}

	private void addConfig(String key, String translation) {
		add(UnfairCraft.MODID + ".config." + currentSection + "." + key, translation);
	}

	@Override
	protected void addTranslations() {
		add("pack.unfaircraft.description", "Unfaircraft");

		section("general");
		addConfig("enable_unfair_mode", "Enable Unfair Mode");
		addConfig("enable_nightmare_mode", "Enable Nightmare Mode");

		section("bed");
		addConfig("enabled", "Enable Bed Mixin");
		addConfig("explosion_chance", "Bed Explosion Chance");
		addConfig("explosion_radius", "Bed Explosion Radius");
		addConfig("fire_chance", "Bed Fire Chance");
		addConfig("fire_duration", "Bed Fire Duration");

		section("shield");
		addConfig("enabled", "Enable Shield Mixin");
		addConfig("fail_chance", "Shield Fail Chance");

		section("player");
		addConfig("enabled", "Enable Player Mixin");
		addConfig("crit_fail_chance", "Crit Fail Chance");
		addConfig("self_attack_chance", "Self Attack Chance");
		addConfig("attack_exhaustion_chance", "Attack Exhaustion Chance");
		addConfig("random_drop_chance", "Random Item Drop Chance");
		addConfig("fall_damage_distance", "Fall Damage Distance");
		addConfig("fall_damage_multiplier", "Fall Damage Multiplier");
		addConfig("pickup_fail_chance", "Item Pickup Fail Chance");

		section("item_durability");
		addConfig("enabled", "Enable Item Durability Mixin");
		addConfig("loss_chance", "Durability Loss Chance");
		addConfig("damage_min", "Min Extra Durability Damage");
		addConfig("damage_max", "Max Extra Durability Damage");

		section("bow");
		addConfig("enabled", "Enable Bow Mixin");
		addConfig("wonky_chance", "Wonky Arrow Chance");
		addConfig("projectile_deviation", "Arrow Deviation Amount");
		addConfig("misfire_chance", "Bow Misfire Chance");
		addConfig("backfire_chance", "Bow Backfire Chance");
		addConfig("backfire_damage_min", "Min Backfire Damage");
		addConfig("backfire_damage_max", "Max Backfire Damage");

		section("chest");
		addConfig("enabled", "Enable Chest Mixin");
		addConfig("eat_chance", "Chest Eat Chance");
		addConfig("eat_item_min", "Min Items Eaten");
		addConfig("eat_item_max", "Max Items Eaten");

		section("anvil");
		addConfig("enabled", "Enable Anvil Mixin");
		addConfig("instant_break_chance", "Instant Break Chance");
		addConfig("cost_increase_chance", "Cost Increase Chance");
		addConfig("cost_multiplier_min", "Min Cost Multiplier");
		addConfig("cost_multiplier_max", "Max Cost Multiplier");

		section("minecart");
		addConfig("enabled", "Enable Minecart Mixin");
		addConfig("slowdown_factor", "Slowdown Factor");
		addConfig("stop_chance", "Random Stop Chance");
		addConfig("reverse_chance", "Random Reverse Chance");

		section("food");
		addConfig("enabled", "Enable Food Mixin");
		addConfig("fail_chance", "Food Fail Chance");
		addConfig("debuff_chance", "Food Debuff Chance");

		section("cave_carver");
		addConfig("enabled", "Enable Cave Carver Mixin");
		addConfig("extra_lava_pocket_chance", "Extra Lava Pocket Chance");

		section("ore");
		addConfig("enabled", "Enable Ore Mixin");
		addConfig("default_replacement_chance", "Default Ore Replacement Chance");
		addConfig("gold_replacement_chance", "Gold Ore Replacement Chance");
		addConfig("emerald_replacement_chance", "Emerald Ore Replacement Chance");
		addConfig("diamond_replacement_chance", "Diamond Ore Replacement Chance");
		addConfig("ancient_debris_replacement_chance", "Ancient Debris Replacement Chance");

		section("loot_table");
		addConfig("enabled", "Enable Loot Table Mixin");
		addConfig("troll_chance", "Troll Loot Chance");

		section("sapling");
		addConfig("enabled", "Enable Sapling Mixin");
		addConfig("death_chance", "Sapling Death Chance");

		section("farmland");
		addConfig("enabled", "Enable Farmland Mixin");
		addConfig("revert_chance", "Farmland Revert Chance");

		section("mob_detection");
		addConfig("enabled", "Enable Mob Detection Mixin");
		addConfig("range_multiplier", "Detection Range Multiplier");

		section("block_interaction");
		addConfig("enabled", "Enable Block Interaction Mixin");
		addConfig("place_fail_chance", "Block Place Fail Chance");
		addConfig("break_fail_chance", "Block Break Fail Chance");

		section("bucket");
		addConfig("enabled", "Enable Bucket Mixin");
		addConfig("water_fail_chance", "Water Bucket Fail Chance");

		section("armour");
		addConfig("enabled", "Enable Armour Mixin");
		addConfig("protection_fail_chance", "Protection Fail Chance");
		addConfig("durability_loss_chance", "Durability Loss Chance");
		addConfig("durability_damage_min", "Min Extra Durability Damage");
		addConfig("durability_damage_max", "Max Extra Durability Damage");

		section("totem");
		addConfig("enabled", "Enable Totem Mixin");
		addConfig("fail_chance", "Totem Fail Chance");

		section("knockback");
		addConfig("enabled", "Enable Knockback Mixin");
		addConfig("chance", "Knockback Chance");
		addConfig("multiplier", "Knockback Multiplier");

		section("damage_reflection");
		addConfig("enabled", "Enable Damage Reflection");
		addConfig("chance", "Reflection Chance");
		addConfig("percentage_min", "Min Reflection Percentage");
		addConfig("percentage_max", "Max Reflection Percentage");
		addConfig("ignore_thorns", "Ignore Thorns");

		section("creeper");
		addConfig("enabled", "Enable Creeper Mixin");
		addConfig("fuse_speed_multiplier", "Fuse Speed Multiplier");
		addConfig("explosion_radius_multiplier", "Explosion Radius Multiplier");

		section("skeleton");
		addConfig("enabled", "Enable Skeleton Mixin");
		addConfig("accuracy_multiplier", "Accuracy Multiplier");
		addConfig("attack_speed_increase", "Attack Speed Increase");

		section("enderman");
		addConfig("enabled", "Enable Enderman Mixin");
		addConfig("proximity_aggro_range", "Proximity Aggro Range");
		addConfig("proximity_aggro_chance", "Proximity Aggro Chance");

		section("zombie");
		addConfig("enabled", "Enable Zombie Mixin");
		addConfig("summon_chance", "Summon Chance");
		addConfig("summon_min", "Min Zombies Summoned");
		addConfig("summon_max", "Max Zombies Summoned");

		section("potion");
		addConfig("enabled", "Enable Potion Mixin");
		addConfig("backfire_chance", "Potion Backfire Chance");

		section("mob_regen");
		addConfig("enabled", "Enable Mob Regen");
		addConfig("delay", "Regen Delay (ticks)");
		addConfig("rate", "Regen Rate (ticks)");
		addConfig("amount", "Regen Amount");

		section("set_fire");
		addConfig("enabled", "Enable Set Fire Mixin");
		addConfig("radius", "Fire Check Radius");
		addConfig("chance", "Fire Ignite Chance");
		addConfig("initial_duration", "Initial Fire Duration");
		addConfig("duration_increase", "Fire Duration Increase");

		section("food_data");
		addConfig("enabled", "Enable Food Exhaustion Mixin");
		addConfig("exhaustion_multiplier", "Exhaustion Multiplier");

		section("drunk_jumping");
		addConfig("enabled", "Enable Drunk Jumping");

		section("nightmare_event");
		addConfig("chance", "Nightmare Event Chance");
	}
}
