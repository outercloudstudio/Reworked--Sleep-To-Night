package com.outercloud.reworked_sleep_to_night.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.SleepManager;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@org.spongepowered.asm.mixin.Mixin(SleepManager.class)
public abstract class SleepManagerMixin {
	@Accessor
	abstract int getSleeping();

	@Inject(at = @At("RETURN"), method = "canSkipNight", cancellable = true)
	private void canSkipNight(int percentage, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(false);
	}

	@Inject(at = @At("HEAD"), method = "canResetTime", cancellable = true)
	private void canResetTime(int percentage, List<ServerPlayerEntity> players, CallbackInfoReturnable<Boolean> cir) {
		SleepManager instance = ((SleepManager)(Object)this);

		int sleepingRequirement = instance.getNightSkippingRequirement(percentage);

		if(getSleeping() < sleepingRequirement) {
			cir.setReturnValue(false);

			return;
		}

		int i = (int)players.stream().filter(PlayerEntity::canResetTimeBySleeping).count();

		cir.setReturnValue(i >= sleepingRequirement);
	}
}