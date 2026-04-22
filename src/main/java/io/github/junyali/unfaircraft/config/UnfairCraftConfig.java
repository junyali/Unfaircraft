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

	public static class NightmareEvent {

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
