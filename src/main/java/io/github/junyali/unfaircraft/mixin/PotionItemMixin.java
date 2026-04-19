package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
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
	private static final Map<MobEffect, MobEffect> POSITIVE_TO_NEGATIVE_EFFECTS = new HashMap<>();

	@Inject(
			method = "finishUsingItem",
			at = @At("RETURN")
	)
	private void onPotionDrink(ItemStack stack, Level level, LivingEntity entity, CallbackInfoReturnable<ItemStack> cir) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_POTION_ITEM_MIXIN.get()) {
			return;
		}

		if (!(entity instanceof Player player) || (level.isClientSide)) {
			return;
		}

		if (level.random.nextFloat() < UnfairCraftConfig.POTION_BACKFIRE_CHANCE.get()) {
			PotionContents potionContents = stack.get(DataComponents.POTION_CONTENTS);
			if (potionContents == null) {
				return;
			}

			boolean backfired = false;

			for (MobEffectInstance effect : potionContents.getAllEffects()) {
				MobEffect negativeEffect = POSITIVE_TO_NEGATIVE_EFFECTS.get(effect.getEffect());

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
