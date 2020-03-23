package ui;

import Generate.Generate;
import Player.Player;
import algorithms.Algorithm;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.input.KeyStroke;
import metadata.Progress;


class Terminal extends UI_Helper {

    private static String lastTyped = "";
    private static String lastScreen = "";
    private static String currentCol = "";
    private static boolean request = false;
    private static boolean profile = false;
    private static boolean once = true;
    private static boolean algorithm = false;
    private static boolean oneGen = true;
    private static boolean validAlg = false;
    private static Generate g;

    static void start(Player p) throws Exception {

        StringBuilder sb = new StringBuilder();

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

        // use bitcoin to buy hashed password + info if salt or hash is used
        // generate hashed password along with salt

        while (true) {

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
                            algorithm = true;
                            once = true;
                            column = 5; row = 22;
                            p.getScreen().clear();
                            lastTyped = sb.toString();
                            if (validCommand()) {
                                lastScreen = lastTyped;
                            } else if (validCol()) {
                                setColour(p);
                            }
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

    private static void displayProfile(Generate g, Player p) throws Exception {
        profile = true;
        TerminalSize ts = new TerminalSize(40,14);
        TerminalPosition tp = new TerminalPosition(20,6);
        p.getGraphics().fillRectangle(tp,ts,' ');
//        p.getGraphics().putString(10,6,g.getHashedPassword());
//        p.getGraphics().putString(10,7,g.getPlainTextPassword());
//        p.getGraphics().putString(10,8,g.getFirstName());
//        p.getGraphics().putString(10,9,g.getLastName());
        if (algorithm) exeAlgorithm(p,g);
    }

    private static void exeAlgorithm(Player p, Generate g) throws Exception {
        algorithm = false;
        Algorithm alg = new Algorithm(g,lastTyped);
        Thread t1 = new Thread(alg);
        if (alg.validate()) {
            Progress progress = new Progress(alg,p);
            Thread t2 = new Thread(progress);
            t1.start();
            t2.start();
        }
    }

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

    private static void exeCommand(Player p) {
            try {
                switch (lastScreen) {
                    case "help":
                        helpScreen(p);
                        break;
                    case "example":
                        example(p);
                        break;
                    case "show items":
                        showItems(p);
                        break;
                    case "request":
                        request(p);
                        break;
                }
            } catch (Exception ignore) {}
    }

    private static void request(Player p) throws Exception {
        request = true;
        if (oneGen) {
            if (validAlg) {
                validAlg = false;
                p.writeProgress(1,0.0f,p.getPurchaced(),p.getColour());
            }
            g = new Generate(p);
            oneGen = false;
        }
        TerminalSize ts = new TerminalSize(30,10);
        TerminalPosition tp = new TerminalPosition(25,6);
        p.getGraphics().drawRectangle(tp,ts,'+');
        p.getGraphics().putString(31,8, "Profile: " + g.getFirstName() + " " + g.getLastName(), SGR.UNDERLINE);
        p.getGraphics().putString(30,10, "Reward: \u20BF" + g.getReward(), SGR.BOLD);
        p.getGraphics().putString(30,11, "Recommended: " + g.getAlgorithm(), SGR.BOLD);
        p.getGraphics().putString(29,14, "Do you accept? [YES/NO]", SGR.BOLD);
        p.getGraphics().putString(22,17, "[NO] will regenerate another profile", SGR.BOLD);

        if ((lastTyped.equals("NO") && !profile && once)) {
            oneGen = true;
            once = false;
            request(p);
        }

        else if (validAlg && lastTyped.equals("request") && once) {
            oneGen = true;
            once = false;
            profile = false;
            request(p);
        }

        else if (lastTyped.equals("YES") || profile) {
            displayProfile(g,p);
        }
    }

    private static void showItems(Player p) throws Exception {
        p.getGraphics().putString(2,3, "Your algorithms", SGR.BOLD);
        for (int i=0; i<p.getAlgorithms().size();i++) {
            p.getGraphics().putString(3,4+i, p.getAlgorithms().get(i));
        }
        p.getGraphics().putString(29,3, "Your dictionaries", SGR.BOLD);
        for (int j=0; j<p.getDictionaries().size();j++) {
            p.getGraphics().putString(30,4+j, p.getDictionaries().get(j));
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
        exeCommand(p);
        setup(p);
    }

    private static void helpScreen(Player p) throws Exception {
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
        if (validCommand() || lastTyped.equals("") || validCol() || lastTyped.equals("YES") || validAlg
                || !profile && lastTyped.equals("NO")  || profile && lastTyped.equals("request"))
            p.getGraphics().putString(14, 20, lastTyped, SGR.ITALIC);
        else
            p.getGraphics().putString(14, 20, "Unknown command: "+ lastTyped, SGR.ITALIC);
        p.getGraphics().putString(2,1, "PRESS [ESC] FOR MAIN MENU | " +
                "TYPE 'help' + [ENTER] FOR COMMANDS", SGR.BOLD);
        p.getGraphics().putString(70, 1, "\u20BF " + p.getBitcoin(), SGR.BOLD);
        p.getGraphics().putString(70, 2, "Rank: " + p.getRank(), SGR.BOLD);
    }
}