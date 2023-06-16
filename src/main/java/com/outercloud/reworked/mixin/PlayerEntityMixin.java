package com.outercloud.reworked.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@org.spongepowered.asm.mixin.Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;wakeUp(ZZ)V", ordinal = 0), cancellable = true)
	private void preventWakeupBecauseDay(CallbackInfo ci) {
		ci.cancel();
	}

	@Inject(method = "wakeUp(ZZ)V", at = @At(value = "HEAD"))
	private void wakeUp(boolean skipSleepTimer, boolean updateSleepingPlayers, CallbackInfo ci) {
		try {
			throw new Error("stack trace");
		} catch(Error error) {
			error.printStackTrace();
		}
	}
}