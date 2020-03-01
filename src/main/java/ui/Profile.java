package ui;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.Terminal;

public class Profile extends UI {

    public static void start(WindowBasedTextGUI gui, Terminal terminal) throws Exception {

        TextGraphics tg = gui.getScreen().newTextGraphics();
        StringBuilder sb = new StringBuilder();

        gui.getScreen().clear();
        gui.getScreen().refresh();

        boolean keepRunning = true;
        int column = 5;
        int row = 22;

        tg.setForegroundColor(TextColor.ANSI.GREEN);
        tg.putString(3, row, ">", SGR.BOLD);

        gui.getScreen().setCursorPosition(new TerminalPosition(column, row));
        gui.getScreen().refresh();

        while (keepRunning) {

            KeyStroke keyStroke = terminal.pollInput();

            if (keyStroke != null) {
                switch (keyStroke.getKeyType()) {
                    case Backspace:
                        if (!(sb.length() < 1)) {
                            column--;
                            gui.getScreen().clear();
                            gui.getScreen().setCursorPosition(new TerminalPosition(column, row));
                            sb.deleteCharAt(sb.length()-1);
                            tg.putString(5,row, sb.toString(), SGR.BOLD);
                        }
                        break;
                    case Character:
                        if (!(sb.length() > 50)) {
                            column++;
                            gui.getScreen().setCursorPosition(new TerminalPosition(column, row));
                            sb.append(keyStroke.getCharacter());
                            tg.putString(5,row, sb.toString(), SGR.FRAKTUR);
                        }
                        break;
                    case Escape:
                        Menu.start(gui, terminal);
                        keepRunning = !keepRunning;
                        break;
                }
                tg.putString(3, row, ">", SGR.BOLD);
                gui.getScreen().refresh();
            }
        }
    }
}
