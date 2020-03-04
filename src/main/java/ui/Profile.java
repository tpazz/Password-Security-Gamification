package ui;

import Player.Player;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import javax.xml.soap.Text;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

class Profile extends UI_Helper {

    private static String lastTyped = "";
    private static String currentCol;

    static void start(Player p) throws Exception {

        StringBuilder sb = new StringBuilder();
        boolean keepRunning = true;
        int column = 5;
        int row = 22;
        currentCol = p.getColour();

        p.getGraphics().setForegroundColor(TextColor.ANSI.valueOf(currentCol));
        p.getScreen().clear();
        setup(p);
        p.getScreen().setCursorPosition(new TerminalPosition(column, row));
        p.getScreen().refresh();
        System.out.println(Arrays.toString(TextColor.ANSI.values()));
        // salt is stored with the password
        // pepper is not

        // salts protect against hash table attacks
        // peppers protect against brute force/dictionary attacks

        // use bitcoin to buy hashed password + info if salt or hash is used
        // generate hashed password along with salt

        while (keepRunning) {

            KeyStroke keyStroke = p.getTerminal().pollInput();

            if (keyStroke != null) {

                switch (keyStroke.getKeyType()) {

                    case ArrowUp:
                        if (!lastTyped.equals("")) {
                            column = 5+lastTyped.length();
                            String tmp = lastTyped;
                            sb.delete(0,49);
                            p.getScreen().clear();
                            sb.append(tmp); }
                        break;

                    case ArrowDown:
                        column = 5; row = 22;
                        sb.delete(0,49);
                        p.getScreen().clear();
                        break;

                    case Backspace:
                        if (!(sb.length() < 1)) {
                            column--;
                            p.getScreen().clear();
                            sb.deleteCharAt(sb.length()-1); }
                        break;

                    case Character:
                        if (!(sb.length() > 35)) {
                            column++;
                            sb.append(keyStroke.getCharacter()); }
                        break;

                    case Enter:
                        if (!sb.toString().equals("")) {
                            column = 5; row = 22;
                            p.getScreen().clear();
                            lastTyped = sb.toString();
                            sb.delete(0, sb.length());
                            exeCommand(p); }
                        break;

                    case Escape:
                        Menu.start(p);
                        keepRunning = !keepRunning;
                        break;
                }
                p.getScreen().setCursorPosition(new TerminalPosition(column, row));
                p.getGraphics().putString(5,row, sb.toString());
                setup(p);
                p.getScreen().refresh();
            }
        }
    }

    private static void exeCommand(Player p) throws Exception {
        if (lastTyped.equals("help"))
            helpScreen(p);

        else if ((lastTyped.substring(0,6).equals("setCol")) && COLOURS.contains(lastTyped.substring(7)))
            setColour(p);

        else if (lastTyped.equals("example")) {
            example(p);
        }
    }

    private static void example(Player p) {
        p.getScreen().clear();
        p.getGraphics().putString(2,4, "Example run-through", SGR.BOLD);
    }

    private static void setColour(Player p) throws Exception {
        currentCol = lastTyped.substring(7);
        p.writeProgress(0, 0, p.getPurchaced(),currentCol);
        p.getGraphics().setForegroundColor(TextColor.ANSI.valueOf(currentCol));
    }

    private static void helpScreen(Player p) throws Exception {
        p.getScreen().refresh();
        p.getGraphics().putString(2,3, "Algorithm structure", SGR.BOLD);
        p.getGraphics().putString(2,12, "Commands", SGR.BOLD);

        for (int k=0;k<EXECUTE.size();k++) {
            p.getGraphics().putString(3,4+k, EXECUTE.get(k));
        }

        for (int i=0;i<COMMANDS.size();i++) {
            if (i > 5)
                p.getGraphics().putString(38,7+i, COMMANDS.get(i));
            else
                p.getGraphics().putString(3,13+i, COMMANDS.get(i));
        }

        for (int l=0; l<DESCRIPTION.size();l++) {
            p.getGraphics().putString(44,4+l, DESCRIPTION.get(l));
        }

        int j = 0;
        for (TextColor.ANSI fgc : TextColor.ANSI.values()) {
            if (!fgc.equals(TextColor.ANSI.DEFAULT) && !fgc.equals(TextColor.ANSI.BLACK)) {
                p.getGraphics().setForegroundColor(fgc);
                if (fgc.toString().equals(currentCol))
                    p.getGraphics().putString(49,15+j, COLOURS.get(j), SGR.BLINK);
                else
                    p.getGraphics().putString(49,15+j, COLOURS.get(j));
                j++;
            }
        }
    }

    private static void setup(Player p) throws Exception {
        p.getGraphics().setForegroundColor(TextColor.ANSI.valueOf(currentCol));
        p.getGraphics().putString(2,20, "last typed: ");
        p.getGraphics().putString(3, 22, ">", SGR.BOLD);
        p.getGraphics().putString(14, 20, lastTyped, SGR.ITALIC);
        p.getGraphics().putString(2,1, "PRESS [ESC] FOR MAIN MENU | " +
                "TYPE 'help' + [ENTER] FOR COMMANDS", SGR.BOLD);
        p.getGraphics().putString(70, 1, "\u20BF " + p.getBitcoin(), SGR.BOLD);
        p.getGraphics().putString(70, 2, "Rank: " + p.getRank(), SGR.BOLD);
    }
}
