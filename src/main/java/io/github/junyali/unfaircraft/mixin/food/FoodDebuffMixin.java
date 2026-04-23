package io.github.junyali.unfaircraft.mixin.food;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class FoodDebuffMixin {
	@Unique
	private static final boolean unfaircraft$enabled = UnfairCraftConfig.isEnabled(UnfairCraftConfig.FOOD.enabled);

	@Unique
	private int unfaircraft$hungerBeforeEat = -1;

	@Inject(
			method = "eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/food/FoodProperties;)Lnet/minecraft/world/item/ItemStack;",
			at = @At("HEAD")
	)
	private void onEatHead(Level level, ItemStack stack, FoodProperties foodProperties, CallbackInfoReturnable<ItemStack> cir) {
		if (!unfaircraft$enabled) {
			return;
		}

		if (level.isClientSide()) {
			return;
		}

		LivingEntity entity = (LivingEntity) (Object) this;

		if (entity instanceof Player player) {
			unfaircraft$hungerBeforeEat = player.getFoodData().getFoodLevel();
		}
	}

	@Inject(
			method = "eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/food/FoodProperties;)Lnet/minecraft/world/item/ItemStack;",
			at = @At("RETURN")
	)
	private void onEat(Level level, ItemStack stack, FoodProperties foodProperties, CallbackInfoReturnable<ItemStack> cir) {
		if (!unfaircraft$enabled) {
			return;
		}

		if (level.isClientSide()) {
			return;
		}

		Item item = stack.getItem();

		boolean isCooked =
				item == Items.COOKED_BEEF       ||
						item == Items.COOKED_CHICKEN    ||
						item == Items.COOKED_COD        ||
						item == Items.COOKED_MUTTON     ||
						item == Items.COOKED_RABBIT     ||
						item == Items.COOKED_PORKCHOP   ||
						item == Items.COOKED_SALMON     ||
						item == Items.BAKED_POTATO      ||
						item == Items.BREAD             ||
						item == Items.CAKE              ||
						item == Items.COOKIE            ||
						item == Items.PUMPKIN_PIE;

		if (!isCooked) {
			return;
		}

		LivingEntity entity = (LivingEntity) (Object) this;
		if (entity instanceof Player player) {
			if (entity.level().random.nextFloat() < UnfairCraftConfig.FOOD.debuffChance.get().floatValue()) {
				boolean givePoison = entity.level().random.nextBoolean();
				if (givePoison) {
					entity.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0));
				} else {
					entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 200, 1));
				}
			}

			if (unfaircraft$hungerBeforeEat < 0) return;

			int nutrition = foodProperties.nutrition();
			int hungerBefore = unfaircraft$hungerBeforeEat;
			int maxHunger = 20;
			int overflow = Math.max(0, (hungerBefore + nutrition) - maxHunger);
			if (overflow > 0) {
				int amplifier = Math.min(2, (overflow - 1) / 2);
				entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200 + overflow * 20, amplifier));
			}
			unfaircraft$hungerBeforeEat = -1;
		}
	}
}
