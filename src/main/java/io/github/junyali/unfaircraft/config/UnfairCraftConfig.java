package io.github.junyali.unfaircraft.config;

import io.wispforest.owo.config.annotation.*;

@Modmenu(modId = "unfaircraft")
@Config(name = "unfaircraft", wrapperName = "UnfaircraftConfig")
public class UnfairCraftConfig {
	@SectionHeader("general")
	public boolean enableUnfairMode = true;
	public boolean enableNightmareMode = false;

	@SectionHeader("nightmareEvent")
	@Nest
	public NightmareEvent nightmareEvent = new NightmareEvent();

	@SectionHeader("bed")
	@Nest
	public Bed bed = new Bed();

	@SectionHeader("shield")
	@Nest
	public Shield shield = new Shield();

	@SectionHeader("player")
	@Nest
	public Player player = new Player();

	@SectionHeader("itemDurability")
	@Nest
	public ItemDurability itemDurability = new ItemDurability();

	@SectionHeader("bow")
	@Nest
	public Bow bow = new Bow();

	@SectionHeader("chest")
	@Nest
	public Chest chest = new Chest();

	@SectionHeader("anvil")
	@Nest
	public Anvil anvil = new Anvil();

	@SectionHeader("minecart")
	@Nest
	public Minecart minecart = new Minecart();

	@SectionHeader("food")
	@Nest
	public Food food = new Food();

	@SectionHeader("caveCarver")
	@Nest
	public CaveCarver caveCarver = new CaveCarver();

	@SectionHeader("ore")
	@Nest
	public Ore ore = new Ore();

	@SectionHeader("lootTable")
	@Nest
	public LootTable lootTable = new LootTable();

	@SectionHeader("sapling")
	@Nest
	public Sapling sapling = new Sapling();

	@SectionHeader("farmland")
	@Nest
	public Farmland farmland = new Farmland();

	@SectionHeader("mobDetection")
	@Nest
	public MobDetection mobDetection = new MobDetection();

	@SectionHeader("blockInteraction")
	@Nest
	public BlockInteraction blockInteraction = new BlockInteraction();

	@SectionHeader("bucket")
	@Nest
	public Bucket bucket = new Bucket();

	@SectionHeader("armour")
	@Nest
	public Armour armour = new Armour();

	@SectionHeader("totem")
	@Nest
	public Totem totem = new Totem();

	@SectionHeader("knockback")
	@Nest
	public Knockback knockback = new Knockback();

	@SectionHeader("damageReflection")
	@Nest
	public DamageReflection damageReflection = new DamageReflection();

	@SectionHeader("creeper")
	@Nest
	public Creeper creeper = new Creeper();

	@SectionHeader("skeleton")
	@Nest
	public Skeleton skeleton = new Skeleton();

	@SectionHeader("enderman")
	@Nest
	public Enderman enderman = new Enderman();

	@SectionHeader("zombie")
	@Nest
	public Zombie zombie = new Zombie();

	@SectionHeader("potion")
	@Nest
	public Potion potion = new Potion();

	@SectionHeader("mobRegen")
	@Nest
	public MobRegen mobRegen = new MobRegen();

	@SectionHeader("setFire")
	@Nest
	public SetFire setFire = new SetFire();

	@SectionHeader("foodData")
	@Nest
	public FoodData foodData = new FoodData();

	@SectionHeader("drunkJumping")
	@Nest
	public DrunkJumping drunkJumping = new DrunkJumping();

	@SectionHeader("merchantOffer")
	@Nest
	public MerchantOffer merchantOffer = new MerchantOffer();

	@SectionHeader("ironGolem")
	@Nest
	public IronGolem ironGolem = new IronGolem();

	@SectionHeader("mob")
	@Nest
	public Mob mob = new Mob();

	@SectionHeader("weather")
	@Nest
	public Weather weather = new Weather();

	@SectionHeader("phantom")
	@Nest
	public Phantom phantom = new Phantom();

	@SectionHeader("block")
	@Nest
	public Block block = new Block();

	@SectionHeader("ghast")
	@Nest
	public Ghast ghast = new Ghast();

	@SectionHeader("blaze")
	@Nest
	public Blaze blaze = new Blaze();

	@SectionHeader("enderDragon")
	@Nest
	public EnderDragon enderDragon = new EnderDragon();

	@SectionHeader("endCrystal")
	@Nest
	public EndCrystal endCrystal = new EndCrystal();

	@SectionHeader("shulker")
	@Nest
	public Shulker shulker = new Shulker();

	@SectionHeader("door")
	@Nest
	public Door door = new Door();

	@SectionHeader("furnace")
	@Nest
	public Furnace furnace = new Furnace();

	@SectionHeader("glass")
	@Nest
	public Glass glass = new Glass();


	public static class NightmareEvent {
		@RangeConstraint(min = 0d, max = 1d)
		public double chance = 0.05;
	}
	public static class Bed {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double explosionChance = 0.25;

		public double explosionRadius = 5.0;

		@RangeConstraint(min = 0d, max = 1d)
		public double fireChance = 0.5;

		public int fireDuration = 200;
	}
	public static class Shield {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double failChance = 0.10;
	}
	public static class Player {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double critFailChance = 0.25;

		@RangeConstraint(min = 0d, max = 1d)
		public double selfAttackChance = 0.05;

		@RangeConstraint(min = 0d, max = 1d)
		public double attackExhaustionChance = 0.2;

		@RangeConstraint(min = 0d, max = 1d)
		public double randomDropChance = 0.0000005;

		public double fallDamageDistance = 1.5;

		public double fallDamageMultiplier = 3.0;

		@RangeConstraint(min = 0d, max = 1d)
		public double pickupFailChance = 0.01;

		public boolean enableBiomeHazards = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double groundBreakChance = 0.005;

		@RangeConstraint(min = 0d, max = 1d)
		public double lowMiningMoraleChance = 0.01;

		@RangeConstraint(min = 0d, max = 1d)
		public double ladderSlipChance = 0.1;

	}
	public static class ItemDurability {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double lossChance = 0.05;

		public int damageMin = 2;

		public int damageMax = 20;
	}
	public static class Bow {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double wonkyChance = 0.25;

		public double projectileDeviation = 0.8;

		@RangeConstraint(min = 0d, max = 1d)
		public double misfireChance = 0.5;

		@RangeConstraint(min = 0d, max = 1d)
		public double backfireChance = 0.4;

		public double backfireDamageMin = 2.0;

		public double backfireDamageMax = 20.0;
	}
	public static class Chest {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double eatChance = 0.15;

		public int eatItemMin = 1;

		public int eatItemMax = 32;
	}
	public static class Anvil {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double instantBreakChance = 0.1;

		@RangeConstraint(min = 0d, max = 1d)
		public double costIncreaseChance = 0.05;

		public double costMultiplierMin = 2.0;

		public double costMultiplierMax = 6.0;
	}
	public static class Minecart {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double slowdownFactor = 0.5;

		@RangeConstraint(min = 0d, max = 1d)
		public double stopChance = 0.05;

		@RangeConstraint(min = 0d, max = 1d)
		public double reverseChance = 0.01;
	}
	public static class Food {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double failChance = 0.05;

		@RangeConstraint(min = 0d, max = 1d)
		public double debuffChance = 0.15;
	}
	public static class CaveCarver {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double extraLavaPocketChance = 0.8;
	}
	public static class Ore {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double defaultReplacementChance = 0.3;

		@RangeConstraint(min = 0d, max = 1d)
		public double goldReplacementChance = 0.4;

		@RangeConstraint(min = 0d, max = 1d)
		public double emeraldReplacementChance = 0.5;

		@RangeConstraint(min = 0d, max = 1d)
		public double diamondReplacementChance = 0.6;

		@RangeConstraint(min = 0d, max = 1d)
		public double ancientDebrisReplacementChance = 0.5;
	}
	public static class LootTable {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double trollChance = 0.05;
	}
	public static class Sapling {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double deathChance = 0.005;
	}
	public static class Farmland {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double revertChance = 0.005;
	}
	public static class MobDetection {
		public boolean enabled = true;

		public double rangeMultiplier = 4.0;
	}
	public static class BlockInteraction {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double placeFailChance = 0.05;

		@RangeConstraint(min = 0d, max = 1d)
		public double breakFailChance = 0.05;
	}
	public static class Bucket {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double waterFailChance = 0.3;
	}
	public static class Armour {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double protectionFailChance = 0.15;

		@RangeConstraint(min = 0d, max = 1d)
		public double durabilityLossChance = 0.20;

		@RangeConstraint(min = 1d, max = 100d)
		public int durabilityDamageMin = 2;

		@RangeConstraint(min = 1d, max = 100d)
		public int durabilityDamageMax = 5;
	}
	public static class Totem {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double failChance = 0.15;
	}
	public static class Knockback {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double chance = 0.1;

		@RangeConstraint(min = 1d, max = 100d)
		public double multiplier = 1.5;
	}
	public static class DamageReflection {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double chance = 0.1;

		@RangeConstraint(min = 1d, max = 100d)
		public int percentageMin = 3;

		@RangeConstraint(min = 1d, max = 100d)
		public int percentageMax = 5;

		public boolean ignoreThorns = true;
	}
	public static class Creeper {
		public boolean enabled = true;

		@RangeConstraint(min = 1d, max = 10d)
		public double fuseSpeedMultiplier = 5.0;

		@RangeConstraint(min = 1d, max = 100d)
		public double explosionRadiusMultiplier = 1.5;
	}
	public static class Skeleton {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double accuracyMultiplier = 0.1;

		@RangeConstraint(min = 0d, max = 10d)
		public int attackSpeedIncrease = 2;
	}
	public static class Enderman {
		public boolean enabled = true;

		@RangeConstraint(min = 1d, max = 64d)
		public double proximityAggroRange = 16.0;

		@RangeConstraint(min = 0d, max = 1d)
		public double proximityAggroChance = 1.0;
	}
	public static class Zombie {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double summonChance = 0.05;

		public int summonMin = 1;

		public int summonMax = 3;
	}
	public static class Potion {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double backfireChance = 0.1;
	}
	public static class MobRegen {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 72000d)
		public int delay = 300;

		@RangeConstraint(min = 0d, max = 1200d)
		public int rate = 20;

		@RangeConstraint(min = 0d, max = 10d)
		public double amount = 0.5;
	}
	public static class SetFire {
		public boolean enabled = true;

		@RangeConstraint(min = 1d, max = 16d)
		public int radius = 3;

		@RangeConstraint(min = 0d, max = 1d)
		public double chance = 0.005;

		@RangeConstraint(min = 20d, max = 6000d)
		public int initialDuration = 40;

		@RangeConstraint(min = 1d, max = 6000d)
		public int durationIncrease = 20;
	}
	public static class FoodData {
		public boolean enabled = true;

		@RangeConstraint(min = 1d, max = 10d)
		public double exhaustionMultiplier = 3.0;
	}
	public static class DrunkJumping {
		public boolean enabled = false;
	}
	public static class MerchantOffer {
		public boolean enabled = true;

		public int multiplier = 3;

		public boolean wanderingTraderScamEnabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double wanderingTraderScamChance = 0.25;

		public boolean villagerRetaliation = true;
	}
	public static class IronGolem {
		public boolean enabled = true;
	}
	public static class Mob {
		public boolean hostileRegardlessEnabled = true;

		public boolean passiveRetaliationEnabled = true;

		public boolean allowHostileDaylightSpawn = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double hostileDaylightSpawnChance = 0.02;
	}
	public static class Weather {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double escalateThunderChance = 0.005;

		@RangeConstraint(min = 0d, max = 1d)
		public double remainThunderChance = 0.3;
	}
	public static class Phantom {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double summonChance = 0.8;

		public int summonMin = 3;

		public int summonMax = 6;
	}
	public static class Block {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double mimicSpawnChance = 0.15;

		@RangeConstraint(min = 0d, max = 1d)
		public double caveInChance = 0.05;

		@RangeConstraint(min = 0d, max = 1d)
		public double silkTouchFailChance = 0.2;

		@RangeConstraint(min = 0d, max = 1d)
		public double fortuneReversalChance = 0.2;

		@RangeConstraint(min = 0d, max = 1d)
		public double veinCollapseChance = 0.05;
	}
	public static class Ghast {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double summonChance = 0.5;

		public int summonMin = 2;

		public int summonMax = 4;
	}
	public static class Blaze {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double summonChance = 0.5;

		public int summonMin = 2;

		public int summonMax = 4;
	}
	public static class EnderDragon {
		public boolean enabled = true;

		public double healthMultiplier = 2.0;

		public double speedMultiplier = 2.5;

		@RangeConstraint(min = 0d, max = 1d)
		public double aggressionChance = 0.02;
	}
	public static class EndCrystal {
		public boolean enabled = true;

		public double healingMultiplier = 3.0;

		public double explosionRadius = 12.0;
	}
	public static class Shulker {
		public boolean enabled = true;

		public double bulletMovementVector = 2.2;

		public int bulletDistanceTarget = 128;
	}
	public static class Door {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double jamChance = 0.2;
	}
	public static class Furnace {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double fuelTheftChance = 0.02;

		@RangeConstraint(min = 0d, max = 1d)
		public double smeltTheftChance = 0.05;

		public int explosionThreshold = 1200;

		@RangeConstraint(min = 0d, max = 1d)
		public double explosionChance = 0.01;
	}
	public static class Glass {
		public boolean enabled = true;

		@RangeConstraint(min = 0d, max = 1d)
		public double breakChance = 0.10;
	}
}
