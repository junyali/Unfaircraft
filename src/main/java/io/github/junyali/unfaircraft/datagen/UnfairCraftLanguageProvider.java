package io.github.junyali.unfaircraft.datagen;

import io.github.junyali.unfaircraft.UnfairCraft;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class UnfairCraftLanguageProvider extends FabricLanguageProvider {
	private String currentSection;

	protected UnfairCraftLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(dataOutput, "en_us", registryLookup);
	}

	private void translateSection(TranslationBuilder translationBuilder, String section, String translation) {
		this.currentSection = section;
		translationBuilder.add("text.config." + UnfairCraft.MOD_ID + ".section." + section, translation);
		translationBuilder.add("text.config." + UnfairCraft.MOD_ID + ".category." + section, translation);
	}

	private void translateConfig(TranslationBuilder translationBuilder, String key, String translation) {
		translationBuilder.add("text.config." + UnfairCraft.MOD_ID + ".option." + currentSection + "." + key, translation);
	}

	@Override
	public void generateTranslations(HolderLookup.Provider wrapperLookup, TranslationBuilder translationBuilder) {
		translationBuilder.add("text.config." + UnfairCraft.MOD_ID + ".title", "Unfaircraft");
		
		translateSection(translationBuilder, "general", "General");
		translationBuilder.add("text.config." + UnfairCraft.MOD_ID + ".option.enableUnfairMode", "Enable Unfair Mode");
		translationBuilder.add("text.config." + UnfairCraft.MOD_ID + ".option.enableNightmareMode", "Enable Nightmare Mode");

		translateSection(translationBuilder, "nightmareEvent", "Nightmare Event");
		translateConfig(translationBuilder, "chance", "Nightmare Event Chance");

		translateSection(translationBuilder, "bed", "Bed");
		translateConfig(translationBuilder, "enabled", "Enable Bed Mixin");
		translateConfig(translationBuilder, "explosionChance", "Bed Explosion Chance");
		translateConfig(translationBuilder, "explosionRadius", "Bed Explosion Radius");
		translateConfig(translationBuilder, "fireChance", "Bed Fire Chance");
		translateConfig(translationBuilder, "fireDuration", "Bed Fire Duration");

		translateSection(translationBuilder, "shield", "Shield");
		translateConfig(translationBuilder, "enabled", "Enable Shield Mixin");
		translateConfig(translationBuilder, "failChance", "Shield Fail Chance");

		translateSection(translationBuilder, "player", "Player");
		translateConfig(translationBuilder, "enabled", "Enable Player Mixin");
		translateConfig(translationBuilder, "critFailChance", "Crit Fail Chance");
		translateConfig(translationBuilder, "selfAttackChance", "Self Attack Chance");
		translateConfig(translationBuilder, "attackExhaustionChance", "Attack Exhaustion Chance");
		translateConfig(translationBuilder, "randomDropChance", "Random Item Drop Chance");
		translateConfig(translationBuilder, "fallDamageDistance", "Fall Damage Distance");
		translateConfig(translationBuilder, "fallDamageMultiplier", "Fall Damage Multiplier");
		translateConfig(translationBuilder, "pickupFailChance", "Item Pickup Fail Chance");
		translateConfig(translationBuilder, "enableBiomeHazards", "Enable Biome Hazards");
		translateConfig(translationBuilder, "groundBreakChance", "Ground Break Chance");
		translateConfig(translationBuilder, "lowMiningMoraleChance", "Low Mining Morale Chance");
		translateConfig(translationBuilder, "ladderSlipChance", "Ladder Slip Chance");

		translateSection(translationBuilder, "itemDurability", "Item Durability");
		translateConfig(translationBuilder, "enabled", "Enable Item Durability Mixin");
		translateConfig(translationBuilder, "lossChance", "Durability Loss Chance");
		translateConfig(translationBuilder, "damageMin", "Min Extra Durability Damage");
		translateConfig(translationBuilder, "damageMax", "Max Extra Durability Damage");

		translateSection(translationBuilder, "bow", "Bow");
		translateConfig(translationBuilder, "enabled", "Enable Bow Mixin");
		translateConfig(translationBuilder, "wonkyChance", "Wonky Arrow Chance");
		translateConfig(translationBuilder, "projectileDeviation", "Arrow Deviation Amount");
		translateConfig(translationBuilder, "misfireChance", "Bow Misfire Chance");
		translateConfig(translationBuilder, "backfireChance", "Bow Backfire Chance");
		translateConfig(translationBuilder, "backfireDamageMin", "Min Backfire Damage");
		translateConfig(translationBuilder, "backfireDamageMax", "Max Backfire Damage");

		translateSection(translationBuilder, "chest", "Chest");
		translateConfig(translationBuilder, "enabled", "Enable Chest Mixin");
		translateConfig(translationBuilder, "eatChance", "Chest Eat Chance");
		translateConfig(translationBuilder, "eatItemMin", "Min Items Eaten");
		translateConfig(translationBuilder, "eatItemMax", "Max Items Eaten");

		translateSection(translationBuilder, "anvil", "Anvil");
		translateConfig(translationBuilder, "enabled", "Enable Anvil Mixin");
		translateConfig(translationBuilder, "instantBreakChance", "Instant Break Chance");
		translateConfig(translationBuilder, "costIncreaseChance", "Cost Increase Chance");
		translateConfig(translationBuilder, "costMultiplierMin", "Min Cost Multiplier");
		translateConfig(translationBuilder, "costMultiplierMax", "Max Cost Multiplier");

		translateSection(translationBuilder, "minecart", "Minecart");
		translateConfig(translationBuilder, "enabled", "Enable Minecart Mixin");
		translateConfig(translationBuilder, "slowdownFactor", "Slowdown Factor");
		translateConfig(translationBuilder, "stopChance", "Random Stop Chance");
		translateConfig(translationBuilder, "reverseChance", "Random Reverse Chance");

		translateSection(translationBuilder, "food", "Food");
		translateConfig(translationBuilder, "enabled", "Enable Food Mixin");
		translateConfig(translationBuilder, "failChance", "Food Fail Chance");
		translateConfig(translationBuilder, "debuffChance", "Food Debuff Chance");

		translateSection(translationBuilder, "caveCarver", "Cave Carver");
		translateConfig(translationBuilder, "enabled", "Enable Cave Carver Mixin");
		translateConfig(translationBuilder, "extraLavaPocketChance", "Extra Lava Pocket Chance");

		translateSection(translationBuilder, "ore", "Ore");
		translateConfig(translationBuilder, "enabled", "Enable Ore Mixin");
		translateConfig(translationBuilder, "defaultReplacementChance", "Default Ore Replacement Chance");
		translateConfig(translationBuilder, "goldReplacementChance", "Gold Ore Replacement Chance");
		translateConfig(translationBuilder, "emeraldReplacementChance", "Emerald Ore Replacement Chance");
		translateConfig(translationBuilder, "diamondReplacementChance", "Diamond Ore Replacement Chance");
		translateConfig(translationBuilder, "ancientDebrisReplacementChance", "Ancient Debris Replacement Chance");

		translateSection(translationBuilder, "lootTable", "Loot Table");
		translateConfig(translationBuilder, "enabled", "Enable Loot Table Mixin");
		translateConfig(translationBuilder, "trollChance", "Troll Loot Chance");

		translateSection(translationBuilder, "sapling", "Sapling");
		translateConfig(translationBuilder, "enabled", "Enable Sapling Mixin");
		translateConfig(translationBuilder, "deathChance", "Sapling Death Chance");

		translateSection(translationBuilder, "farmland", "Farmland");
		translateConfig(translationBuilder, "enabled", "Enable Farmland Mixin");
		translateConfig(translationBuilder, "revertChance", "Farmland Revert Chance");

		translateSection(translationBuilder, "mobDetection", "Mob Detection");
		translateConfig(translationBuilder, "enabled", "Enable Mob Detection Mixin");
		translateConfig(translationBuilder, "rangeMultiplier", "Detection Range Multiplier");

		translateSection(translationBuilder, "blockInteraction", "Block Interaction");
		translateConfig(translationBuilder, "enabled", "Enable Block Interaction Mixin");
		translateConfig(translationBuilder, "placeFailChance", "Block Place Fail Chance");
		translateConfig(translationBuilder, "breakFailChance", "Block Break Fail Chance");

		translateSection(translationBuilder, "bucket", "Bucket");
		translateConfig(translationBuilder, "enabled", "Enable Bucket Mixin");
		translateConfig(translationBuilder, "waterFailChance", "Water Bucket Fail Chance");

		translateSection(translationBuilder, "armour", "Armour");
		translateConfig(translationBuilder, "enabled", "Enable Armour Mixin");
		translateConfig(translationBuilder, "protectionFailChance", "Protection Fail Chance");
		translateConfig(translationBuilder, "durabilityLossChance", "Durability Loss Chance");
		translateConfig(translationBuilder, "durabilityDamageMin", "Min Extra Durability Damage");
		translateConfig(translationBuilder, "durabilityDamageMax", "Max Extra Durability Damage");

		translateSection(translationBuilder, "totem", "Totem");
		translateConfig(translationBuilder, "enabled", "Enable Totem Mixin");
		translateConfig(translationBuilder, "failChance", "Totem Fail Chance");

		translateSection(translationBuilder, "knockback", "Knockback");
		translateConfig(translationBuilder, "enabled", "Enable Knockback Mixin");
		translateConfig(translationBuilder, "chance", "Knockback Chance");
		translateConfig(translationBuilder, "multiplier", "Knockback Multiplier");

		translateSection(translationBuilder, "damageReflection", "Damage Reflection");
		translateConfig(translationBuilder, "enabled", "Enable Damage Reflection Mixin");
		translateConfig(translationBuilder, "chance", "Reflection Chance");
		translateConfig(translationBuilder, "percentageMin", "Min Reflection Percentage");
		translateConfig(translationBuilder, "percentageMax", "Max Reflection Percentage");
		translateConfig(translationBuilder, "ignoreThorns", "Ignore Thorns");

		translateSection(translationBuilder, "creeper", "Creeper");
		translateConfig(translationBuilder, "enabled", "Enable Creeper Mixin");
		translateConfig(translationBuilder, "fuseSpeedMultiplier", "Fuse Speed Multiplier");
		translateConfig(translationBuilder, "explosionRadiusMultiplier", "Explosion Radius Multiplier");

		translateSection(translationBuilder, "skeleton", "Skeleton");
		translateConfig(translationBuilder, "enabled", "Enable Skeleton Mixin");
		translateConfig(translationBuilder, "accuracyMultiplier", "Accuracy Multiplier");
		translateConfig(translationBuilder, "attackSpeedIncrease", "Attack Speed Increase");

		translateSection(translationBuilder, "enderman", "Enderman");
		translateConfig(translationBuilder, "enabled", "Enable Enderman Mixin");
		translateConfig(translationBuilder, "proximityAggroRange", "Proximity Aggro Range");
		translateConfig(translationBuilder, "proximityAggroChance", "Proximity Aggro Chance");

		translateSection(translationBuilder, "zombie", "Zombie");
		translateConfig(translationBuilder, "enabled", "Enable Zombie Mixin");
		translateConfig(translationBuilder, "summonChance", "Summon Chance");
		translateConfig(translationBuilder, "summonMin", "Min Zombies Summoned");
		translateConfig(translationBuilder, "summonMax", "Max Zombies Summoned");

		translateSection(translationBuilder, "potion", "Potion");
		translateConfig(translationBuilder, "enabled", "Enable Potion Mixin");
		translateConfig(translationBuilder, "backfireChance", "Potion Backfire Chance");

		translateSection(translationBuilder, "mobRegen", "Mob Regen");
		translateConfig(translationBuilder, "enabled", "Enable Mob Regen Mixin");
		translateConfig(translationBuilder, "delay", "Regen Delay (ticks)");
		translateConfig(translationBuilder, "rate", "Regen Rate (ticks)");
		translateConfig(translationBuilder, "amount", "Regen Amount");

		translateSection(translationBuilder, "setFire", "Set Fire");
		translateConfig(translationBuilder, "enabled", "Enable Set Fire Mixin");
		translateConfig(translationBuilder, "radius", "Fire Check Radius");
		translateConfig(translationBuilder, "chance", "Fire Ignite Chance");
		translateConfig(translationBuilder, "initialDuration", "Initial Fire Duration");
		translateConfig(translationBuilder, "durationIncrease", "Fire Duration Increase");

		translateSection(translationBuilder, "foodData", "Food Data");
		translateConfig(translationBuilder, "enabled", "Enable Food Exhaustion Mixin");
		translateConfig(translationBuilder, "exhaustionMultiplier", "Exhaustion Multiplier");

		translateSection(translationBuilder, "drunkJumping", "Drunk Jumping");
		translateConfig(translationBuilder, "enabled", "Enable Drunk Jumping Mixin");

		translateSection(translationBuilder, "merchantOffer", "Merchant Offer");
		translateConfig(translationBuilder, "enabled", "Enable Merchant Offer Mixin");
		translateConfig(translationBuilder, "multiplier", "Multiplier for merchant prices");
		translateConfig(translationBuilder, "wanderingTraderScamEnabled", "Enable Wandering Trader Scam Offers");
		translateConfig(translationBuilder, "wanderingTraderScamChance", "Wandering Trader Scam Chance");
		translateConfig(translationBuilder, "villagerRetaliation", "Enable Villager Retaliation");

		translateSection(translationBuilder, "ironGolem", "Iron Golem");
		translateConfig(translationBuilder, "enabled", "Enable Iron Golem Mixin");

		translateSection(translationBuilder, "mob", "Mob");
		translateConfig(translationBuilder, "hostileRegardlessEnabled", "Make all passive mobs hostile");
		translateConfig(translationBuilder, "passiveRetaliationEnabled", "Passive Retaliation Enabled");
		translateConfig(translationBuilder, "allowHostileDaylightSpawn", "Allow Hostile Daylight Spawn");
		translateConfig(translationBuilder, "hostileDaylightSpawnChance", "Hostile Daylight Spawn Chance");

		translateSection(translationBuilder, "weather", "Weather");
		translateConfig(translationBuilder, "enabled", "Enable Weather Mixin");
		translateConfig(translationBuilder, "escalateThunderChance", "Escalate Thunder Chance");
		translateConfig(translationBuilder, "remainThunderChance", "Remain Thunder Chance");

		translateSection(translationBuilder, "phantom", "Phantom");
		translateConfig(translationBuilder, "enabled", "Enable Phantom Mixin");
		translateConfig(translationBuilder, "summonChance", "Additional swarm summon chance");
		translateConfig(translationBuilder, "summonMin", "Min phantoms summoned");
		translateConfig(translationBuilder, "summonMax", "Max phantoms summoned");

		translateSection(translationBuilder, "block", "Block");
		translateConfig(translationBuilder, "enabled", "Enable Block Mixin");
		translateConfig(translationBuilder, "mimicSpawnChance", "Mimic Spawn Chance");
		translateConfig(translationBuilder, "caveInChance", "Cave In Chance");
		translateConfig(translationBuilder, "silkTouchfailChance", "Silk Touch Fail Chance");
		translateConfig(translationBuilder, "fortuneReversalChance", "Fortune Reversal Chance");
		translateConfig(translationBuilder, "veinCollapseChance", "Vein Collapse Chance");

		translateSection(translationBuilder, "ghast", "Ghast");
		translateConfig(translationBuilder, "enabled", "Enable Ghast Mixin");
		translateConfig(translationBuilder, "summonChance", "Additional ghast summon chance");
		translateConfig(translationBuilder, "summonMin", "Min ghasts summoned");
		translateConfig(translationBuilder, "summonMax", "Max ghasts summoned");

		translateSection(translationBuilder, "blaze", "Blaze");
		translateConfig(translationBuilder, "enabled", "Enable Blaze Mixin");
		translateConfig(translationBuilder, "summonChance", "Additional blaze summon chance");
		translateConfig(translationBuilder, "summonMin", "Min blazes summoned");
		translateConfig(translationBuilder, "summonMax", "Max blazes summoned");

		translateSection(translationBuilder, "enderDragon", "Ender Dragon");
		translateConfig(translationBuilder, "enabled", "Enable Ender Dragon Mixin");
		translateConfig(translationBuilder, "healthMultiplier", "Health Multiplier");
		translateConfig(translationBuilder, "speedMultiplier", "Speed Multiplier");
		translateConfig(translationBuilder, "aggressionChance", "Aggression Chance");

		translateSection(translationBuilder, "endCrystal", "End Crystal");
		translateConfig(translationBuilder, "enabled", "Enable End Crystal Mixin");
		translateConfig(translationBuilder, "healingMultiplier", "Healing Multiplier");
		translateConfig(translationBuilder, "explosionRadius", "Explosion Radius");

		translateSection(translationBuilder, "shulker", "Shulker");
		translateConfig(translationBuilder, "enabled", "Enable Shulker Mixin");
		translateConfig(translationBuilder, "bulletMovementVector", "Bullet Movement Vector");
		translateConfig(translationBuilder, "bulletDistanceTarget", "Bullet Distance Target");

		translateSection(translationBuilder, "door", "Door");
		translateConfig(translationBuilder, "enabled", "Enable Door Mixin");
		translateConfig(translationBuilder, "jamChance", "Door Jam Chance");

		translateSection(translationBuilder, "furnace", "Furnace");
		translateConfig(translationBuilder, "enabled", "Enable Furnace Mixin");
		translateConfig(translationBuilder, "fuelTheftChance", "Fuel Theft Chance");
		translateConfig(translationBuilder, "smeltTheftChance", "Smelt Theft Chance");
		translateConfig(translationBuilder, "explosionThreshold", "Explosion Threshold");
		translateConfig(translationBuilder, "explosionChance", "Explosion Chance");

		translateSection(translationBuilder, "glass", "Glass");
		translateConfig(translationBuilder, "enabled", "Enable Glass Mixin");
		translateConfig(translationBuilder, "breakChance", "Break Chance");
	}
}
