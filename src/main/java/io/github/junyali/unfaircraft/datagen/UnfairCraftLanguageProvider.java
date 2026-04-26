package io.github.junyali.unfaircraft.datagen;

import io.github.junyali.unfaircraft.UnfairCraft;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class UnfairCraftLanguageProvider extends LanguageProvider {
	private String currentSection;

	public UnfairCraftLanguageProvider(PackOutput output, String locale) {
		super(output, UnfairCraft.MODID, locale);
	}

	private void section(String section, String translation) {
		this.currentSection = section;
		add(UnfairCraft.MODID + ".configuration." + currentSection, translation);
	}

	private void addConfig(String key, String translation) {
		add(UnfairCraft.MODID + ".config." + currentSection + "." + key, translation);
	}

	@Override
	protected void addTranslations() {
		add("pack.unfaircraft.description", "Unfaircraft");

		section("general", "General");
		addConfig("enable_unfair_mode", "Enable Unfair Mode");
		addConfig("enable_nightmare_mode", "Enable Nightmare Mode");

		section("bed", "Bed");
		addConfig("enabled", "Enable Bed Mixin");
		addConfig("explosion_chance", "Bed Explosion Chance");
		addConfig("explosion_radius", "Bed Explosion Radius");
		addConfig("fire_chance", "Bed Fire Chance");
		addConfig("fire_duration", "Bed Fire Duration");

		section("shield", "Shield");
		addConfig("enabled", "Enable Shield Mixin");
		addConfig("fail_chance", "Shield Fail Chance");

		section("player", "Player");
		addConfig("enabled", "Enable Player Mixin");
		addConfig("crit_fail_chance", "Crit Fail Chance");
		addConfig("self_attack_chance", "Self Attack Chance");
		addConfig("attack_exhaustion_chance", "Attack Exhaustion Chance");
		addConfig("random_drop_chance", "Random Item Drop Chance");
		addConfig("fall_damage_distance", "Fall Damage Distance");
		addConfig("fall_damage_multiplier", "Fall Damage Multiplier");
		addConfig("pickup_fail_chance", "Item Pickup Fail Chance");
		addConfig("enable_biome_hazards", "Enable Biome Hazards");
		addConfig("ground_break_chance", "Ground Break Chance");
		addConfig("low_mining_morale_chance", "Low Mining Morale Chance");

		section("item_durability", "Item Durability");
		addConfig("enabled", "Enable Item Durability Mixin");
		addConfig("loss_chance", "Durability Loss Chance");
		addConfig("damage_min", "Min Extra Durability Damage");
		addConfig("damage_max", "Max Extra Durability Damage");

		section("bow", "Bow");
		addConfig("enabled", "Enable Bow Mixin");
		addConfig("wonky_chance", "Wonky Arrow Chance");
		addConfig("projectile_deviation", "Arrow Deviation Amount");
		addConfig("misfire_chance", "Bow Misfire Chance");
		addConfig("backfire_chance", "Bow Backfire Chance");
		addConfig("backfire_damage_min", "Min Backfire Damage");
		addConfig("backfire_damage_max", "Max Backfire Damage");

		section("chest", "Chest");
		addConfig("enabled", "Enable Chest Mixin");
		addConfig("eat_chance", "Chest Eat Chance");
		addConfig("eat_item_min", "Min Items Eaten");
		addConfig("eat_item_max", "Max Items Eaten");

		section("anvil", "Anvil");
		addConfig("enabled", "Enable Anvil Mixin");
		addConfig("instant_break_chance", "Instant Break Chance");
		addConfig("cost_increase_chance", "Cost Increase Chance");
		addConfig("cost_multiplier_min", "Min Cost Multiplier");
		addConfig("cost_multiplier_max", "Max Cost Multiplier");

		section("minecart", "Minecart");
		addConfig("enabled", "Enable Minecart Mixin");
		addConfig("slowdown_factor", "Slowdown Factor");
		addConfig("stop_chance", "Random Stop Chance");
		addConfig("reverse_chance", "Random Reverse Chance");

		section("food", "Food");
		addConfig("enabled", "Enable Food Mixin");
		addConfig("fail_chance", "Food Fail Chance");
		addConfig("debuff_chance", "Food Debuff Chance");

		section("cave_carver", "Cave Carver");
		addConfig("enabled", "Enable Cave Carver Mixin");
		addConfig("extra_lava_pocket_chance", "Extra Lava Pocket Chance");

		section("ore", "Ore");
		addConfig("enabled", "Enable Ore Mixin");
		addConfig("default_replacement_chance", "Default Ore Replacement Chance");
		addConfig("gold_replacement_chance", "Gold Ore Replacement Chance");
		addConfig("emerald_replacement_chance", "Emerald Ore Replacement Chance");
		addConfig("diamond_replacement_chance", "Diamond Ore Replacement Chance");
		addConfig("ancient_debris_replacement_chance", "Ancient Debris Replacement Chance");

		section("loot_table", "Loot Table");
		addConfig("enabled", "Enable Loot Table Mixin");
		addConfig("troll_chance", "Troll Loot Chance");

		section("sapling", "Sapling");
		addConfig("enabled", "Enable Sapling Mixin");
		addConfig("death_chance", "Sapling Death Chance");

		section("farmland", "Farmland");
		addConfig("enabled", "Enable Farmland Mixin");
		addConfig("revert_chance", "Farmland Revert Chance");

		section("mob_detection", "Mob Detection");
		addConfig("enabled", "Enable Mob Detection Mixin");
		addConfig("range_multiplier", "Detection Range Multiplier");

		section("block_interaction", "Block Interaction");
		addConfig("enabled", "Enable Block Interaction Mixin");
		addConfig("place_fail_chance", "Block Place Fail Chance");
		addConfig("break_fail_chance", "Block Break Fail Chance");

		section("bucket", "Bucket");
		addConfig("enabled", "Enable Bucket Mixin");
		addConfig("water_fail_chance", "Water Bucket Fail Chance");

		section("armour", "Armour");
		addConfig("enabled", "Enable Armour Mixin");
		addConfig("protection_fail_chance", "Protection Fail Chance");
		addConfig("durability_loss_chance", "Durability Loss Chance");
		addConfig("durability_damage_min", "Min Extra Durability Damage");
		addConfig("durability_damage_max", "Max Extra Durability Damage");

		section("totem", "Totem");
		addConfig("enabled", "Enable Totem Mixin");
		addConfig("fail_chance", "Totem Fail Chance");

		section("knockback", "Knockback");
		addConfig("enabled", "Enable Knockback Mixin");
		addConfig("chance", "Knockback Chance");
		addConfig("multiplier", "Knockback Multiplier");

		section("damage_reflection", "Damage Reflection");
		addConfig("enabled", "Enable Damage Reflection Mixin");
		addConfig("chance", "Reflection Chance");
		addConfig("percentage_min", "Min Reflection Percentage");
		addConfig("percentage_max", "Max Reflection Percentage");
		addConfig("ignore_thorns", "Ignore Thorns");

		section("creeper", "Creeper");
		addConfig("enabled", "Enable Creeper Mixin");
		addConfig("fuse_speed_multiplier", "Fuse Speed Multiplier");
		addConfig("explosion_radius_multiplier", "Explosion Radius Multiplier");

		section("skeleton", "Skeleton");
		addConfig("enabled", "Enable Skeleton Mixin");
		addConfig("accuracy_multiplier", "Accuracy Multiplier");
		addConfig("attack_speed_increase", "Attack Speed Increase");

		section("enderman", "Enderman");
		addConfig("enabled", "Enable Enderman Mixin");
		addConfig("proximity_aggro_range", "Proximity Aggro Range");
		addConfig("proximity_aggro_chance", "Proximity Aggro Chance");

		section("zombie", "Zombie");
		addConfig("enabled", "Enable Zombie Mixin");
		addConfig("summon_chance", "Summon Chance");
		addConfig("summon_min", "Min Zombies Summoned");
		addConfig("summon_max", "Max Zombies Summoned");

		section("potion", "Potion");
		addConfig("enabled", "Enable Potion Mixin");
		addConfig("backfire_chance", "Potion Backfire Chance");

		section("mob_regen", "Mob Regen");
		addConfig("enabled", "Enable Mob Regen Mixin");
		addConfig("delay", "Regen Delay (ticks)");
		addConfig("rate", "Regen Rate (ticks)");
		addConfig("amount", "Regen Amount");

		section("set_fire", "Set Fire");
		addConfig("enabled", "Enable Set Fire Mixin");
		addConfig("radius", "Fire Check Radius");
		addConfig("chance", "Fire Ignite Chance");
		addConfig("initial_duration", "Initial Fire Duration");
		addConfig("duration_increase", "Fire Duration Increase");

		section("food_data", "Food Data");
		addConfig("enabled", "Enable Food Exhaustion Mixin");
		addConfig("exhaustion_multiplier", "Exhaustion Multiplier");

		section("drunk_jumping", "Drunk Jumping");
		addConfig("enabled", "Enable Drunk Jumping Mixin");

		section("nightmare_event", "Nightmare Event");
		addConfig("chance", "Nightmare Event Chance");

		section("merchant_offer", "Merchant Offer");
		addConfig("enabled", "Enable Merchant Offer Mixin");
		addConfig("multiplier", "Multiplier for merchant prices");
		addConfig("wandering_trader_scam_enabled", "Enable Wandering Trader Scam Offers");
		addConfig("wandering_trader_scam_chance", "Wandering Trader Scam Chance");
		addConfig("villager_retaliation", "Enable Villager Retaliation");

		section("iron_golem", "Iron Golem");
		addConfig("enabled", "Enable Iron Golem Mixin");

		section("mob", "Mob");
		addConfig("hostile_regardless_enabled", "Make all passive mobs hostile");
		addConfig("allow_hostile_daylight_spawn", "Allow Hostile Daylight Spawn");
		addConfig("hostile_daylight_spawn_chance", "Hostile Daylight Spawn Chance");

		section("weather", "Weather");
		addConfig("enabled", "Enable Weather Mixin");
		addConfig("escalate_thunder_chance", "Escalate Thunder Chance");
		addConfig("remain_thunder_chance", "Remain Thunder Chance");

		section("phantom", "Phantom");
		addConfig("enabled", "Enable Phantom Mixin");
		addConfig("summon_chance", "Additional swarm summon chance");
		addConfig("summon_min", "Min phantoms summoned");
		addConfig("summon_max", "Max phantoms summoned");

		section("block", "Block");
		addConfig("enabled", "Enable Block Mixin");
		addConfig("mimic_spawn_chance", "Mimic Spawn Chance");
		addConfig("cave_in_chance", "Cave In Chance");
		addConfig("silk_touch_fail_chance", "Silk Touch Fail Chance");
		addConfig("fortune_reversal_chance", "Fortune Reversal Chance");
		addConfig("vein_collapse_chance", "Vein Collapse Chance");

		section("ghast", "Ghast");
		addConfig("enabled", "Enable Ghast Mixin");
		addConfig("summon_chance", "Additional ghast summon chance");
		addConfig("summon_min", "Min ghasts summoned");
		addConfig("summon_max", "Max ghasts summoned");
	}
}
