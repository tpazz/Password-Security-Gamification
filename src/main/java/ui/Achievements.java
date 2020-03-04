package ui;

import Player.Player;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;

import java.util.Collections;

class Achievements extends UI_Helper {

    static void start(Player p) {

        BasicWindow window = new BasicWindow();
        Panel panel = new Panel(new GridLayout(1));
        TerminalSize ts = new TerminalSize(0,1);
        TerminalSize size = new TerminalSize(30, 10);
        CheckBoxList<String> checkBoxList = new CheckBoxList<>(size);

        checkBoxList.addItem("item 1");
        checkBoxList.addItem("item 2");
        checkBoxList.addItem("item 3");
        checkBoxList.setEnabled(false);
        checkBoxList.setChecked("item 2", true);

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
