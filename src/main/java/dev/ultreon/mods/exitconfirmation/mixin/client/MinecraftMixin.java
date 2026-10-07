package dev.ultreon.mods.exitconfirmation.mixin.client;

import dev.ultreon.mods.exitconfirmation.ExitConfirmation;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	@Inject(method = "init", at = @At("HEAD"))
	private void exampleMod$onInit(CallbackInfo ci) {
		ExitConfirmation.minecraft = (Minecraft) (Object) this;
	}

	@Inject(method = "tick", at = @At("HEAD"))
	private void exampleMod$onTick(CallbackInfo ci) {
		ExitConfirmation.tick();
	}
}
