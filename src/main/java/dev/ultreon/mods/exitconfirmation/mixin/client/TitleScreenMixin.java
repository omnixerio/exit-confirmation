package dev.ultreon.mods.exitconfirmation.mixin.client;

import dev.ultreon.mods.exitconfirmation.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.menu.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    @Inject(method = "buttonClicked", at = @At("HEAD"), cancellable = true)
    public void exitConfirm$injectButtonClick(ButtonWidget button, CallbackInfo ci) {
        if (button.id == 4) {
            if (ExitConfirmation.getInstance().onWindowClose(WindowCloseEvent.Source.QUIT_BUTTON) == ActionResult.CANCEL) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    public void exitConfirm$injectEscapePrompt(char chr, int key, CallbackInfo ci) {
        if (key == Keyboard.KEY_ESCAPE && ExitConfirmation.CONFIG.closePrompt.get() && ExitConfirmation.CONFIG.quitOnEscInTitle.get()) {
            if (this.minecraft.screen == this) {
                this.minecraft.openScreen(new ConfirmExitScreen(this.minecraft.screen));
                ci.cancel();
            }
        }
    }
}
