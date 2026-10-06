package dev.ultreon.mods.exitconfirmation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.menu.TitleScreen;

public final class WorldUtils {
    public static void saveWorldThenOpenTitle() {
        Minecraft mc = ExitConfirmation.minecraft;
        saveWorldThen(() -> {
            mc.openScreen(new TitleScreen());
        });
    }

    public static void saveWorldThen(Runnable runnable) {
        Minecraft mc = ExitConfirmation.minecraft;
        mc.world.disconnect();
        mc.world.forceSave(mc.progressRenderer);
        runnable.run();
    }

    public static void saveWorldThenQuitGame() {
        saveWorldThen(() -> {
            ExitConfirmation.minecraft.stop();
            ExitConfirmation.minecraft.shutdown();
            AppletJFrame.getInstance().dispose();
        });
    }
}
