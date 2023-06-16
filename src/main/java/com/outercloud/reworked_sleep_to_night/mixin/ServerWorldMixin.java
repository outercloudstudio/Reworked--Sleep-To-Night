package com.outercloud.reworked_sleep_to_night.mixin;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.world.SleepManager;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@org.spongepowered.asm.mixin.Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
	@Invoker("wakeSleepingPlayers")
	abstract void InvokeWakeSleepingPlayers();

	@Accessor
	abstract SleepManager getSleepManager();

	@Inject(at = @At("HEAD"), method = "tick")
	private void canResetTime(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
		ServerWorld instance = ((ServerWorld)(Object)this);

		if(!getSleepManager().canResetTime(50, instance.getPlayers())) return;

		instance.setTimeOfDay(instance.getTimeOfDay() + 12000L);

		InvokeWakeSleepingPlayers();
	}
}