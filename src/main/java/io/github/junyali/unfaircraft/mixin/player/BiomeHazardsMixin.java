package io.github.junyali.unfaircraft.mixin.player;

import io.github.junyali.unfaircraft.UnfairCraft;
import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class BiomeHazardsMixin {
	@Unique
	private static final int unfaircraft$biome_check_interval = 100;

	@Inject(
			method = "tick",
			at = @At("TAIL")
	)
	private void unfaircraft$biomeHazards(CallbackInfo ci) {
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.player.enabled() && UnfairCraft.CONFIG.player.enableBiomeHazards())) {
			return;
		}

		Player self = (Player) (Object) this;

		if (self.level().isClientSide()) {
			return;
		}

		if (self.level().getGameTime() % unfaircraft$biome_check_interval != 0) {
			return;
		}

		Holder<Biome> biomeHolder = self.level().getBiome(self.blockPosition());

		if (biomeHolder.is(Biomes.DESERT) || biomeHolder.is(Biomes.BADLANDS)) {
			self.addEffect(new MobEffectInstance(MobEffects.HUNGER, 120, 1, false, true));
		}

		if (biomeHolder.is(Biomes.SNOWY_PLAINS) || biomeHolder.is(Biomes.SNOWY_BEACH) || biomeHolder.is(Biomes.SNOWY_SLOPES) || biomeHolder.is(Biomes.SNOWY_TAIGA)) {
			self.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 1, false, true));
		}

		if (biomeHolder.is(Biomes.JUNGLE) || biomeHolder.is(Biomes.BAMBOO_JUNGLE) || biomeHolder.is(Biomes.SPARSE_JUNGLE)) {
			self.addEffect(new MobEffectInstance(MobEffects.POISON, 10, 0, false, true));
		}
	}
}
