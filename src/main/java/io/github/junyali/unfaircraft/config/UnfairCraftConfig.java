package io.github.junyali.unfaircraft.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Map;

public class UnfairCraftConfig {
	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_UNFAIR_MODE;
	public static final ModConfigSpec.ConfigValue<Boolean> ENABLE_NIGHTMARE_MODE;

	public static final Bed BED;
	public static final Shield SHIELD;
	public static final Player PLAYER;
	public static final ItemDurability ITEM_DURABILITY;
	public static final Bow BOW;
	public static final Chest CHEST;
	public static final Anvil ANVIL;
	public static final Minecart MINECART;
	public static final Food FOOD;
	public static final CaveCarver CAVE_CARVER;
	public static final Ore ORE;
	public static final LootTable LOOT_TABLE;
	public static final Sapling SAPLING;
	public static final Farmland FARMLAND;
	public static final MobDetection MOB_DETECTION;
	public static final BlockInteraction BLOCK_INTERACTION;
	public static final Bucket BUCKET;
	public static final Armour ARMOUR;
	public static final Totem TOTEM;
	public static final Knockback KNOCKBACK;
	public static final DamageReflection DAMAGE_REFLECTION;
	public static final Creeper CREEPER;
	public static final Skeleton SKELETON;
	public static final Enderman ENDERMAN;
	public static final Zombie ZOMBIE;
	public static final Potion POTION;
	public static final MobRegen MOB_REGEN;
	public static final SetFire SET_FIRE;
	public static final FoodData FOOD_DATA;
	public static final DrunkJumping DRUNK_JUMPING;
	public static final NightmareEvent NIGHTMARE_EVENT;
	public static final MerchantOffer MERCHANT_OFFER;

	public static boolean isEnabled(ModConfigSpec.ConfigValue<Boolean> featureToggle) {
		return ENABLE_UNFAIR_MODE.get() && featureToggle.get();
	}

	public static class Bed {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> explosionChance;
		public final ModConfigSpec.ConfigValue<Double> explosionRadius;
		public final ModConfigSpec.ConfigValue<Double> fireChance;
		public final ModConfigSpec.ConfigValue<Integer> fireDuration;

		private Bed(ModConfigSpec.Builder builder) {
			builder.push("bed");
			enabled = builder.comment("Enable Bed mixin")
					.translation("unfaircraft.config.bed.enabled")
					.define("enabled", true);
			explosionChance = builder.comment("Chance for beds to explode on interaction")
					.translation("unfaircraft.config.bed.explosion_chance")
					.defineInRange("explosion_chance", 0.25, 0.0, 1.0);
			explosionRadius = builder.comment("Radius for bed explosion")
					.translation("unfaircraft.config.bed.explosion_radius")
					.define("explosion_radius", 5.0);
			fireChance = builder.comment("Chance for beds to set player on fire after explosion")
					.translation("unfaircraft.config.bed.fire_chance")
					.defineInRange("fire_chance", 0.5, 0.0, 1.0);
			fireDuration = builder.comment("Duration of bed fire in ticks")
					.translation("unfaircraft.config.bed.fire_duration")
					.define("fire_duration", 200);
			builder.pop();
		}
	}

	public static class Shield {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> failChance;

		private Shield(ModConfigSpec.Builder builder) {
			builder.push("shield");
			enabled = builder.comment("Enable Shield mixin")
					.translation("unfaircraft.config.shield.enabled")
					.define("enabled", true);
			failChance = builder.comment("Chance for shields to fail to block attacks")
					.translation("unfaircraft.config.shield.fail_chance")
					.defineInRange("fail_chance", 0.10, 0.0, 1.0);
			builder.pop();
		}
	}

	public static class Player {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> critFailChance;
		public final ModConfigSpec.ConfigValue<Double> selfAttackChance;
		public final ModConfigSpec.ConfigValue<Double> attackExhaustionChance;
		public final ModConfigSpec.ConfigValue<Double> randomDropChance;
		public final ModConfigSpec.ConfigValue<Double> fallDamageDistance;
		public final ModConfigSpec.ConfigValue<Double> fallDamageMultiplier;
		public final ModConfigSpec.ConfigValue<Double> pickupFailChance;

		private Player(ModConfigSpec.Builder builder) {
			builder.push("player");
			enabled = builder.comment("Enable Player mixin")
					.translation("unfaircraft.config.player.enabled")
					.define("enabled", true);
			critFailChance = builder.comment("Chance for player crit attacks to fail")
					.translation("unfaircraft.config.player.crit_fail_chance")
					.defineInRange("crit_fail_chance", 0.25, 0.0, 1.0);
			selfAttackChance = builder.comment("Chance for player attacks to inflict upon themself")
					.translation("unfaircraft.config.player.self_attack_chance")
					.defineInRange("self_attack_chance", 0.05, 0.0, 1.0);
			attackExhaustionChance = builder.comment("Chance for player attacks to cause exhaustion upon themself")
					.translation("unfaircraft.config.player.attack_exhaustion_chance")
					.defineInRange("attack_exhaustion_chance", 0.2, 0.0, 1.0);
			randomDropChance = builder.comment("Chance per tick for a player to randomly drop their held item")
					.translation("unfaircraft.config.player.random_drop_chance")
					.defineInRange("random_drop_chance", 0.0000005, 0.0, 1.0);
			fallDamageDistance = builder.comment("Minimum distance for player to fall to take fall damage")
					.translation("unfaircraft.config.player.fall_damage_distance")
					.define("fall_damage_distance", 1.5);
			fallDamageMultiplier = builder.comment("Multiplier for increased fall damage")
					.translation("unfaircraft.config.player.fall_damage_multiplier")
					.define("fall_damage_multiplier", 3.0);
			pickupFailChance = builder.comment("Chance for items picked up by the player to vanish")
					.translation("unfaircraft.config.player.pickup_fail_chance")
					.defineInRange("pickup_fail_chance", 0.01, 0.0, 1.0);
			builder.pop();
		}
	}

	public static class ItemDurability {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> lossChance;
		public final ModConfigSpec.ConfigValue<Integer> damageMin;
		public final ModConfigSpec.ConfigValue<Integer> damageMax;

		private ItemDurability(ModConfigSpec.Builder builder) {
			builder.push("item_durability");
			enabled = builder.comment("Enable Item Durability mixin")
					.translation("unfaircraft.config.item_durability.enabled")
					.define("enabled", true);
			lossChance = builder.comment("Chance for tiered items to lose extra durability")
					.translation("unfaircraft.config.item_durability.loss_chance")
					.defineInRange("loss_chance", 0.05, 0.0, 1.0);
			damageMin = builder.comment("Minimum additional durability damage on items")
					.translation("unfaircraft.config.item_durability.damage_min")
					.define("damage_min", 2);
			damageMax = builder.comment("Maximum additional durability damage on items")
					.translation("unfaircraft.config.item_durability.damage_max")
					.define("damage_max", 20);
			builder.pop();
		}
	}

	public static class Bow {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> wonkyChance;
		public final ModConfigSpec.ConfigValue<Double> projectileDeviation;
		public final ModConfigSpec.ConfigValue<Double> misfireChance;
		public final ModConfigSpec.ConfigValue<Double> backfireChance;
		public final ModConfigSpec.ConfigValue<Double> backfireDamageMin;
		public final ModConfigSpec.ConfigValue<Double> backfireDamageMax;

		private Bow(ModConfigSpec.Builder builder) {
			builder.push("bow");
			enabled = builder.comment("Enable Bow mixin")
					.translation("unfaircraft.config.bow.enabled")
					.define("enabled", true);
			wonkyChance = builder.comment("Chance for player arrows to deviate in direction")
					.translation("unfaircraft.config.bow.wonky_chance")
					.defineInRange("wonky_chance", 0.25, 0.0, 1.0);
			projectileDeviation = builder.comment("How much player-fired arrows can deviate from their intended path")
					.translation("unfaircraft.config.bow.projectile_deviation")
					.define("projectile_deviation", 0.8);
			misfireChance = builder.comment("Chance for player-fired bows to not shoot")
					.translation("unfaircraft.config.bow.misfire_chance")
					.defineInRange("misfire_chance", 0.5, 0.0, 1.0);
			backfireChance = builder.comment("Chance for player-fired bows to damage themself when misfiring")
					.translation("unfaircraft.config.bow.backfire_chance")
					.defineInRange("backfire_chance", 0.4, 0.0, 1.0);
			backfireDamageMin = builder.comment("Minimum damage from bow backfire")
					.translation("unfaircraft.config.bow.backfire_damage_min")
					.define("backfire_damage_min", 2.0);
			backfireDamageMax = builder.comment("Maximum damage from bow backfire")
					.translation("unfaircraft.config.bow.backfire_damage_max")
					.define("backfire_damage_max", 20.0);
			builder.pop();
		}
	}

	public static class Chest {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> eatChance;
		public final ModConfigSpec.ConfigValue<Integer> eatItemMin;
		public final ModConfigSpec.ConfigValue<Integer> eatItemMax;

		private Chest(ModConfigSpec.Builder builder) {
			builder.push("chest");
			enabled = builder.comment("Enable Chest mixin")
					.translation("unfaircraft.config.chest.enabled")
					.define("enabled", true);
			eatChance = builder.comment("Chance for a chest to eat items when opened")
					.translation("unfaircraft.config.chest.eat_chance")
					.defineInRange("eat_chance", 0.15, 0.0, 1.0);
			eatItemMin = builder.comment("Minimum number of items a chest will eat at once")
					.translation("unfaircraft.config.chest.eat_item_min")
					.define("eat_item_min", 1);
			eatItemMax = builder.comment("Maximum number of items a chest will eat at once")
					.translation("unfaircraft.config.chest.eat_item_max")
					.define("eat_item_max", 32);
			builder.pop();
		}
	}

	public static class Anvil {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> instantBreakChance;
		public final ModConfigSpec.ConfigValue<Double> costIncreaseChance;
		public final ModConfigSpec.ConfigValue<Double> costMultiplierMin;
		public final ModConfigSpec.ConfigValue<Double> costMultiplierMax;

		private Anvil(ModConfigSpec.Builder builder) {
			builder.push("anvil");
			enabled = builder.comment("Enable Anvil mixin")
					.translation("unfaircraft.config.anvil.enabled")
					.define("enabled", true);
			instantBreakChance = builder.comment("Chance for anvils to break completely in one use")
					.translation("unfaircraft.config.anvil.instant_break_chance")
					.defineInRange("instant_break_chance", 0.1, 0.0, 1.0);
			costIncreaseChance = builder.comment("Chance for anvil repair costs to be multiplied")
					.translation("unfaircraft.config.anvil.cost_increase_chance")
					.defineInRange("cost_increase_chance", 0.05, 0.0, 1.0);
			costMultiplierMin = builder.comment("Minimum multiplier for increased anvil repair costs")
					.translation("unfaircraft.config.anvil.cost_multiplier_min")
					.define("cost_multiplier_min", 2.0);
			costMultiplierMax = builder.comment("Maximum multiplier for increased anvil repair costs")
					.translation("unfaircraft.config.anvil.cost_multiplier_max")
					.define("cost_multiplier_max", 6.0);
			builder.pop();
		}
	}

	public static class Minecart {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> slowdownFactor;
		public final ModConfigSpec.ConfigValue<Double> stopChance;
		public final ModConfigSpec.ConfigValue<Double> reverseChance;

		private Minecart(ModConfigSpec.Builder builder) {
			builder.push("minecart");
			enabled = builder.comment("Enable Minecart mixin")
					.translation("unfaircraft.config.minecart.enabled")
					.define("enabled", true);
			slowdownFactor = builder.comment("Factor to reduce minecart speed, lower = slower")
					.translation("unfaircraft.config.minecart.slowdown_factor")
					.defineInRange("slowdown_factor", 0.5, 0.0, 1.0);
			stopChance = builder.comment("Chance for minecarts to randomly stop each tick")
					.translation("unfaircraft.config.minecart.stop_chance")
					.defineInRange("stop_chance", 0.05, 0.0, 1.0);
			reverseChance = builder.comment("Chance for minecarts to randomly reverse direction each tick")
					.translation("unfaircraft.config.minecart.reverse_chance")
					.defineInRange("reverse_chance", 0.01, 0.0, 1.0);
			builder.pop();
		}
	}

	public static class Food {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> failChance;
		public final ModConfigSpec.ConfigValue<Double> debuffChance;

		private Food(ModConfigSpec.Builder builder) {
			builder.push("food");
			enabled = builder.comment("Enable Food mixin")
					.translation("unfaircraft.config.food.enabled")
					.define("enabled", true);
			failChance = builder.comment("Chance for food to completely fail to restore hunger")
					.translation("unfaircraft.config.food.fail_chance")
					.defineInRange("fail_chance", 0.05, 0.0, 1.0);
			debuffChance = builder.comment("Chance for cooked food to give you a harmful effect when consumed")
					.translation("unfaircraft.config.food.debuff_chance")
					.defineInRange("debuff_chance", 0.15, 0.0, 1.0);
			builder.pop();
		}
	}

	public static class CaveCarver {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> extraLavaPocketChance;

		private CaveCarver(ModConfigSpec.Builder builder) {
			builder.push("cave_carver");
			enabled = builder.comment("Enable Cave Carver mixin")
					.translation("unfaircraft.config.cave_carver.enabled")
					.define("enabled", true);
			extraLavaPocketChance = builder.comment("Chance for extra lava pockets to generate in caves")
					.translation("unfaircraft.config.cave_carver.extra_lava_pocket_chance")
					.defineInRange("extra_lava_pocket_chance", 0.8, 0.0, 1.0);
			builder.pop();
		}
	}

	public static class Ore {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> defaultReplacementChance;
		public final ModConfigSpec.ConfigValue<Double> goldReplacementChance;
		public final ModConfigSpec.ConfigValue<Double> emeraldReplacementChance;
		public final ModConfigSpec.ConfigValue<Double> diamondReplacementChance;
		public final ModConfigSpec.ConfigValue<Double> ancientDebrisReplacementChance;

		private Ore(ModConfigSpec.Builder builder) {
			builder.push("ore");
			enabled = builder.comment("Enable Ore mixin")
					.translation("unfaircraft.config.ore.enabled")
					.define("enabled", true);
			defaultReplacementChance = builder.comment("Chance to replace ores in ore veins by default")
					.translation("unfaircraft.config.ore.default_replacement_chance")
					.defineInRange("default_replacement_chance", 0.3, 0.0, 1.0);
			goldReplacementChance = builder.comment("Chance to replace ores in gold ore veins")
					.translation("unfaircraft.config.ore.gold_replacement_chance")
					.defineInRange("gold_replacement_chance", 0.4, 0.0, 1.0);
			emeraldReplacementChance = builder.comment("Chance to replace ores in emerald ore veins")
					.translation("unfaircraft.config.ore.emerald_replacement_chance")
					.defineInRange("emerald_replacement_chance", 0.5, 0.0, 1.0);
			diamondReplacementChance = builder.comment("Chance to replace ores in diamond ore veins")
					.translation("unfaircraft.config.ore.diamond_replacement_chance")
					.defineInRange("diamond_replacement_chance", 0.6,  0.0, 1.0);
			ancientDebrisReplacementChance = builder.comment("Chance to replace ores in ancient debris ore veins")
					.translation("unfaircraft.config.ore.ancient_debris_replacement_chance")
					.defineInRange("ancient_debris_replacement_chance", 0.5, 0.0, 1.0);

			builder.pop();
		}
	}

	public static class LootTable {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> trollChance;

		private LootTable(ModConfigSpec.Builder builder) {
			builder.push("loot_table");
			enabled = builder.comment("Enable Loot Table mixin")
					.translation("unfaircraft.config.loot_table.enabled")
					.define("enabled", true);
			trollChance = builder.comment("Chance for loot tables to be replaced with troll loot")
					.translation("unfaircraft.config.loot_table.troll_chance")
					.defineInRange("troll_chance", 0.05, 0.0, 1.0);
			builder.pop();
		}
	}

	public static class Sapling {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> deathChance;

		private Sapling(ModConfigSpec.Builder builder) {
			builder.push("sapling");
			enabled = builder.comment("Enable Sapling mixin")
					.translation("unfaircraft.config.sapling.enabled")
					.define("enabled", true);
			deathChance = builder.comment("Chance for saplings to 'die' every random tick")
					.translation("unfaircraft.config.sapling.death_chance")
					.defineInRange("death_chance", 0.005, 0.0, 1.0);
			builder.pop();
		}
	}

	public static class Farmland {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> revertChance;

		private Farmland(ModConfigSpec.Builder builder) {
			builder.push("farmland");
			enabled = builder.comment("Enable Farmland mixin")
					.translation("unfaircraft.config.farmland.enabled")
					.define("enabled", true);
			revertChance = builder.comment("Chance for farmland blocks to revert every random tick")
					.translation("unfaircraft.config.farmland.revert_chance")
					.defineInRange("revert_chance", 0.005, 0.0, 1.0);
			builder.pop();
		}
	}

	public static class MobDetection {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> rangeMultiplier;

		private MobDetection(ModConfigSpec.Builder builder) {
			builder.push("mob_detection");
			enabled = builder.comment("Enable Mob Detection mixin")
					.translation("unfaircraft.config.mob_detection.enabled")
					.define("enabled", true);
			rangeMultiplier = builder.comment("Multiplier for increased mob detection range")
					.translation("unfaircraft.config.mob_detection.range_multiplier")
					.define("range_multiplier", 4.0);
			builder.pop();
		}
	}

	public static class BlockInteraction {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> placeFailChance;
		public final ModConfigSpec.ConfigValue<Double> breakFailChance;

		private BlockInteraction(ModConfigSpec.Builder builder) {
			builder.push("block_interaction");
			enabled = builder.comment("Enable Block Interaction mixin")
					.translation("unfaircraft.config.block_interaction.enabled")
					.define("enabled", true);
			placeFailChance = builder.comment("Chance for block placements to fail")
					.translation("unfaircraft.config.block_interaction.place_fail_chance")
					.defineInRange("place_fail_chance", 0.05, 0.0, 1.0);
			breakFailChance = builder.comment("Chance for block breaking to fail")
					.translation("unfaircraft.config.block_interaction.break_fail_chance")
					.defineInRange("break_fail_chance", 0.05, 0.0, 1.0);
			builder.pop();
		}
	}

	public static class Bucket {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> waterFailChance;

		private Bucket(ModConfigSpec.Builder builder) {
			builder.push("bucket");
			enabled = builder.comment("Enable Bucket mixin")
					.translation("unfaircraft.config.bucket.enabled")
					.define("enabled", true);
			waterFailChance = builder.comment("Chance for water bucket placement to fail")
					.translation("unfaircraft.config.bucket.water_fail_chance")
					.defineInRange("water_fail_chance", 0.3, 0.0, 1.0);
			builder.pop();
		}
	}

	public static class Armour {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> protectionFailChance;
		public final ModConfigSpec.ConfigValue<Double> durabilityLossChance;
		public final ModConfigSpec.ConfigValue<Integer> durabilityDamageMin;
		public final ModConfigSpec.ConfigValue<Integer> durabilityDamageMax;

		private Armour(ModConfigSpec.Builder builder) {
			builder.push("armour");
			enabled = builder.comment("Enable Armour mixin")
					.translation("unfaircraft.config.armour.enabled")
					.define("enabled", true);
			protectionFailChance = builder.comment("Chance for armour to provide no protection when hit")
					.translation("unfaircraft.config.armour.protection_fail_chance")
					.defineInRange("protection_fail_chance", 0.15, 0.0, 1.0);
			durabilityLossChance = builder.comment("Chance for armour to take extra durability damage when hit")
					.translation("unfaircraft.config.armour.durability_loss_chance")
					.defineInRange("durability_loss_chance", 0.20, 0.0, 1.0);
			durabilityDamageMin = builder.comment("Minimum extra durability damage to armour")
					.translation("unfaircraft.config.armour.durability_damage_min")
					.defineInRange("durability_damage_min", 2, 1, 100);
			durabilityDamageMax = builder.comment("Maximum extra durability damage to armour")
					.translation("unfaircraft.config.armour.durability_damage_max")
					.defineInRange("durability_damage_max", 5, 1, 100);
			builder.pop();
		}
	}

	public static class Totem {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> failChance;

		private Totem(ModConfigSpec.Builder builder) {
			builder.push("totem");
			enabled = builder.comment("Enable Totem mixin")
					.translation("unfaircraft.config.totem.enabled")
					.define("enabled", true);
			failChance = builder.comment("Chance for totems of undying to fail to save the player")
					.translation("unfaircraft.config.totem.fail_chance")
					.defineInRange("fail_chance", 0.15, 0.0, 1.0);
			builder.pop();
		}
	}

	public static class Knockback {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> chance;
		public final ModConfigSpec.ConfigValue<Double> multiplier;

		private Knockback(ModConfigSpec.Builder builder) {
			builder.push("knockback");
			enabled = builder.comment("Enable Knockback mixin")
					.translation("unfaircraft.config.knockback.enabled")
					.define("enabled", true);
			chance = builder.comment("Chance for player to be knocked back when hitting a mob")
					.translation("unfaircraft.config.knockback.chance")
					.defineInRange("chance", 0.1, 0.0, 1.0);
			multiplier = builder.comment("Multiplier for knockback effect on player")
					.translation("unfaircraft.config.knockback.multiplier")
					.defineInRange("multiplier", 1.5, 1.0, 100.0);
			builder.pop();
		}
	}

	public static class DamageReflection {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> chance;
		public final ModConfigSpec.ConfigValue<Integer> percentageMin;
		public final ModConfigSpec.ConfigValue<Integer> percentageMax;
		public final ModConfigSpec.ConfigValue<Boolean> ignoreThorns;

		private DamageReflection(ModConfigSpec.Builder builder) {
			builder.push("damage_reflection");
			enabled = builder.comment("Enable Damage Reflection mixin")
					.translation("unfaircraft.config.damage_reflection.enabled")
					.define("enabled", true);
			chance = builder.comment("Chance for damage to be reflected back to player")
					.translation("unfaircraft.config.damage_reflection.chance")
					.defineInRange("chance", 0.1, 0.0, 1.0);
			percentageMin = builder.comment("Minimum percentage for damage to be reflected back to player")
					.translation("unfaircraft.config.damage_reflection.percentage_min")
					.defineInRange("percentage_min", 3, 1, 100);
			percentageMax = builder.comment("Maximum percentage for damage to be reflected back to player")
					.translation("unfaircraft.config.damage_reflection.percentage_max")
					.defineInRange("percentage_max", 5, 1, 100);
			ignoreThorns = builder.comment("Ignore thorns when reflecting damage back to player")
					.translation("unfaircraft.config.damage_reflection.ignore_thorns")
					.define("ignore_thorns", true);
			builder.pop();
		}
	}

	public static class Creeper {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> fuseSpeedMultiplier;
		public final ModConfigSpec.ConfigValue<Double> explosionRadiusMultiplier;

		private Creeper(ModConfigSpec.Builder builder) {
			builder.push("creeper");
			enabled = builder.comment("Enable Creeper mixin")
					.translation("unfaircraft.config.creeper.enabled")
					.define("enabled", true);
			fuseSpeedMultiplier = builder.comment("Multiplier for creeper fuse speed")
					.translation("unfaircraft.config.creeper.fuse_speed_multiplier")
					.defineInRange("fuse_speed_multiplier", 5.0, 1.0, 10.0);
			explosionRadiusMultiplier = builder.comment("Multiplier for creeper explosion radius")
					.translation("unfaircraft.config.creeper.explosion_radius_multiplier")
					.defineInRange("explosion_radius_multiplier", 1.5, 1.0, 100.0);
			builder.pop();
		}
	}

	public static class Skeleton {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> accuracyMultiplier;
		public final ModConfigSpec.ConfigValue<Integer> attackSpeedIncrease;

		private Skeleton(ModConfigSpec.Builder builder) {
			builder.push("skeleton");
			enabled = builder.comment("Enable Skeleton mixin")
					.translation("unfaircraft.config.skeleton.enabled")
					.define("enabled", true);
			accuracyMultiplier = builder.comment("Multiplier for skeleton accuracy")
					.translation("unfaircraft.config.skeleton.accuracy_multiplier")
					.defineInRange("accuracy_multiplier", 0.1, 0.0, 1.0);
			attackSpeedIncrease = builder.comment("Number of ticks to reduce from skeleton attack cooldown")
					.translation("unfaircraft.config.skeleton.attack_speed_increase")
					.defineInRange("attack_speed_increase", 2, 0, 10);
			builder.pop();
		}
	}

	public static class Enderman {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> proximityAggroRange;
		public final ModConfigSpec.ConfigValue<Double> proximityAggroChance;

		private Enderman(ModConfigSpec.Builder builder) {
			builder.push("enderman");
			enabled = builder.comment("Enable Enderman mixin")
					.translation("unfaircraft.config.enderman.enabled")
					.define("enabled", true);
			proximityAggroRange = builder.comment("Range in blocks for enderman to aggro")
					.translation("unfaircraft.config.enderman.proximity_aggro_range")
					.defineInRange("proximity_aggro_range", 16.0, 1.0, 64.0);
			proximityAggroChance = builder.comment("Chance per tick for enderman to become aggressive when player is nearby")
					.translation("unfaircraft.config.enderman.proximity_aggro_chance")
					.defineInRange("proximity_aggro_chance", 1.0, 0.0, 1.0);
			builder.pop();
		}
	}

	public static class Zombie {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> summonChance;
		public final ModConfigSpec.ConfigValue<Integer> summonMin;
		public final ModConfigSpec.ConfigValue<Integer> summonMax;

		private Zombie(ModConfigSpec.Builder builder) {
			builder.push("zombie");
			enabled = builder.comment("Enable Zombie mixin")
					.translation("unfaircraft.config.zombie.enabled")
					.define("enabled", true);
			summonChance = builder.comment("Chance for zombie to summon reinforcements when hit")
					.translation("unfaircraft.config.zombie.summon_chance")
					.defineInRange("summon_chance", 0.05, 0.0, 1.0);
			summonMin = builder.comment("Minimum number of zombies to summon")
					.translation("unfaircraft.config.zombie.summon_min")
					.define("summon_min", 1);
			summonMax = builder.comment("Maximum number of zombies to summon")
					.translation("unfaircraft.config.zombie.summon_max")
					.define("summon_max", 3);
			builder.pop();
		}
	}

	public static class Potion {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> backfireChance;

		private Potion(ModConfigSpec.Builder builder) {
			builder.push("potion");
			enabled = builder.comment("Enable Potion mixin")
					.translation("unfaircraft.config.potion.enabled")
					.define("enabled", true);
			backfireChance = builder.comment("Chance for potion effects to backfire on player")
					.translation("unfaircraft.config.potion.backfire_chance")
					.defineInRange("backfire_chance", 0.1, 0.0, 1.0);
			builder.pop();
		}
	}

	public static class MobRegen {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Integer> delay;
		public final ModConfigSpec.ConfigValue<Integer> rate;
		public final ModConfigSpec.ConfigValue<Double> amount;

		private MobRegen(ModConfigSpec.Builder builder) {
			builder.push("mob_regen");
			enabled = builder.comment("Enable Mob Regen mixin")
					.translation("unfaircraft.config.mob_regen.enabled")
					.define("enabled", true);
			delay = builder.comment("Ticks to wait after damage before mob regen starts")
					.translation("unfaircraft.config.mob_regen.delay")
					.defineInRange("delay", 300, 0, 72000);
			rate = builder.comment("How often to regenerate in ticks")
					.translation("unfaircraft.config.mob_regen.rate")
					.defineInRange("rate", 20, 0, 1200);
			amount = builder.comment("Health restored per regen tick")
					.translation("unfaircraft.config.mob_regen.amount")
					.defineInRange("amount", 0.5, 0.0, 10.0);
			builder.pop();
		}
	}

	public static class SetFire {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Integer> radius;
		public final ModConfigSpec.ConfigValue<Double> chance;
		public final ModConfigSpec.ConfigValue<Integer> initialDuration;
		public final ModConfigSpec.ConfigValue<Integer> durationIncrease;

		private SetFire(ModConfigSpec.Builder builder) {
			builder.push("set_fire");
			enabled = builder.comment("Enable Set Fire mixin")
					.translation("unfaircraft.config.set_fire.enabled")
					.define("enabled", true);
			radius = builder.comment("Radius to check for fire source blocks around the player")
					.translation("unfaircraft.config.set_fire.radius")
					.defineInRange("radius", 3, 1, 16);
			chance = builder.comment("Chance per tick for a player to be set on fire near a heat source")
					.translation("unfaircraft.config.set_fire.chance")
					.defineInRange("chance", 0.005, 0.0, 1.0);
			initialDuration = builder.comment("Initial duration for the player to be set on fire")
					.translation("unfaircraft.config.set_fire.initial_duration")
					.defineInRange("initial_duration", 40, 20, 6000);
			durationIncrease = builder.comment("Ticks to add to fire duration if player is already on fire")
					.translation("unfaircraft.config.set_fire.duration_increase")
					.defineInRange("duration_increase", 20, 1, 6000);
			builder.pop();
		}
	}

	public static class FoodData {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Double> exhaustionMultiplier;

		private FoodData(ModConfigSpec.Builder builder) {
			builder.push("food_data");
			enabled = builder.comment("Enable Food Data mixin")
					.translation("unfaircraft.config.food_data.enabled")
					.define("enabled", true);
			exhaustionMultiplier = builder.comment("Multiplier for food exhaustion rate")
							.translation("unfaircraft.config.food_data.exhaustion_multiplier")
									.defineInRange("exhaustion_multiplier", 3.0, 1.0, 10.0);
			builder.pop();
		}
	}

	public static class DrunkJumping {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;

		private DrunkJumping(ModConfigSpec.Builder builder) {
			builder.push("drunk_jumping");
			enabled = builder.comment("Enable Drunk Jumping")
					.translation("unfaircraft.config.drunk_jumping.enabled")
					.define("enabled", false);
			builder.pop();
		}
	}

	public static class NightmareEvent {
		public final ModConfigSpec.ConfigValue<Double> chance;

		private NightmareEvent(ModConfigSpec.Builder builder) {
			builder.push("nightmare_event");
			chance = builder.comment("Chance for a nightmare event to happen")
					.translation("unfaircraft.config.nightmare_event.chance")
					.defineInRange("chance", 0.0001, 0.0, 1.0);
			builder.pop();
		}
	}

	public static class MerchantOffer {
		public final ModConfigSpec.ConfigValue<Boolean> enabled;
		public final ModConfigSpec.ConfigValue<Integer> multiplier;

		private MerchantOffer(ModConfigSpec.Builder builder) {
			builder.push("merchant_offer");
			enabled = builder.comment("Enable Villager Price Gouging")
					.translation("unfaircraft.config.merchant_offer.enabled")
					.define("enabled", true);
			multiplier = builder.comment("Multiplier for merchant prices")
					.translation("unfaircraft.config.merchant_offer.multiplier")
					.define("multiplier", 3);
			builder.pop();
		}
	}

	static {
		BUILDER.push("general");
		ENABLE_UNFAIR_MODE = BUILDER.comment("Master toggle for UnfairCraft")
				.translation("unfaircraft.config.general.enable_unfair_mode")
				.define("enable_unfair_mode", true);
		ENABLE_NIGHTMARE_MODE = BUILDER.comment("Master toggle for Nightmare Mode")
				.translation("unfaircraft.config.general.enable_nightmare_mode")
				.define("enable_nightmare_mode", false);
		BUILDER.pop();

		BED = new Bed(BUILDER);
		SHIELD = new Shield(BUILDER);
		PLAYER = new Player(BUILDER);
		ITEM_DURABILITY = new ItemDurability(BUILDER);
		BOW = new Bow(BUILDER);
		CHEST = new Chest(BUILDER);
		ANVIL = new Anvil(BUILDER);
		MINECART = new Minecart(BUILDER);
		FOOD = new Food(BUILDER);
		CAVE_CARVER = new CaveCarver(BUILDER);
		ORE = new Ore(BUILDER);
		LOOT_TABLE = new LootTable(BUILDER);
		SAPLING = new Sapling(BUILDER);
		FARMLAND = new Farmland(BUILDER);
		MOB_DETECTION = new MobDetection(BUILDER);
		BLOCK_INTERACTION = new BlockInteraction(BUILDER);
		BUCKET = new Bucket(BUILDER);
		ARMOUR = new Armour(BUILDER);
		TOTEM = new Totem(BUILDER);
		KNOCKBACK = new Knockback(BUILDER);
		DAMAGE_REFLECTION = new DamageReflection(BUILDER);
		CREEPER = new Creeper(BUILDER);
		SKELETON = new Skeleton(BUILDER);
		ENDERMAN = new Enderman(BUILDER);
		ZOMBIE = new Zombie(BUILDER);
		POTION = new Potion(BUILDER);
		MOB_REGEN = new MobRegen(BUILDER);
		SET_FIRE = new SetFire(BUILDER);
		FOOD_DATA = new FoodData(BUILDER);
		DRUNK_JUMPING = new DrunkJumping(BUILDER);
		NIGHTMARE_EVENT = new NightmareEvent(BUILDER);
		MERCHANT_OFFER = new MerchantOffer(BUILDER);
	}

	public static final ModConfigSpec SPEC = BUILDER.build();
}
