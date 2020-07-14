package ui;
import Player.Player;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;

public class Menu extends UI_Helper {

    public static void start(Player p) throws Exception {
        if (p.getGUI().getWindows().size() > 0) p.getGUI().getActiveWindow().close();
        System.out.println(p.getGUI().getWindows().size());
        p.clearWindow();
        new ActionListDialogBuilder()
                .setTitle("MAIN MENU")
                .setDescription("\n")
                .addAction("Terminal", () -> {
                    try {
                        p.writeProgress(0,0.0f,p.getPurchaced(),p.getColour(),p.getAchievements());
                        Terminal.start(p); } catch (Exception e) { System.out.println(e); } })
                .addAction("Store", () -> {
                    try {
                        Store.start(p,0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .addAction("Password Strength", () -> {
                    try {
                        Password.start(p);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .addAction("How to play", () -> message(p.getGUI(), "HOW TO PLAY", HOW2PLAY))
                .addAction("Achievements", () -> {
                    try {
                        Achievements.start(p);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .addAction("About", () -> message(p.getGUI(), "ABOUT", ABOUT))
                .setCanCancel(false)
                .build()
                .showDialog(p.getGUI());

        if (msg) {
            msg = false;
            start(p);
        }
    }
}
