package dev.ultreon.mods.exitconfirmation;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ModWindowListener implements WindowListener {
    @Override
    public void windowOpened(WindowEvent e) {
        for (WindowListener listener : ExitConfirmation.listeners) {
            listener.windowOpened(e);
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        ExitConfirmation.LOGGER.info("Prevent closing");
        ExitConfirmation.open = 2;
    }

    @Override
    public void windowClosed(WindowEvent e) {
        for (WindowListener listener : ExitConfirmation.listeners) {
            listener.windowClosed(e);
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {
        for (WindowListener listener : ExitConfirmation.listeners) {
            listener.windowIconified(e);
        }
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        for (WindowListener listener : ExitConfirmation.listeners) {
            listener.windowDeiconified(e);
        }
    }

    @Override
    public void windowActivated(WindowEvent e) {
        for (WindowListener listener : ExitConfirmation.listeners) {
            listener.windowActivated(e);
        }
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        for (WindowListener listener : ExitConfirmation.listeners) {
            listener.windowDeactivated(e);
        }
    }
}
