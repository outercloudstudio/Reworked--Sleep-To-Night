package com.outercloud.reworked.mixin;

import com.mojang.datafixers.util.Either;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@org.spongepowered.asm.mixin.Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
	@Inject(at = @At("RETURN"), method = "trySleep", cancellable = true)
	private void trySleep(BlockPos pos, CallbackInfoReturnable<Either<PlayerEntity.SleepFailureReason, Unit>> cir) {
//		Either<PlayerEntity.SleepFailureReason, Unit> value = cir.getReturnValue();

		ServerPlayerEntity instance = ((ServerPlayerEntity)(Object)this);

		instance.sleep(pos);
//		instance.sleepTimer = 0;

		((ServerWorld)instance.getWorld()).updateSleepingPlayers();

		cir.setReturnValue(Either.right(Unit.INSTANCE));

//		value.ifLeft(reason -> {
//			if(reason == PlayerEntity.SleepFailureReason.NOT_POSSIBLE_NOW) return;
//
//			cir.setReturnValue(Either.right(Unit.INSTANCE));
//		});
	}
}