package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.BooleanSupplier;

@Mixin(ServerLevel.class)
public class NightmareEventMixin {
	@Unique
	private void unfaircraft$triggerRandomEvent(ServerLevel level) {
		List<ServerPlayer> players = level.players();
		if (players.isEmpty()) return;

		Player randomPlayer = players.get(level.random.nextInt(players.size()));
		int eventType = level.random.nextInt(4);

		switch (eventType) {
			case 0 -> unfaircraft$tntRain(level, randomPlayer);
			case 1 -> unfaircraft$instantSmite(level, randomPlayer);
			case 2 -> unfaircraft$spawnArmouredMobs(level, randomPlayer);
			case 3 -> unfaircraft$inventoryDrop(level, randomPlayer);
			case 4 -> unfaircraft$launchPlayer(level, randomPlayer);
		}
	}

	@Inject(
			method = "tick",
			at = @At("TAIL")
	)
	private void triggerNightmareEvent(BooleanSupplier hasTimeLeft, CallbackInfo ci) {
		if (!UnfairCraftConfig.ENABLE_NIGHTMARE_MODE.get()) {
			return;
		}

		ServerLevel level = (ServerLevel) (Object) this;

		if (level.getGameTime() % 20 != 0 ) return;

		if (level.random.nextFloat() < UnfairCraftConfig.NIGHTMARE_EVENT_CHANCE.get().floatValue()) {
			unfaircraft$triggerRandomEvent(level);
		}
	}

	@Unique
	private void unfaircraft$tntRain(ServerLevel level, Player player) {
		BlockPos playerPos = player.blockPosition();

		for (int i = 0; i < 8 + level.random.nextInt(32); i++) {
			BlockPos tntPos = playerPos.offset(
					level.random.nextInt(16) - 8,
					16 + level.random.nextInt(16),
					level.random.nextInt(16) - 8
			);

			PrimedTnt tnt = new PrimedTnt(level, tntPos.getX(), tntPos.getY(), tntPos.getZ(), null);
			tnt.setFuse(20 + level.random.nextInt(40));
			level.addFreshEntity(tnt);
		}
	}

	@Unique
	private void unfaircraft$instantSmite(ServerLevel level, Player player) {
		LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
		if (lightning != null) {
			lightning.moveTo(player.getX(), player.getY(), player.getZ());
			level.addFreshEntity(lightning);
		}

		level.getServer().tell(new TickTask(20 + level.random.nextInt(60), () -> {
			LightningBolt delayedLightning = EntityType.LIGHTNING_BOLT.create(level);
			if (delayedLightning != null) {
				BlockPos nearPlayer = player.blockPosition().offset(
						level.random.nextInt(8) - 4,
						0,
						level.random.nextInt(8) - 4
				);
				delayedLightning.moveTo(nearPlayer.getX(), nearPlayer.getY(), nearPlayer.getZ());
				level.addFreshEntity(delayedLightning);
			}
		}));
	}

	@Unique
	private ItemStack unfaircraft$createRandomArmourPiece(RandomSource random, String pieceType) {
		Item[] helmets = {Items.LEATHER_HELMET, Items.CHAINMAIL_HELMET, Items.IRON_HELMET, Items.GOLDEN_HELMET, Items.DIAMOND_HELMET};
		Item[] chestplates = {Items.LEATHER_CHESTPLATE, Items.CHAINMAIL_CHESTPLATE, Items.IRON_CHESTPLATE, Items.GOLDEN_CHESTPLATE, Items.DIAMOND_CHESTPLATE};
		Item[] leggings = {Items.LEATHER_LEGGINGS, Items.CHAINMAIL_LEGGINGS, Items.IRON_LEGGINGS, Items.GOLDEN_LEGGINGS, Items.DIAMOND_LEGGINGS};
		Item[] boots = {Items.LEATHER_BOOTS, Items.CHAINMAIL_BOOTS, Items.IRON_BOOTS, Items.GOLDEN_BOOTS, Items.DIAMOND_BOOTS};

		Item selectedItem = switch (pieceType) {
			case "helmet" -> helmets[random.nextInt(helmets.length)];
			case "chestplate" -> chestplates[random.nextInt(chestplates.length)];
			case "leggings" -> leggings[random.nextInt(leggings.length)];
			case "boots" -> boots[random.nextInt(boots.length)];
			default -> throw new IllegalStateException("Unexpected value: " + pieceType);
		};

		return new ItemStack(selectedItem);
	}

	@Unique
	private ItemStack unfaircraft$createRandomWeapon(RandomSource random) {
		Item[] weapons = {Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD, Items.GOLDEN_SWORD, Items.DIAMOND_SWORD};

		return new ItemStack(weapons[random.nextInt(weapons.length)]);
	}

	@Unique
	private void unfaircraft$spawnArmouredMobs(ServerLevel level, Player player) {
		BlockPos playerPos = player.blockPosition();

		for (int i = 0; i < 8 + level.random.nextInt(8); i++) {
			BlockPos spawnPos = playerPos.offset(
					level.random.nextInt(24) - 12,
					0,
					level.random.nextInt(24) - 12
			);

			while (spawnPos.getY() > level.getMinBuildHeight() && level.getBlockState(spawnPos).isAir()) {
				spawnPos = spawnPos.below();
			}
			spawnPos = spawnPos.above();

			int mobType = level.random.nextInt(2);
			switch (mobType) {
				case 0 -> unfaircraft$spawnArmouredZombie(level, spawnPos);
				case 1 -> unfaircraft$spawnArmouredSkeleton(level, spawnPos);
				case 2 -> unfaircraft$spawnArmouredCreeper(level, spawnPos);
				case 3 -> unfaircraft$spawnArmouredSpider(level, spawnPos);
			}
		}
	}

	@Unique
	private void unfaircraft$spawnArmouredZombie(ServerLevel level, BlockPos blockPos) {
		Zombie zombie = EntityType.ZOMBIE.create(level);
		if (zombie != null) {
			zombie.setPos(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);

			zombie.setItemSlot(EquipmentSlot.HEAD, unfaircraft$createRandomArmourPiece(level.random, "helmet"));
			zombie.setItemSlot(EquipmentSlot.CHEST, unfaircraft$createRandomArmourPiece(level.random, "chestplate"));
			zombie.setItemSlot(EquipmentSlot.LEGS, unfaircraft$createRandomArmourPiece(level.random, "leggings"));
			zombie.setItemSlot(EquipmentSlot.FEET, unfaircraft$createRandomArmourPiece(level.random, "boots"));
			zombie.setItemSlot(EquipmentSlot.MAINHAND, unfaircraft$createRandomWeapon(level.random));

			zombie.setDropChance(EquipmentSlot.HEAD, 0.0f);
			zombie.setDropChance(EquipmentSlot.CHEST, 0.0f);
			zombie.setDropChance(EquipmentSlot.LEGS, 0.0f);
			zombie.setDropChance(EquipmentSlot.FEET, 0.0f);

			if (level.random.nextFloat() < 0.3f) {
				zombie.setBaby(true);
			}

			level.addFreshEntity(zombie);
		}
	}

	@Unique
	private void unfaircraft$spawnArmouredSkeleton(ServerLevel level, BlockPos blockPos) {
		Skeleton skeleton = EntityType.SKELETON.create(level);
		if (skeleton != null) {
			skeleton.setPos(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);

			skeleton.setItemSlot(EquipmentSlot.HEAD, unfaircraft$createRandomArmourPiece(level.random, "helmet"));
			skeleton.setItemSlot(EquipmentSlot.CHEST, unfaircraft$createRandomArmourPiece(level.random, "chestplate"));
			skeleton.setItemSlot(EquipmentSlot.LEGS, unfaircraft$createRandomArmourPiece(level.random, "leggings"));
			skeleton.setItemSlot(EquipmentSlot.FEET, unfaircraft$createRandomArmourPiece(level.random, "boots"));

			if (level.random.nextBoolean()) {
				ItemStack bow = new ItemStack(Items.BOW);
				skeleton.setItemSlot(EquipmentSlot.MAINHAND, bow);
			} else {
				skeleton.setItemSlot(EquipmentSlot.MAINHAND, unfaircraft$createRandomWeapon(level.random));
			}

			skeleton.setDropChance(EquipmentSlot.HEAD, 0.0f);
			skeleton.setDropChance(EquipmentSlot.CHEST, 0.0f);
			skeleton.setDropChance(EquipmentSlot.LEGS, 0.0f);
			skeleton.setDropChance(EquipmentSlot.FEET, 0.0f);

			if (level.random.nextFloat() < 0.3f) {
				skeleton.setBaby(true);
			}

			level.addFreshEntity(skeleton);
		}
	}

	@Unique
	private void unfaircraft$spawnArmouredCreeper(ServerLevel level, BlockPos blockPos) {
		Creeper creeper = EntityType.CREEPER.create(level);
		if (creeper != null) {
			creeper.setPos(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);

			if (level.random.nextFloat() < 0.3f) {
				LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
				if (lightning != null) {
					creeper.thunderHit(level, lightning);
				}
			}

			level.addFreshEntity(creeper);
		}
	}

	@Unique
	private void unfaircraft$spawnArmouredSpider(ServerLevel level, BlockPos blockPos) {
		boolean isCaveSpider = level.random.nextFloat() < 0.3f;

		Spider spider;
		if (isCaveSpider) {
			spider = EntityType.CAVE_SPIDER.create(level);
		} else {
			spider = EntityType.SPIDER.create(level);
		}

		if (spider != null) {
			spider.setPos(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
			if (level.random.nextFloat() < 0.3f) {
				Skeleton rider = EntityType.SKELETON.create(level);
				if (rider != null) {
					rider.setPos(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
					rider.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
					rider.startRiding(spider);
				}
			}

			level.addFreshEntity(spider);
		}
	}

	@Unique
	private void unfaircraft$inventoryDrop(ServerLevel level, Player player) {
		for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
			ItemStack stack = player.getInventory().getItem(i);
			if (!stack.isEmpty()) {
				player.drop(stack, true);
				player.getInventory().setItem(i, ItemStack.EMPTY);
			}
		}
	}

	@Unique
	private void unfaircraft$launchPlayer(ServerLevel level, Player player) {
		player.setDeltaMovement(player.getDeltaMovement().add(0, 5.0, 0));
		player.hasImpulse = true;
	}
}
