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
        TerminalSize ts = terminal.getTerminalSize();

        boolean keepRunning = true;
        int column = 5;
        int row = 22;

        gui.getScreen().clear();
        setup(tg, ts);
        gui.getScreen().setCursorPosition(new TerminalPosition(column, row));
        gui.getScreen().refresh();

        // salt is stored with the password
        // pepper is not

        // salts protect against hash table attacks
        // peppers protect against brute force/dictionary attacks

        // use bitcoin to buy hashed password + info if salt or hash is used
        // generate hashed password along with salt

        // Exe f7dx -d English -s || English
        // Exe f7dx -h English 4 || 4 English
        // Exe f7dx -c English Two Word
        // Exe f7dx -b Alphanumeric 8
        // Exe f7dx -k hello 99 cool
        // No matches found. Perhaps try adding a salt or pepper?
        // alpha_brute p9x01k -s

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
                            tg.putString(5,row, sb.toString());
                            setup(tg,ts);
                        }
                        break;
                    case Character:
                        if (!(sb.length() > 50)) {
                            column++;
                            gui.getScreen().setCursorPosition(new TerminalPosition(column, row));
                            sb.append(keyStroke.getCharacter());
                            tg.putString(5,row, sb.toString());
                        }
                        break;
                    case Escape:
                        Menu.start(gui, terminal);
                        keepRunning = !keepRunning;
                        break;
                }
                setup(tg, terminal.getTerminalSize());
                gui.getScreen().refresh();
            }
        }
    }

    private static void setup(TextGraphics tg, TerminalSize ts) throws Exception {

        tg.setForegroundColor(TextColor.ANSI.GREEN);
        tg.putString(3, 22, ">", SGR.BOLD);
        tg.putString(2,1, "Available algorithms:", SGR.BOLD);
        tg.putString(25,1, "Available dictionaries:", SGR.BOLD);
        tg.putString(ts.getColumns()-10, ts.getRows()-23, "\u20BF" + getBitcoin(), SGR.BOLD);
        tg.putString(ts.getColumns()-10, ts.getRows()-22, "Rank: " + getLevel(), SGR.BOLD);

        for (int i = 0; i < getAlgorithms().size(); i++) {
            tg.putString(2, 2+i, getAlgorithms().get(i));
        }

        for (int i = 0; i < getDictionaries().size(); i++) {
            tg.putString(25, 2+i, getDictionaries().get(i));
        }
    }
}
