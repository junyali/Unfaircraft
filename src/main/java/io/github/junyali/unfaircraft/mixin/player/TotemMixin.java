package io.github.junyali.unfaircraft.mixin.player;

import io.github.junyali.unfaircraft.UnfairCraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class TotemMixin {
	@Inject(
			method = "checkTotemDeathProtection",
			at = @At("HEAD"),
			cancellable = true
	)
	private void makeTotemFail(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.totem.enabled())) {
			return;
		}

		LivingEntity entity = (LivingEntity) (Object) this;
		if (!(entity instanceof Player player)) {
			return;
		}

		ItemStack totemStack = null;
		for (InteractionHand hand : InteractionHand.values()) {
			ItemStack stack = player.getItemInHand(hand);
			if (stack.is(Items.TOTEM_OF_UNDYING)) {
				totemStack = stack;
				break;
			}
		}

		if (totemStack != null && entity.level().random.nextFloat() < UnfairCraft.CONFIG.totem.failChance()) {
			totemStack.shrink(1);
			entity.level().playSound(null, entity.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1.0F, 0.5F + entity.level().random.nextFloat() * 0.2F);
			cir.setReturnValue(false);
		}
	}
}
