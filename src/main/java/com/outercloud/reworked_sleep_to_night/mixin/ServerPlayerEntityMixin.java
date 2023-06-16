package com.outercloud.reworked_sleep_to_night.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@org.spongepowered.asm.mixin.Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
	public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
		super(world, pos, yaw, gameProfile);
	}

	@Inject(at = @At("RETURN"), method = "trySleep", cancellable = true)
	private void trySleep(BlockPos pos, CallbackInfoReturnable<Either<PlayerEntity.SleepFailureReason, Unit>> cir) {
		Either<PlayerEntity.SleepFailureReason, Unit> value = cir.getReturnValue();

		value.ifLeft(reason -> {
			if(reason != PlayerEntity.SleepFailureReason.NOT_POSSIBLE_NOW) return;

			ServerPlayerEntity instance = ((ServerPlayerEntity)(Object)this);

			this.incrementStat(Stats.SLEEP_IN_BED);
			Criteria.SLEPT_IN_BED.trigger(instance);

			instance.sleep(pos);
			((PlayerEntityAccessorMixin)this).setSleepTimer(0);

			((ServerWorld)instance.getWorld()).updateSleepingPlayers();

			cir.setReturnValue(Either.right(Unit.INSTANCE));
		});
	}
}