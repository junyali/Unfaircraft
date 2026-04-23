package io.github.junyali.unfaircraft.mixin.combat;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class KnockbackMixin {
	@Unique
	private static final boolean unfaircraft$enabled = UnfairCraftConfig.isEnabled(UnfairCraftConfig.KNOCKBACK.enabled);

	@Inject(
			method = "knockback",
			at = @At("HEAD")
	)
	private void knockbackPlayer(double strength, double x, double z, CallbackInfo ci) {
		if (!unfaircraft$enabled) {
			return;
		}

		LivingEntity entity = (LivingEntity) (Object) this;

		if (entity.getLastHurtByMob() instanceof Player player) {
			if (entity.level().random.nextFloat() < UnfairCraftConfig.KNOCKBACK.chance.get().floatValue()) {
				ItemStack heldItem = player.getMainHandItem();
				int knockbackLevel = heldItem.getEnchantmentLevel(player.level().registryAccess()
						.registryOrThrow(Registries.ENCHANTMENT)
						.getHolderOrThrow(Enchantments.KNOCKBACK));

				double knockbackMultiplier = 1.0 + (knockbackLevel * 0.5);
				double modifiedStrength = strength * knockbackMultiplier * UnfairCraftConfig.KNOCKBACK.multiplier.get();

				player.knockback(modifiedStrength, -x, -z);
			}
		}
	}
}
