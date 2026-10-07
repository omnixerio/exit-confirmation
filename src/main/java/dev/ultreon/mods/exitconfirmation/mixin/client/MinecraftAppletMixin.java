package dev.ultreon.mods.exitconfirmation.mixin.client;

import net.fabricmc.loader.impl.game.minecraft.applet.AppletFrame;
import net.fabricmc.loader.impl.game.minecraft.applet.AppletLauncher;
import net.minecraft.client.MinecraftApplet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.applet.Applet;
import java.awt.*;

@Mixin(MinecraftApplet.class)
public abstract class MinecraftAppletMixin extends Applet {

	@Shadow
	public abstract void stop();

	@Inject(method = "init", at = @At("HEAD"), cancellable = true, order = 100)
	private void exampleMod$onInit(CallbackInfo ci) {
		AppletLauncher launcher = (AppletLauncher) getParent();
		Container launcherParent = launcher.getParent();
		if (launcherParent instanceof AppletFrame) {
			AppletFrame frame = (AppletFrame) launcherParent;
			frame.setVisible(false);

			ci.cancel();
		}
	}
}
