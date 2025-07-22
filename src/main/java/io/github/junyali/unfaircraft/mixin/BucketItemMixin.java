package io.github.junyali.unfaircraft.mixin;

import io.github.junyali.unfaircraft.config.UnfairCraftConfig;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public class BucketItemMixin {
	@Inject(
			method = "use",
			at = @At("HEAD"),
			cancellable = true
	)
	private void failWaterBucket(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
		if (!UnfairCraftConfig.ENABLE_UNFAIR_MODE.get() || !UnfairCraftConfig.ENABLE_BUCKET_ITEM_MIXIN.get()) {
			return;
		}

		ItemStack stack = player.getItemInHand(hand);

		if (stack.getItem() == Items.WATER_BUCKET) {
			if (level.random.nextFloat() < UnfairCraftConfig.WATER_BUCKET_FAIL_CHANCE.get().floatValue()) {
				if (!player.getAbilities().instabuild) {
					player.setItemInHand(hand, new ItemStack(Items.BUCKET));
				}
				level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BUCKET_FILL,  SoundSource.BLOCKS, 1.0f, 1.0f);
				cir.setReturnValue(InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide()));
			}
		}
	}
}
