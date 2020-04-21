package ui;

import Player.Player;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;

import java.util.Collections;

class Achievements extends UI_Helper {

    static void start(Player p) throws Exception {

        BasicWindow window = new BasicWindow();
        Panel panel = new Panel(new GridLayout(1));
        TerminalSize ts = new TerminalSize(0,1);
        TerminalSize size = new TerminalSize(46, 10);
        CheckBoxList<String> checkBoxList = new CheckBoxList<>(size);
        checkBoxList.setEnabled(false);
        String pAchievements = p.getAchievements();
        for (int i = 0; i < ACHIEVEMENTS.size(); i++) {
            checkBoxList.addItem(ACHIEVEMENTS.get(i));
            if (i == 9) {
                if (pAchievements.substring(9).equals("1")) {
                    checkBoxList.setChecked(ACHIEVEMENTS.get(9), true);
                }
            } else if (pAchievements.substring(i,i+1).equals("1")) {
                checkBoxList.setChecked(ACHIEVEMENTS.get(i), true);
            }
        }

        window.setHints(Collections.singletonList(Window.Hint.CENTERED));
        window.setTitle("ACHIEVEMENTS");

        panel.addComponent(new EmptySpace(TextColor.ANSI.DEFAULT, ts));
        panel.addComponent(checkBoxList);
        panel.addComponent(new Button("OK", () -> {
            try { Menu.start(p); }
            catch (Exception e) { e.printStackTrace(); }
        }).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));

        window.setComponent(panel);
        p.getGUI().addWindowAndWait(window);
    }
}
