package ui;
import Player.Player;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;

public class Menu extends UI_Helper {

    public static void start(Player p) throws Exception {

        p.clearWindow();

        if (p.getRank() == 0) { // fresh start or selecting 'New Game' will omit 'Continue' option
            new ActionListDialogBuilder()
                    .setTitle("MAIN MENU")
                    .setDescription("\n")
                    .addAction("New game", () -> {
                        try { Terminal.start(p); } catch (Exception e) { e.printStackTrace(); } })
                    .addAction("How to play", () -> message(p.getGUI(), "HOW TO PLAY", HOW2PLAY))
                    .addAction("About", () -> message(p.getGUI(), "ABOUT", ABOUT))
                    .addAction("Disclaimer", () -> {
                        p.clearWindow();
                        message(p.getGUI(), "DISCLAIMER", DISCLAIMER);
                    })
                    .setCanCancel(false)
                    .build()
                    .showDialog(p.getGUI());
        }
        else {
            new ActionListDialogBuilder()
                    .setTitle("MAIN MENU")
                    .setDescription("\n")
                    .addAction("Continue", () -> {
                    })
                    .addAction("New game", () -> {
                        try {
                            Terminal.start(p);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    })
                    .addAction("Store", () -> {
                        try {
                            Store.start(p);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    })
                    .addAction("Achievements", () -> {
                        try {
                            Achievements.start(p);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    })
                    .addAction("How to play", () -> message(p.getGUI(), "HOW TO PLAY", HOW2PLAY))
                    .addAction("About", () -> message(p.getGUI(), "ABOUT", ABOUT))
                    .addAction("Disclaimer", () -> {
                        p.clearWindow();
                        message(p.getGUI(), "DISCLAIMER", DISCLAIMER);

                    })
                    .setCanCancel(false)
                    .build()
                    .showDialog(p.getGUI());
        }
        if (msg) {
            msg = false;
            start(p);
        }
    }
}
