package dev.ultreon.mods.exitconfirmation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.util.ProgressListener;
import net.minecraft.world.World;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
@Environment(EnvType.CLIENT)
public class ConfirmExitScreen extends Screen {
    private final String description = "Are you sure you want to exit Minecraft?";
    private final String title = "Exit Confirmation";
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

        this.buttons.add(yesButton = new ExitButtonWidget(0, this.width / 2 - 105, this.height / 6 + 96, "Yes"));
        this.buttons.add(new ExitButtonWidget(1, this.width / 2 + 5, this.height / 6 + 96, "No"));

        yesButton.active = false;

        this.setButtonDelay(10);
    }

    @Override
    protected void buttonClicked(ButtonWidget button) {
        if (button.id == 0) {
            if (this.minecraft != null) {
                button.active = false;
                World world = this.minecraft.world;
                if (world != null) {
                    world.forceSave(minecraft.progressRenderer);
                }

                this.minecraft.stop();
                this.minecraft.shutdown();
                AppletJFrame.getInstance().dispose();
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
            ButtonWidget yesButton1 = yesButton;
            if (yesButton1 != null) {
                yesButton1.active = true;
            }
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
