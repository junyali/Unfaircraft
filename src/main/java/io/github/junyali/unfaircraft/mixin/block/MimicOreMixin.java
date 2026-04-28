package io.github.junyali.unfaircraft.mixin.block;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(ServerPlayerGameMode.class)
public abstract class MimicOreMixin {
	@Shadow
	protected ServerLevel level;

	@Final
	@Shadow
	protected ServerPlayer player;

	@Shadow
	@Final
	private static Logger LOGGER;
	@Unique
	private BlockState unfaircraft$mimicSavedState;

	@Unique
	private static Set<Block> unfaircraft$mimicOres;

	@Unique
	private static Set<Block> unfaircraft$getMimicOres() {
		if (unfaircraft$mimicOres == null) {
			unfaircraft$mimicOres = Set.of(
					Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE,
					Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE,
					Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE,
					Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE,
					Blocks.REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE,
					Blocks.LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE,
					Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE,
					Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE,
					Blocks.NETHER_GOLD_ORE, Blocks.NETHER_QUARTZ_ORE,
					Blocks.ANCIENT_DEBRIS
			);
		}
		return unfaircraft$mimicOres;
	}

	@Inject(
			method = "destroyBlock",
			at = @At("HEAD")
	)
	private void unfaircraft$captureState(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		unfaircraft$mimicSavedState = level.getBlockState(pos);
	}

	@Inject(
			method = "destroyBlock",
			at = @At("RETURN")
	)
	private void unfaircraft$mimicOreSpawn(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValue()) {
			return;
		}

		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.BLOCK.enabled)) {
			return;
		}

		if (unfaircraft$mimicSavedState == null) {
			return;
		}

		if (!unfaircraft$getMimicOres().contains(unfaircraft$mimicSavedState.getBlock())) {
			return;
		}

		if (level.random.nextFloat() < UnfairCraftConfig.BLOCK.mimicSpawnChance.get().floatValue()) {
			Entity mob = EntityType.SILVERFISH.create(level);
			if (mob == null) {
				return;
			}
			mob.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.0f, 0.0f);
			level.addFreshEntity(mob);
		}
	}
}
