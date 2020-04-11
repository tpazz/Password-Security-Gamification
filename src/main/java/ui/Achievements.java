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
        TerminalSize size = new TerminalSize(45, 8);
        CheckBoxList<String> checkBoxList = new CheckBoxList<>(size);

        checkBoxList.setEnabled(false);
        checkBoxList.addItem("Crack your first password");
        checkBoxList.addItem("Complete all brute-force levels");
        checkBoxList.addItem("Complete all dictionary attack levels");
        checkBoxList.addItem("Buy all items available from the store");
        checkBoxList.addItem("Reach level 25");
        checkBoxList.addItem("Acquire \u20BF420");
        checkBoxList.addItem("Complete all achievements");

        if (p.getRank() > 1) checkBoxList.setChecked("Crack your first password", true);
        if (p.getRank() > 9) checkBoxList.setChecked("Complete all brute-force levels", true);
        if (p.getRank() > 19) checkBoxList.setChecked("Complete all dictionary attack levels", true);
        if (p.getPurchaced().equals("1111111111")) checkBoxList.setChecked("Buy all items available from the store", true);
        if (p.getRank() > 24) checkBoxList.setChecked("Reach level 25", true);
        if (p.getBitcoin() > 420) checkBoxList.setChecked("Acquire \u20BF420", true);
        if (checkBoxList.isChecked(4) && checkBoxList.isChecked(5)) checkBoxList.setChecked("Complete all achievements", true);

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
