package io.github.junyali.unfaircraft.mixin.item;

import io.github.junyali.unfaircraft.UnfairCraft;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(PotionItem.class)
public class PotionItemMixin {
	@Unique
	private static Map<MobEffect, Holder<MobEffect>> POSITIVE_TO_NEGATIVE_EFFECTS;

	@Unique
	private PotionContents unfaircraft$savedPotionContents;

	@Unique
	private static Map<MobEffect, Holder<MobEffect>> unfaircraft$getEffectMap() {
		if (POSITIVE_TO_NEGATIVE_EFFECTS == null) {
			POSITIVE_TO_NEGATIVE_EFFECTS = new HashMap<>();
			POSITIVE_TO_NEGATIVE_EFFECTS.put(MobEffects.MOVEMENT_SPEED.value(), MobEffects.MOVEMENT_SLOWDOWN);
			POSITIVE_TO_NEGATIVE_EFFECTS.put(MobEffects.DAMAGE_BOOST.value(), MobEffects.WEAKNESS);
			POSITIVE_TO_NEGATIVE_EFFECTS.put(MobEffects.DAMAGE_RESISTANCE.value(), MobEffects.WITHER);
			POSITIVE_TO_NEGATIVE_EFFECTS.put(MobEffects.HEAL.value(), MobEffects.HARM);
			POSITIVE_TO_NEGATIVE_EFFECTS.put(MobEffects.REGENERATION.value(), MobEffects.POISON);
			POSITIVE_TO_NEGATIVE_EFFECTS.put(MobEffects.HEALTH_BOOST.value(), MobEffects.WITHER);
			POSITIVE_TO_NEGATIVE_EFFECTS.put(MobEffects.JUMP.value(), MobEffects.LEVITATION);
			POSITIVE_TO_NEGATIVE_EFFECTS.put(MobEffects.NIGHT_VISION.value(), MobEffects.BLINDNESS);
			POSITIVE_TO_NEGATIVE_EFFECTS.put(MobEffects.DIG_SPEED.value(), MobEffects.DIG_SLOWDOWN);
			POSITIVE_TO_NEGATIVE_EFFECTS.put(MobEffects.SLOW_FALLING.value(), MobEffects.LEVITATION);
			POSITIVE_TO_NEGATIVE_EFFECTS.put(MobEffects.ABSORPTION.value(), MobEffects.WITHER);
			POSITIVE_TO_NEGATIVE_EFFECTS.put(MobEffects.SATURATION.value(), MobEffects.HUNGER);
			POSITIVE_TO_NEGATIVE_EFFECTS.put(MobEffects.LUCK.value(), MobEffects.UNLUCK);
		}
		return POSITIVE_TO_NEGATIVE_EFFECTS;
	}

	@Unique
	private static void unfaircraft$registerOppositeEffect(MobEffect positive, Holder<MobEffect> negative) {
		POSITIVE_TO_NEGATIVE_EFFECTS.put(positive, negative);
	}

	@Inject(
			method = "finishUsingItem",
			at = @At("HEAD")
	)
	private void onPotionDrinkStart(ItemStack stack, Level level, LivingEntity entity, CallbackInfoReturnable<ItemStack> cir) {
		unfaircraft$savedPotionContents = stack.get(DataComponents.POTION_CONTENTS);
	}

	@Inject(
			method = "finishUsingItem",
			at = @At("RETURN")
	)
	private void onPotionDrink(ItemStack stack, Level level, LivingEntity entity, CallbackInfoReturnable<ItemStack> cir) {
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.potion.enabled())) {
			return;
		}

		if (!(entity instanceof Player player) || (level.isClientSide)) {
			return;
		}

		if (level.random.nextFloat() < UnfairCraft.CONFIG.potion.backfireChance()) {
			PotionContents potionContents = unfaircraft$savedPotionContents;
			if (potionContents == null) {
				return;
			}

			boolean backfired = false;

			for (MobEffectInstance effect : potionContents.getAllEffects()) {
				Holder<MobEffect> negativeEffect = unfaircraft$getEffectMap().get(effect.getEffect().value());

				if (negativeEffect != null) {
					// idk if there's a more effective way of doing this lol
					player.removeEffect(effect.getEffect());

					player.addEffect(new MobEffectInstance(
							negativeEffect,
							effect.getDuration(),
							effect.getAmplifier(),
							effect.isAmbient(),
							effect.isVisible(),
							effect.showIcon()
					));

					backfired = true;
				}
			}

			if (backfired) {
				level.playSound(null, player.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1.0F, 0.5F + level.random.nextFloat() * 0.2F);
			}
		}
	}
}
