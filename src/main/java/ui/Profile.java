package ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import javax.swing.*;
import java.io.IOException;
import java.util.Random;

public class Profile extends Helper {

//    public Profile(WindowBasedTextGUI gui, Terminal ter) {
//        this.gui = gui;
//        this.ter = ter;
//    }

    public void start() throws IOException {

        //while (true) {
            Random random = new Random();
            TerminalSize ts = gui.getScreen().getTerminalSize();
            for(int column = 0; column < ts.getColumns(); column++) {
                for (int row = 0; row < ts.getRows(); row++) {
                    gui.getScreen().setCharacter(column, row, new TextCharacter(
                            ' ',
                            TextColor.ANSI.DEFAULT,
                            TextColor.ANSI.RED));
                }
            //}
            gui.getScreen().refresh();
        }

    }
}
