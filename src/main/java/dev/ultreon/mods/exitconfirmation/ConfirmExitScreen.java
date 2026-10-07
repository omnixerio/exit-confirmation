package dev.ultreon.mods.exitconfirmation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.impl.game.minecraft.applet.AppletLauncher;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.locale.Language;

import java.awt.event.WindowListener;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
@Environment(EnvType.CLIENT)
public class ConfirmExitScreen extends Screen {
    private final String description = Language.getInstance().translate("screen.exit_confirm.description");
    private final String title = Language.getInstance().translate("screen.exit_confirm.title");
    private Screen previousScreen;
    private int ticksUntilEnableIn;
    private ButtonWidget yesButton;

    public ConfirmExitScreen(Screen previousScreen) {
        super();
        this.previousScreen = previousScreen;
    }

    @Override
    public void init() {
        super.init();

        this.buttons.clear();

        this.buttons.add(yesButton = new ButtonWidget(0, this.width / 2 - 105, this.height / 6 + 96, 100, 20, Language.getInstance().translate("screen.exit_confirm.yes")));
        this.buttons.add(new ButtonWidget(1, this.width / 2 + 5, this.height / 6 + 96, 100, 20, Language.getInstance().translate("screen.exit_confirm.no")));

        yesButton.active = false;

        this.setButtonDelay(10);
    }

    @Override
    protected void buttonClicked(ButtonWidget button) {
        if (button.id == 0) {
            if (this.minecraft != null) {
                button.active = false;
                if (this.minecraft.world != null) {
                    WorldUtils.saveWorldThenQuitGame();
                    return;
                }

                for (WindowListener listener : ExitConfirmation.listeners) {
                    listener.windowClosing(null);
                }

                if (ExitConfirmation.minecraft != null) {
                    ExitConfirmation.minecraft.shutdown();
                }

                System.exit(0);
            }
        } else if (button.id == 1) {
            if (this.minecraft != null) {
                button.active = false;
                this.minecraft.openScreen(this.previousScreen);
            }
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();

        this.drawCenteredString(this.textRenderer, this.title, this.width / 2, 70, 0xffffff);
        this.drawCenteredString(this.textRenderer, this.description, this.width / 2, 90, 0xbfbfbf);

        super.render(mouseX, mouseY, partialTicks);

    }

    /**
     * Sets the number of ticks to wait before enabling the buttons.
     */
    public void setButtonDelay(int ticksUntilEnable) {
        this.ticksUntilEnableIn = ticksUntilEnable;
    }

    @Override
    public void tick() {
        if (this.ticksUntilEnableIn-- <= 0) {
            yesButton.active = true;
        }
    }

    public void back() {
        this.minecraft.openScreen(this.previousScreen);
    }

    @Override
    protected void keyPressed(char id, int code) {
        // Don't allow closing the GUI
    }
}
