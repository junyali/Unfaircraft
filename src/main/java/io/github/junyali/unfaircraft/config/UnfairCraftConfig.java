package io.github.junyali.unfaircraft.config;

import net.neoforged.neoforge.common.ModConfigSpec;

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
		public final ModConfigSpec.ConfigValue<Double> backfireDamaeMax;

		private Bow(ModConfigSpec.Builder builder) {
			builder.push("bow");
			enabled = builder.comment("Enable Bow mixin")
					.translation("unfaircraft.config.bow.enabled")
					.define("enabled", true);
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
	}

	public static final ModConfigSpec SPEC = BUILDER.build();
}
