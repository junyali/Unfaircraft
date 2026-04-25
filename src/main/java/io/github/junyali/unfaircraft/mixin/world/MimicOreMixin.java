package io.github.junyali.unfaircraft.mixin.world;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(Block.class)
public abstract class MimicOreMixin {
	@Unique
	private static Set<Block> unfaircraft$ores;

	@Unique
	private static Set<Block> unfaircraft$getOres() {
		if (unfaircraft$ores == null) {
			unfaircraft$ores = Set.of(
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
		return unfaircraft$ores;
	}

	@Inject(
			method = "playerDestroy",
			at = @At("TAIL")
	)
	private void unfaircraft$oreMimic(Level level, Player player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack tool, CallbackInfo ci) {
		if (level.isClientSide()) {
			return;
		}

		if (!unfaircraft$getOres().contains(state.getBlock())) {
			return;
		}

		if (!(level instanceof ServerLevel serverLevel)) {
			return;
		}

		if (level.random.nextFloat() < UnfairCraftConfig.ORE.mimicSpawnChance.get().floatValue()) {
			Entity mob = EntityType.SILVERFISH.create(serverLevel);
			if (mob == null) {
				return;
			}
			mob.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.0f, 0.0f);
			serverLevel.addFreshEntity(mob);
		}
	}
}
