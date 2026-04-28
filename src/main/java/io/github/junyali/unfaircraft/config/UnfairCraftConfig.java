package io.github.junyali.unfaircraft.config;

import io.wispforest.owo.config.annotation.*;

@Modmenu(modId = "unfaircraft")
@Config(name = "unfaircraft", wrapperName = "UnfaircraftConfig")
public class UnfairCraftConfig {
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

	}
	public static class Bed {

	}
	public static class Shield {

	}
	public static class Player {

	}
	public static class ItemDurability {

	}
	public static class Bow {

	}
	public static class Chest {

	}
	public static class Anvil {

	}
	public static class Minecart {

	}
	public static class Food {

	}
	public static class CaveCarver {

	}
	public static class Ore {

	}
	public static class LootTable {

	}
	public static class Sapling {

	}
	public static class Farmland {

	}
	public static class MobDetection {

	}
	public static class BlockInteraction {

	}
	public static class Bucket {

	}
	public static class Armour {

	}
	public static class Totem {

	}
	public static class Knockback {

	}
	public static class DamageReflection {

	}
	public static class Creeper {

	}
	public static class Skeleton {

	}
	public static class Enderman {

	}
	public static class Zombie {

	}
	public static class Potion {

	}
	public static class MobRegen {

	}
	public static class SetFire {

	}
	public static class FoodData {

	}
	public static class DrunkJumping {

	}
	public static class MerchantOffer {

	}
	public static class IronGolem {

	}
	public static class Mob {

	}
	public static class Weather {

	}
	public static class Phantom {

	}
	public static class Block {

	}
	public static class Ghast {

	}
	public static class Blaze {

	}
	public static class EnderDragon {

	}
	public static class EndCrystal {

	}
	public static class Shulker {

	}
	public static class Door {

	}
	public static class Furnace {

	}
	public static class Glass {

	}
}
