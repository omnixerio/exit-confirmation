package dev.ultreon.mods.exitconfirmation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.menu.TitleScreen;

import java.awt.event.WindowListener;

public final class WorldUtils {
    public static void saveWorldThenOpenTitle() {
        Minecraft mc = ExitConfirmation.minecraft;
        mc.world.disconnect();
        mc.world.forceSave(mc.progressRenderer);
        mc.openScreen(new TitleScreen());
    }

    public static void saveWorldThen(Runnable runnable) {
        Minecraft mc = ExitConfirmation.minecraft;
        mc.world.disconnect();
        mc.world.forceSave(mc.progressRenderer);
        mc.openScreen(new TitleScreen());
        runnable.run();
    }

    public static void saveWorldThenOpen(Screen screen) {
        Minecraft mc = ExitConfirmation.minecraft;
        mc.world.disconnect();
        mc.world.forceSave(mc.progressRenderer);
        mc.openScreen(screen);
    }

    public static void saveWorldThenQuitGame() {
        saveWorldThen(() -> {
            ExitConfirmation.minecraft.shutdown();


        });
    }
}
