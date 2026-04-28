package io.github.junyali.unfaircraft.mixin.entity;

import io.github.junyali.unfaircraft.UnfairCraft;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager {
	@Shadow
	public abstract int getPlayerReputation(Player player);

	@Unique
	private boolean unfaircraft$goalsRegistered = false;

	public VillagerMixin(EntityType<? extends AbstractVillager> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(
			method = "createAttributes",
			at = @At("RETURN")
	)
	private static void unfaircraft$addAttackAttribute(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
		cir.getReturnValue().add(Attributes.ATTACK_DAMAGE, 2.0f);
	}

	@Inject(
			method = "tick",
			at = @At("HEAD")
	)
	private void unfaircraft$hostileVillager(CallbackInfo ci) {
		if (!(UnfairCraft.CONFIG.enableUnfairMode() && UnfairCraft.CONFIG.merchantOffer.villagerRetaliation())) {
			return;
		}

		if (!unfaircraft$goalsRegistered) {
			this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
			this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false,
			entity -> {
				if (!(entity instanceof Player player)) {
					return false;
				}
				return this.getPlayerReputation(player) <= -15;
			}));
			this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
			unfaircraft$goalsRegistered = true;
		}

		if (this.getTarget() == null) {
			Player nearestPlayer = this.level().getNearestPlayer(this, 16.0D);
			if (nearestPlayer != null && this.getPlayerReputation(nearestPlayer) <= -15) {
				this.setTarget(nearestPlayer);
			}
		}
	}
}
