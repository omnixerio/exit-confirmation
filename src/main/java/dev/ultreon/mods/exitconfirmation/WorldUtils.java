package dev.ultreon.mods.exitconfirmation;

import dev.ultreon.mods.exitconfirmation.provider.AppletJFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.menu.TitleScreen;

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
            ExitConfirmation.minecraft.stop();
            ExitConfirmation.minecraft.shutdown();

            AppletJFrame.getInstance().getApplet().stop();
            AppletJFrame.getInstance().getApplet().destroy();
            AppletJFrame.getInstance().dispose();

            Runtime.getRuntime().halt(0);
        });
    }
}
