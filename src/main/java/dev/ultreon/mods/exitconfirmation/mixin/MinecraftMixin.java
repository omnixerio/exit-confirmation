package dev.ultreon.mods.exitconfirmation.mixin;

import dev.ultreon.mods.exitconfirmation.AppletJFrame;
import dev.ultreon.mods.exitconfirmation.ExitConfirmation;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.game.minecraft.applet.AppletFrame;
import net.fabricmc.loader.impl.game.minecraft.applet.AppletLauncher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MinecraftApplet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	@Inject(method = "<init>", at = @At("CTOR_HEAD"))
	private void exampleMod$onInit(Component component, Canvas canvas, MinecraftApplet applet, int width, int height, boolean fullscreen, CallbackInfo ci) {
		ExitConfirmation.minecraft = (Minecraft) (Object) this;
	}

	@Inject(method = "tick", at = @At("HEAD"))
	private void exampleMod$onTick(CallbackInfo ci) {
		ExitConfirmation.tick();
	}
}
