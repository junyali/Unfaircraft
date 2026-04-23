package io.github.junyali.unfaircraft.mixin.entity;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Zombie.class)
public class ZombieMixin {
	@Unique
	private static final boolean unfaircraft$enabled = UnfairCraftConfig.isEnabled(UnfairCraftConfig.ZOMBIE.enabled);

	@Inject(
			method = "setBaby",
			at = @At("TAIL")
	)
	private void onSetBaby(boolean baby, CallbackInfo ci) {
		if (!unfaircraft$enabled) {
			return;
		}

		if (!baby) {
			return;
		}

		Zombie zombie = (Zombie) (Object) this;

		if (zombie.level().isClientSide() || zombie.isDeadOrDying()) {
			return;
		}

		AttributeInstance attackDamage = zombie.getAttribute(Attributes.ATTACK_DAMAGE);
		if (attackDamage != null) {
			attackDamage.setBaseValue(3.0);
		}

		AttributeInstance movementSpeed = zombie.getAttribute(Attributes.MOVEMENT_SPEED);
		if (movementSpeed != null) {
			double currentSpeed = movementSpeed.getBaseValue();
			movementSpeed.setBaseValue(currentSpeed * 1.5);
		}
	}

	@Inject(
			method = "hurt",
			at = @At("HEAD")
	)
	private void onHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if (!unfaircraft$enabled) {
			return;
		}

		Zombie zombie = (Zombie) (Object) this;

		if (zombie.level().isClientSide() || zombie.isDeadOrDying()) {
			return;
		}

		if (!(source.getEntity() instanceof Player)) {
			return;
		}

		if (zombie.level().random.nextFloat() < UnfairCraftConfig.ZOMBIE.summonChance.get()) {
			unfaircraft$summonZombies(zombie);
		}


	}

	@Unique
	private void unfaircraft$summonZombies(Zombie zombie) {
		ServerLevel level = (ServerLevel) zombie.level();
		int minZombies = UnfairCraftConfig.ZOMBIE.summonMin.get();
		int maxZombies = UnfairCraftConfig.ZOMBIE.summonMax.get();
		int zombiesToSummon = minZombies + level.random.nextInt(maxZombies - minZombies + 1);
		int summonRadius = 5;

		BlockPos zombiePos = zombie.blockPosition();

		for (int i = 0; i < zombiesToSummon; i++) {
			int offsetX = level.random.nextInt(summonRadius * 2 + 1) - summonRadius;
			int offsetZ = level.random.nextInt(summonRadius * 2 + 1) - summonRadius;
			int offsetY = level.random.nextInt(3) - 1;

			BlockPos spawnPos = zombiePos.offset(offsetX, offsetY, offsetZ);

			Zombie newZombie = EntityType.ZOMBIE.create(level);
			if (newZombie != null) {
				newZombie.moveTo(spawnPos.getX() + 0.5, spawnPos.getY() + 0.5, spawnPos.getZ() + 0.5);
				level.addFreshEntity(newZombie);
			}
		}
	}
}
