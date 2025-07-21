package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {
	@Inject(
			method = "attack",
			at = @At("HEAD"),
			cancellable = true
	)
	private void beforeAttack(Entity target, CallbackInfo ci) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_PLAYER_MIXIN.get()) {
			return;
		}

		Player player = (Player) (Object) this;

		boolean isCritical = player.getAttackStrengthScale(0.5f) > 0.9f &&
				player.fallDistance > 0.0f &&
				!player.onGround() &&
				!player.onClimbable() &&
				!player.isInWater() &&
				!player.isPassenger();

		if (isCritical) {
			if (player.level().random.nextFloat() < UnfairCraftConfig.PLAYER_CRIT_FAIL_CHANCE.get().floatValue()) {
				ci.cancel();
			}
		}

		if (player.level().random.nextFloat() < UnfairCraftConfig.PLAYER_SELF_ATTACK_CHANCE.get().floatValue()) {
			float damage = 1.0f + player.level().random.nextFloat() * 2.0f;
			player.hurt(player.level().damageSources().generic(), damage);
			player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_HURT, SoundSource.PLAYERS, 1.0f, 1.0f);
		}

		if (player.level().random.nextFloat() < UnfairCraftConfig.PLAYER_ATTACK_EXHAUSTION_CHANCE.get().floatValue()) {
			player.causeFoodExhaustion(4.0f);
		}
	}

	@Inject(
			method = "tick",
			at = @At("TAIL")
	)
	private void onPlayerTick(CallbackInfo ci) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_PLAYER_MIXIN.get()) {
			return;
		}

		Player player = (Player) (Object) this;

		if (!player.level().isClientSide() && player.level().random.nextFloat() < UnfairCraftConfig.PLAYER_RANDOM_DROP_CHANCE.get().floatValue()) {
			ItemStack mainHandItem = player.getItemInHand(InteractionHand.MAIN_HAND);
			if (!mainHandItem.isEmpty()) {
				unfaircraft$dropItemAndPlaySound(player, mainHandItem);
				player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
				return;
			}

			ItemStack offHandItem = player.getItemInHand(InteractionHand.OFF_HAND);
			if (!offHandItem.isEmpty()) {
				unfaircraft$dropItemAndPlaySound(player, offHandItem);
				player.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
				return;
			}

			Inventory inventory = player.getInventory();
			for (int i = 0; i < inventory.getContainerSize(); i++) {
				ItemStack stack = inventory.getItem(i);
				if (!stack.isEmpty()) {
					unfaircraft$dropItemAndPlaySound(player, stack.copy());
					inventory.setItem(i, ItemStack.EMPTY);
					return;
				}
			}
		}
	}

	@ModifyVariable(
			method = "causeFallDamage",
			at = @At("HEAD"),
			argsOnly = true,
			ordinal = 0
	)
	private float increaseFallDamage(float fallDistance) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_PLAYER_MIXIN.get()) {
			return fallDistance;
		}

		if (fallDistance > UnfairCraftConfig.PLAYER_FALL_DAMAGE_DISTANCE.get().floatValue()) {
			return fallDistance * UnfairCraftConfig.PLAYER_FALL_DAMAGE_MULTIPLIER.get().floatValue();
		}

		return fallDistance;
	}

	@Unique
	private void unfaircraft$dropItemAndPlaySound(Player player, ItemStack stack) {
		player.drop(stack, true);
		player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2f, ((player.level().random.nextFloat() - player.level().random.nextFloat()) * 0.7f + 1.0f) * 2.0f);
	}
}
