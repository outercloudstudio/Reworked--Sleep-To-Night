package com.outercloud.reworked_sleep_to_night.mixin.compat.travelersbackpack;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(com.tiviacz.travelersbackpack.blocks.SleepingBagBlock.class)
public class SleepingBagMixin {
    @ModifyExpressionValue(method = "trySleep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isDay()Z", ordinal = 0))
    boolean isDay(boolean isDay) {
        return false;
    }
}
