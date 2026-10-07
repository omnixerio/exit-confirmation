package dev.ultreon.mods.exitconfirmation;

import dev.ultreon.mods.exitconfirmation.config.Config;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ExitConfirmation implements ClientModInitializer {

    public static final String MOD_ID = "exit_confirm";
    public static boolean allowExit;
    public static final Config CONFIG = new Config();

    // Directly reference a log4j logger.
    @SuppressWarnings("unused")
    static final Logger LOGGER = LogManager.getLogManager().getLogger("ExitConfirmation");
    public static Minecraft minecraft;
    private static ExitConfirmation instance;
    public static int open = -1;

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

    public ActionResult onWindowClose(WindowCloseEvent.Source source) {
        Minecraft minecraft = ExitConfirmation.minecraft;

        // Check close source.
        if (source == WindowCloseEvent.Source.GENERIC) {
            // Always cancel if the world isn't loaded but also being ingame. (Fixes bug)
            if (minecraft.world == null && minecraft.screen == null) {
                return ActionResult.CANCEL;
            }

            // Otherwise only cancel when the close prompt is enabled.
            if (ExitConfirmation.CONFIG.closePrompt.get()) {
                // Allow closing ingame if enabled in config.
                if (minecraft.world != null && !ExitConfirmation.CONFIG.closePromptInGame.get()) {
                    return ActionResult.PASS;
                }

                // Only show screen, when the screen isn't the confirmation screen yet.
                if (!(minecraft.screen instanceof ConfirmExitScreen)) {
                    // Set the screen.
                    minecraft.openScreen(new ConfirmExitScreen(minecraft.screen));
                }

                // Cancel the event.
                return ActionResult.CANCEL;
            }
        } else if (source == WindowCloseEvent.Source.QUIT_BUTTON) {
            // Cancel quit button when set in config, and the screen isn't currently the confirmation screen already.
            if (ExitConfirmation.CONFIG.closePrompt.get() && ExitConfirmation.CONFIG.closePromptQuitButton.get() && !(minecraft.screen instanceof ConfirmExitScreen)) {
                minecraft.openScreen(new ConfirmExitScreen(minecraft.screen));
                return ActionResult.CANCEL;
            }
        }
        return ActionResult.PASS;
    }
}
