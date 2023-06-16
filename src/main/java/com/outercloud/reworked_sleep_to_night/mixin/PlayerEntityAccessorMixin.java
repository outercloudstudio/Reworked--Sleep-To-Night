package com.outercloud.reworked_sleep_to_night.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@org.spongepowered.asm.mixin.Mixin(PlayerEntity.class)
public interface PlayerEntityAccessorMixin {
	@Accessor("sleepTimer")
	void setSleepTimer(int sleepTimer);
}