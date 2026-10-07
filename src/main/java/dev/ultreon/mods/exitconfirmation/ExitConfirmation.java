package dev.ultreon.mods.exitconfirmation;

import dev.ultreon.mods.exitconfirmation.config.Config;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.WindowListener;
import java.util.logging.LogManager;

public class ExitConfirmation implements ClientModInitializer {
    public static Minecraft minecraft;

    public static final String MOD_ID = "exit_confirm";
    public static boolean allowExit;
    public static final Config CONFIG = new Config();

    // Directly reference a log4j logger.
    @SuppressWarnings("unused")
    static final Logger LOGGER = LoggerFactory.getLogger("ExitConfirmation");
    public static int open = -1;
    public static WindowListener[] listeners;
    private static ExitConfirmation instance;

    public static ExitConfirmation getInstance() {
        return ExitConfirmation.instance;
    }

    public static void tick() {
        if (open == 0) {
            minecraft.unlockMouse();
            minecraft.openScreen(new ConfirmExitScreen(minecraft.screen));
            open--;
        } else if (open >= 1) {
            open--;
        }
    }

    @Override
    public void onInitializeClient() {
        ExitConfirmation.instance = this;

        Config.load();
        Config.save();
    }

}
