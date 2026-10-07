package dev.ultreon.mods.exitconfirmation.mixin.client;

import dev.ultreon.mods.exitconfirmation.ExitConfirmation;
import dev.ultreon.mods.exitconfirmation.ModWindowListener;
import net.fabricmc.loader.impl.game.minecraft.applet.AppletFrame;
import net.fabricmc.loader.impl.game.minecraft.applet.AppletLauncher;
import net.minecraft.client.MinecraftApplet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.WindowListener;

@Mixin(MinecraftApplet.class)
public abstract class MinecraftAppletMixin extends Applet {
	@Inject(method = "init", at = @At("HEAD"), order = 100)
	private void exampleMod$onInit(CallbackInfo ci) {
		AppletLauncher launcher = (AppletLauncher) getParent();
		Container launcherParent = launcher.getParent();
		if (launcherParent instanceof AppletFrame) {
			AppletFrame frame = (AppletFrame) launcherParent;
			WindowListener[] windowListeners = frame.getWindowListeners();
			ExitConfirmation.listeners = windowListeners;
			for (WindowListener windowListener : windowListeners) {
				frame.removeWindowListener(windowListener);
			}

			frame.addWindowListener(new ModWindowListener());
		}
	}
}
