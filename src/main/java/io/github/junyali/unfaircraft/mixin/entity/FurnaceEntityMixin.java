package io.github.junyali.unfaircraft.mixin.entity;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class FurnaceEntityMixin {
	@Shadow
	int cookingProgress;

	@Shadow
	int cookingTotalTime;

	@Unique
	private int unfaircraft$continuousCookTime = 0;

	@Inject(
			method = "serverTick",
			at = @At("TAIL")
	)
	private static void unfaircraft$furnaceMalfunction(Level level, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity, CallbackInfo ci) {
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.FURNACE.enabled)) {
			return;
		}

		if (level.isClientSide()) {
			return;
		}

		FurnaceEntityMixin self = (FurnaceEntityMixin) (Object) blockEntity;
		if (self == null) {
			return;
		}
		boolean isLit = state.getValue(AbstractFurnaceBlock.LIT);
		if (!isLit) {
			self.unfaircraft$continuousCookTime = 0;
			return;
		}

		self.unfaircraft$continuousCookTime++;

		if (level.getRandom().nextFloat() < UnfairCraftConfig.FURNACE.fuelTheftChance.get().floatValue()) {
			self.cookingProgress = Math.max(0, self.cookingProgress - 10);
		}

		if (self.cookingProgress >= self.cookingTotalTime) {
			if (level.getRandom().nextFloat() < UnfairCraftConfig.FURNACE.smeltTheftChance.get().floatValue()) {
				ItemStack outputSlot = blockEntity.getItem(2);
				if (!outputSlot.isEmpty()) {
					outputSlot.setCount(0);
					blockEntity.setItem(2, new ItemStack(Items.CHARCOAL));
				}
			}
		}

		int threshold = UnfairCraftConfig.FURNACE.explosionThreshold.get();
		if (self.unfaircraft$continuousCookTime >= threshold) {
			if (level.getRandom().nextFloat() < UnfairCraftConfig.FURNACE.explosionChance.get().floatValue()) {
				self.unfaircraft$continuousCookTime = 0;
				level.explode(
						null,
						pos.getX() + 0.5,
						pos.getY() + 0.5,
						pos.getZ() + 0.5,
						3.0f,
						Level.ExplosionInteraction.BLOCK
				);
			} else {
				if (level instanceof ServerLevel serverLevel) {
					int overThreshold = self.unfaircraft$continuousCookTime - threshold;
					int particleCount = Math.min(1 + (overThreshold / 20), 10);
					for (int i = 0; i < particleCount; i++) {
						double offsetX = (level.getRandom().nextDouble() - 0.5) * 0.5;
						double offsetZ = (level.getRandom().nextDouble() - 0.5) * 0.5;
						double velocityY = 0.05 + (level.getRandom().nextDouble() * 0.5);
						serverLevel.sendParticles(
								ParticleTypes.LARGE_SMOKE,
								pos.getX() + 0.5 + offsetX,
								pos.getY() + 1.0,
								pos.getZ() + 0.5 + offsetZ,
								1,
								0.0,
								velocityY,
								0.0,
								0.01
						);
					}
				}
			}
		}
	}
}
