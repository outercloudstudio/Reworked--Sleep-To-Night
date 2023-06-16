package com.outercloud.reworked_sleep_to_night.mixin;

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
}