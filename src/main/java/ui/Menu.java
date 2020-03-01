package ui;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import com.googlecode.lanterna.terminal.Terminal;

public class Menu extends UI {

    public static void start(WindowBasedTextGUI gui, Terminal terminal) throws Exception {

        gui.removeWindow(gui.getActiveWindow());

        if (getLevel() == 0) { // fresh start or selecting 'New Game' will omit 'Continue' option
            new ActionListDialogBuilder()
                    .setTitle("MAIN MENU")
                    .setDescription("\n")
                    .addAction("New game", () -> {
                        try {
                            Profile.start(gui, terminal);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    })
                    .addAction("How to play", () -> message(gui, "HOW TO PLAY", HOW2PLAY))
                    .addAction("About", () -> message(gui, "ABOUT", ABOUT))
                    .addAction("Disclaimer", () -> {
                        gui.removeWindow(gui.getActiveWindow());
                        message(gui, "DISCLAIMER", DISCLAIMER);

                    })
                    .setCanCancel(false)
                    .build()
                    .showDialog(gui);
        }
        else {
            new ActionListDialogBuilder()
                    .setTitle("MAIN MENU")
                    .setDescription("\n")
                    .addAction("Continue", () -> {
                    })
                    .addAction("New game", () -> {
                        try {
                            Profile.start(gui, terminal);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    })
                    .addAction("Store", () -> {
                        try {
                            Store.start(gui, terminal);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    })
                    .addAction("Achievements", () -> {
                        try {
                            Achievements.start(gui, terminal);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    })
                    .addAction("How to play", () -> message(gui, "HOW TO PLAY", HOW2PLAY))
                    .addAction("About", () -> message(gui, "ABOUT", ABOUT))
                    .addAction("Disclaimer", () -> {
                        gui.removeWindow(gui.getActiveWindow());
                        message(gui, "DISCLAIMER", DISCLAIMER);

                    })
                    .setCanCancel(false)
                    .build()
                    .showDialog(gui);
        }
        if (msg) {
            msg = false;
            start(gui, terminal);
        }
    }
}
