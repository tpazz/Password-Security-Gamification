package ui;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Menu extends Helper {

    public Menu(WindowBasedTextGUI gui, Terminal ter) {
        this.gui = gui;
        this.ter = ter;
    }

    public void start(WindowBasedTextGUI gui) throws Exception {
        if (getLevel().equals("0")) { // fresh start or selecting 'New Game' will omit 'Continue' option
            new ActionListDialogBuilder()
                    .setTitle("MENU")
                    .setDescription("\n")
                    .addAction("New game", () -> {

                        Profile p = new Profile();
                        try {
                            p.start();
                            gui.getScreen().close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    })
                    .addAction("How to play", () -> message(gui, "HOW TO PLAY", HOW2PLAY))
                    .addAction("About", () -> message(gui, "ABOUT", ABOUT))
                    .addAction("Disclaimer", () -> message(gui, "DISCLAIMER", DISCLAIMER))
                    .setCanCancel(false)
                    .build()
                    .showDialog(gui);
        }
        else {
            new ActionListDialogBuilder()
                    .setTitle("Main Menu")
                    .setDescription("\n")
                    .addAction("Continue", () -> {})
                    .addAction("New game", () -> {})
                    .addAction("How to play", () -> message(gui, "HOW TO PLAY", HOW2PLAY))
                    .addAction("About", () -> message(gui, "ABOUT", ABOUT))
                    .addAction("Disclaimer", () -> message(gui, "DISCLAIMER", DISCLAIMER))
                    .setCanCancel(false)
                    .build()
                    .showDialog(gui);
        }
        if (msg) {
            msg = false;
            start(gui);
        }
    }
}
