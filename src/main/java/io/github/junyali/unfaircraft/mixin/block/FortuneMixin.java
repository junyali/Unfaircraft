package io.github.junyali.unfaircraft.mixin.block;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(Block.class)
public abstract class FortuneMixin {
	@Inject(
			method = "getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;",
			at = @At("RETURN"),
			cancellable = true
	)
	private static void unfaircraft$fortuneReversal(BlockState state, ServerLevel level, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack tool, CallbackInfoReturnable<List<ItemStack>> cir) {
		if (!UnfairCraftConfig.isEnabled(UnfairCraftConfig.BLOCK.enabled)) {
			return;
		}

		Registry<Enchantment> enchantmentRegistry = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
		Holder<Enchantment> fortune = enchantmentRegistry.getHolderOrThrow(Enchantments.SILK_TOUCH);

		ItemEnchantments enchantments = tool.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
		if (enchantments.getLevel(fortune) == 0) {
			return;
		}

		if (level.getRandom().nextFloat() < UnfairCraftConfig.BLOCK.fortuneReversalChance.get().floatValue()) {
			List<ItemStack> drops = cir.getReturnValue();
			List<ItemStack> reduced = new ArrayList<>();

			for (ItemStack drop: drops) {
				if (drop.isEmpty()) continue;
				int newCount = Math.max(1, (int) (drop.getCount() * (0.25f + level.getRandom().nextFloat() * 0.25f)));
				ItemStack reducedDrop = drop.copy();
				reducedDrop.setCount(newCount);
				reduced.add(reducedDrop);
			}

			cir.setReturnValue(reduced);
		}
	}
}
