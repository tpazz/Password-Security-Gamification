package ui;

import Generate.Generate;
import Player.Player;
import algorithms.Algorithm;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.input.KeyStroke;
import metadata.Progress;
import static java.lang.Character.valueOf;

class Terminal extends UI_Helper {

    private static String lastTyped = "";
    private static String lastScreen = "";
    private static String currentCol = "";
    private static String pwd = "";
    private static boolean profile = false;
    private static boolean once = true;
    private static boolean algorithm = true;
    private static boolean oneGen = true;
    private static boolean validAlg = false;
    private static boolean triv = false;
    private static boolean triv2 = false;
    private static Generate g;
    private static Algorithm alg;
    private static Progress progress = new Progress(null,null);

    static void start(Player p) throws Exception {


        StringBuilder sb = new StringBuilder();
        getCommands(p);
        System.out.println(VALIDCOMMANDS);
        int column = 5;
        int row = 22;
        currentCol = p.getColour();

        p.getScreen().clear();
        p.getScreen().setCursorPosition(new TerminalPosition(column, row));
        setup(p);
        p.getScreen().refresh();

        // salt is stored with the password
        // pepper is not

        // salts protect against hash table attacks
        // peppers protect against brute force/dictionary attacks

        while (true) {

            KeyStroke keyStroke = p.getTerminal().pollInput();

            if (keyStroke != null && !progress.inProgress) {

                switch (keyStroke.getKeyType()) {

                    case ArrowUp:
                        if (!lastTyped.equals("")) {
                            column = 5+lastTyped.length();
                            String tmp = lastTyped;
                            sb.delete(0,49);
                            sb.append(tmp); }
                        break;

                    case ArrowDown:
                        column = 5; row = 22;
                        sb.delete(0,49);
                        p.getGraphics().drawLine(5,22,25,22, ' ');
                        break;

                    case Backspace:
                        if (!(sb.length() < 1)) {
                            column--;
                            p.getGraphics().putString(column,22, " ");
                            sb.deleteCharAt(sb.length()-1); }
                        break;

                    case Character:
                        if (!(sb.length() > 25)) {
                            column++;
                            sb.append(keyStroke.getCharacter()); }
                        break;

                    case Enter:
                        if (!sb.toString().equals("")) {
                            once = true;
                            column = 5; row = 22;
                            p.getGraphics().drawLine(5,22,25,22, ' ');
                            algorithm = true;
                            lastTyped = sb.toString();
                            if (validCommand()) lastScreen = lastTyped;
                            else if (validCol()) setColour(p);
                            sb.delete(0, sb.length()); }
                        break;

                    case Escape:
                        Menu.start(p);
                        break;

                }
                p.getScreen().setCursorPosition(new TerminalPosition(column, row));
                p.getGraphics().putString(5,row, sb.toString(), SGR.BORDERED);
                exeCommand(p);
                setup(p);
                p.getScreen().refresh();
            }
        }
    }

    private static boolean validSyntax() {
        if (lastTyped.contains(" ")) {
            int dlim = lastTyped.indexOf(" ");
            System.out.println(lastTyped.substring(0, dlim));
            return VALIDCOMMANDS.contains(lastTyped.substring(0, dlim));
        } else {
            return VALIDCOMMANDS.contains(lastTyped);
        }
    }

    private static void getCommands(Player p) throws Exception {
        VALIDCOMMANDS.addAll(p.getAlgorithms());
    }

    private static void exeCommand(Player p) {
        try {

            switch (lastScreen) {
                case "help":
                    helpScreen(p);
                    break;

                case "show items":
                    showItems(p);
                    break;

                case "req":
                    request(p);
                    break;
            }
        } catch (Exception ignore) {}
    }

    private static void request(Player p) throws Exception {

        if (oneGen) {
            g = new Generate(p);
            oneGen = false;
        }

        TerminalSize ts = new TerminalSize(30,9);
        TerminalPosition tp = new TerminalPosition(25,6);
        drawBoarder(p);

        if (validAlg && lastTyped.equals("req") && once) {
            oneGen = true;
            once = false;
            profile = false;
            request(p);
        }

        else if (lastTyped.equals("y") || profile) {
            p.getGraphics().fillRectangle(tp,ts,' ');
            displayProfile(g,p);
        }
    }

    private static void displayProfile(Generate g, Player p) throws Exception {
        profile = true;
        int i;
        for (i = 0; i < g.getDescription().size(); i++) {
            p.getGraphics().putString(3,4+i, g.getDescription().get(i));
        }
        if (g.getPlainTextPassword() != null) p.getGraphics().putString(3,5+i, "> " + g.getHashedPassword(), SGR.BOLD);
        if (p.getRank() == 1) {
            algorithm = false;
            if (lastTyped.equals("password123")) {
                triv2 = true;
                p.getGraphics().putString(45,22, "Type 'request' for next challenge"); }
        } else if (p.getRank() == 2) {
            algorithm = false;
            if (lastTyped.equals("qjuehdxf")) {
                triv2 = true;
                p.getGraphics().putString(45,22, "Type 'request' for next challenge"); }
        } else if (p.getRank() == 3) {
            algorithm = false;
            switch (lastTyped) {
                case "h(qjuehdxf)":
                    p.getGraphics().putString(39, 17, "da0cebfbcf5bbc4b22e4e1ed3d4c70c2");
                    break;
                case "h(letmein123)":
                    p.getGraphics().putString(39, 17, "4ca7c5c27c2314eecc71f67501abb724");
                    break;
                case "h(white_gold)":
                    p.getGraphics().putString(39, 17, "78bd36d4a3fabba1f528b9f804ce03dd");
                    break;
                case "h(hashingiscool)":
                    p.getGraphics().putString(39, 17, "29a5002cf73a9852da4b12811c0897cc");
                    p.getGraphics().putString(45,22, "Type 'request' for next challenge");
                    triv2 = true;
                    break;
            }
        }

        if (triv || triv2) {
            if (checkTriv()) {
                pwd = g.getPlainTextPassword();
                p.writeProgress(1,g.getReward(),p.getPurchaced(),p.getColour());
                profile = false;
                triv = false;
                triv2 = false;
                oneGen = true;
                p.getScreen().clear();
                request(p);
            }
        }
        else {
            if (algorithm && validSyntax()) exeAlgorithm(p,g);
            else if (checkAlg()) triv = true;
        }
    }

    private static boolean checkTriv() {
        return (triv && lastTyped.equals(g.getPlainTextPassword()) || triv2 && lastTyped.equals("req"));
    }

    private static void exeAlgorithm(Player p, Generate g) {
        if (!triv) p.getGraphics().fillRectangle(new TerminalPosition(9,15),new TerminalSize(62,4), ' ');
        algorithm = false;
        alg = new Algorithm(g,lastTyped);
        Thread t1 = new Thread(alg);
        if (alg.validate()) {
            progress = new Progress(alg,p);
            Thread t2 = new Thread(progress);
            t1.start();
            t2.start();
        }
    }

    private static boolean checkAlg() { return !alg.getResult().equals("No match!") && !alg.getResult().equals(""); }

    private static boolean validCommand() {
        return METACOMMANDS.contains(lastTyped);
    }

    private static boolean validCol() {
        boolean x = false;
        try {
            if ((lastTyped.substring(0, 6).equals("setCol")) && COLOURS.contains(lastTyped.substring(7)))
                x = true;
        } catch (Exception e) { x = false; }
        return x;
    }

    private static void showItems(Player p) throws Exception {
        TerminalSize ts = new TerminalSize(77,17);
        TerminalPosition tp = new TerminalPosition(2,3);
        p.getGraphics().fillRectangle(tp,ts,' ');
        p.getGraphics().putString(2,3, "Your algorithms", SGR.BOLD);
        for (int i=0; i<p.getAlgorithms().size();i++) {
            p.getGraphics().putString(3,4+i, p.getAlgorithms().get(i));
        }
        p.getGraphics().putString(29,3, "Your dictionaries", SGR.BOLD);
        for (int j=0; j<p.getDictionaries().size();j++) {
            p.getGraphics().putString(30,4+j, p.getDictionaries().get(j));
        }
    }

    private static void setColour(Player p) throws Exception {
        currentCol = lastTyped.substring(7);
        p.writeProgress(0, 0, p.getPurchaced(),currentCol);
        p.getGraphics().setForegroundColor(TextColor.ANSI.valueOf(currentCol));
        exeCommand(p);
        setup(p);
    }

    private static void helpScreen(Player p) {
        TerminalSize ts = new TerminalSize(77,17);
        TerminalPosition tp = new TerminalPosition(2,3);
        p.getGraphics().fillRectangle(tp,ts,' ');
        p.getGraphics().putString(2,3, "Algorithm syntax", SGR.BOLD);
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
            if (!fgc.equals(TextColor.ANSI.DEFAULT) && !fgc.equals(TextColor.ANSI.BLACK) && !fgc.equals(TextColor.ANSI.BLUE)) {
                p.getGraphics().setForegroundColor(fgc);
                if (j < 3) {
                    if (fgc.toString().equals(currentCol))
                        p.getGraphics().putString(49,15+j, COLOURS.get(j), SGR.BLINK);
                    else
                        p.getGraphics().putString(49,15+j, COLOURS.get(j));
                } else {
                    if (fgc.toString().equals(currentCol))
                        p.getGraphics().putString(56,12+j, COLOURS.get(j), SGR.BLINK);
                    else
                        p.getGraphics().putString(56,12+j, COLOURS.get(j));
                }
                j++;
            }
        }
    }

    private static void setup(Player p) throws Exception {
        p.getGraphics().setForegroundColor(TextColor.ANSI.valueOf(currentCol));
        p.getGraphics().putString(2,20, "last typed: ");
        p.getGraphics().putString(3, 22, ">", SGR.BOLD);
        p.getGraphics().drawLine(14,20,50,20, ' ');
        //System.out.println(g.getPlainTextPassword());
        if (validCommand() || lastTyped.equals("") || validCol() || lastTyped.equals("y") || validAlg
                || profile && lastTyped.equals("req") || validSyntax() || lastTyped.equals(pwd))
            p.getGraphics().putString(14, 20, lastTyped, SGR.ITALIC);
        else
            p.getGraphics().putString(14, 20, "Unknown command: "+ lastTyped, SGR.ITALIC);
        p.getGraphics().putString(2,1, "PRESS [ESC] FOR MAIN MENU | " +
                "TYPE 'help' + [ENTER] FOR COMMANDS", SGR.BOLD);
        p.getGraphics().putString(70, 1, "\u20BF " + p.getBitcoin(), SGR.BOLD);
        p.getGraphics().putString(70, 2, "Rank: " + p.getRank(), SGR.BOLD);
    }

    private static void drawBoarder(Player p) throws Exception {
        p.getGraphics().drawLine(26, 6, 54, 6, valueOf(Symbols.DOUBLE_LINE_HORIZONTAL));
        p.getGraphics().drawLine(26, 14, 54, 14, valueOf(Symbols.DOUBLE_LINE_HORIZONTAL));
        p.getGraphics().drawLine(25, 7, 25, 14, valueOf(Symbols.DOUBLE_LINE_VERTICAL));
        p.getGraphics().drawLine(54, 7, 54, 14, valueOf(Symbols.DOUBLE_LINE_VERTICAL));
        p.getGraphics().putString(25, 6, String.valueOf(Symbols.DOUBLE_LINE_TOP_LEFT_CORNER));
        p.getGraphics().putString(54, 6, String.valueOf(Symbols.DOUBLE_LINE_TOP_RIGHT_CORNER));
        p.getGraphics().putString(25, 14, String.valueOf(Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER));
        p.getGraphics().putString(54, 14, String.valueOf(Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER));
        p.getGraphics().putString(31,7, "Difficulty: " + g.getDifficulty(), SGR.ITALIC);
        p.getGraphics().putString(30,9, "Reward: \u20BF" + g.getReward(), SGR.BOLD);
        if (p.getRank() > 3) p.getGraphics().putString(30,10, "Required: " + g.getAlgorithm(), SGR.BOLD);
        p.getGraphics().putString(34,13, "Accept? [y]", SGR.BOLD);
    }
}
