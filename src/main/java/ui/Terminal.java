package ui;

import Generate.Generate;
import Player.Player;
import algorithms.Algorithm;
import algorithms.MD5;
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
    private static boolean clearAlg = false;
    private static boolean once = true;
    private static boolean syntax = false;
    private static boolean algorithm = true;
    private static boolean oneGen = true;
    private static boolean validAlg = false;
    private static boolean triv = false;
    private static boolean triv2 = false;
    private static Generate g;
    private static Algorithm alg = new Algorithm(null, null, null);
    private static Progress progress = new Progress(null,null, null);
    static Thread t1;
    static Thread t2;

    static void start(Player p) throws Exception {

        StringBuilder sb = new StringBuilder();
        getCommands(p);
        int column = 5;
        int row = 22;
        currentCol = p.getColour();

        p.getScreen().clear();
        p.getScreen().setCursorPosition(new TerminalPosition(column, row));
        lastTyped = "";
        if (!lastScreen.equals("") && p.getRank() != 1) exeCommand(p);
        setup(p);
        p.getScreen().refresh();

        while (true) {

            KeyStroke keyStroke = p.getTerminal().pollInput();

            if (keyStroke != null) { // user cannot type during algorithm execution

                if (!progress.inProgress) {

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
                            if (!(sb.length() > 50)) {
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
                                Algorithm x = new Algorithm(g,lastTyped, p); // dummy algorithm
                                syntax = x.validate(); // valid algorithm syntax?
                                System.out.println(syntax);
                                if (validCommand()) lastScreen = lastTyped; // change screens
                                else if (validCol()) setColour(p); // can set terminal colour at any stage
                                exeCommand(p); // command is always executed, validation performed after
                                setup(p); // header and other permanent display elements always shown
                                sb.delete(0, sb.length()); }
                            break;

                        case Tab:
                            Menu.start(p);
                            break;
                    }
                    p.getScreen().setCursorPosition(new TerminalPosition(column, row));
                    p.getGraphics().putString(5,row, sb.toString(), SGR.BORDERED);
                    p.getScreen().refresh();
                }
                else {
                    switch (keyStroke.getKeyType()) {
                        case Escape:
                            t1.stop();
                            t2.stop();
                            progress.setInProgress(false);
                            break;
                    }
                }
            }
        }
    }

    private static boolean validSyntax() {
        if (lastTyped.contains(" ")) {
            int dlim = lastTyped.indexOf(" ");
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
             // user cannot navigate to any page if match is found, must enter pwd
                if (elValido()) p.getScreen().clear();
                switch (lastScreen) {
                    case "help":
                        helpScreen(p);
                        break;
                    case "syntax":
                        syntaxScreen(p);
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

        TerminalSize ts = new TerminalSize(75,16);
        TerminalPosition tp = new TerminalPosition(2,3);

        if (oneGen) {
            g = new Generate(p);
            oneGen = false;
        }

        if (p.getRank() > 20) {
            p.getGraphics().fillRectangle(tp, ts, ' ');
            displayProfile(g, p);
        }
        else {
            drawBoarder(p);

            if (validAlg && lastTyped.equals("req") && once) { // not sure why validAlg is needed but it works
                oneGen = true;
                once = false;
                profile = false;
                request(p);
            }

            else if (p.getAllItmes().containsAll(g.getRequired()) && (lastTyped.equals("y") || profile)) {
                p.getGraphics().fillRectangle(tp,ts,' ');
                displayProfile(g,p);
            }
        }
    }

    private static void displayProfile(Generate g, Player p) throws Exception {
        profile = true;
        int i;
        for (i = 0; i < g.getDescription().size(); i++)
            p.getGraphics().putString(3,4+i, g.getDescription().get(i));

        if (g.getPlainTextPassword() != null) p.getGraphics().putString(8,14, "> " + g.getHashedPassword(), SGR.BOLD);
        if (g.getPlainTextPassword()!= null && g.getSalt() != null) p.getGraphics().putString(8,14, "> " + g.getSalt() + ", " + MD5.getSaltHashPassword(g.getPlainTextPassword(), g.getSalt().getBytes()), SGR.BOLD);
        if (p.getRank() == 28) {
            int noAchievements = p.getAchievements().length() - p.getAchievements().replace("1", "").length();
            p.getGraphics().putString(10,14, "Passwords Cracked | \u20BFitcoin Acquired | Achievements Unlocked", SGR.BOLD);
            p.getGraphics().putString(18,16, String.valueOf(p.getRank()), SGR.BOLD, SGR.ITALIC);
            p.getGraphics().putString(50, 26, "Press [TAB] to return to Main Menu", SGR.BOLD, SGR.ITALIC);
            p.getGraphics().putString(35,16, String.valueOf(p.getBitcoin()+23.821f), SGR.BOLD, SGR.ITALIC);
            p.getGraphics().putString(57,16, String.valueOf(noAchievements) + "/9" , SGR.ITALIC, SGR.BOLD);

            drawFinalBoarder(p);
        }
        if (p.getRank() == 1) {
            algorithm = false;
            if (lastTyped.equals("password123")) {
                triv2 = true;
                p.getGraphics().putString(57,22, "Type 'req' to advance",SGR.BOLD, SGR.ITALIC);}
        }
        else if (p.getRank() == 2) {
            algorithm = false;
            if (lastTyped.equals("qjuehdxf")) {
                triv2 = true;
                p.getGraphics().putString(57,22, "Type 'req' to advance",SGR.BOLD, SGR.ITALIC);}
        }
        else if (p.getRank() == 3) {
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
                    p.getGraphics().putString(57,22, "Type 'req' to advance",SGR.BOLD, SGR.ITALIC);
                    triv2 = true;
                    break;
            }
        }
        else { // levels > 3
            if (algorithm && validSyntax() && syntax && !checkAlg()) exeAlgorithm(p,g); // execute algorithm if player owns it + correct syntax
            else if (alg.getComplete() && clearAlg && (alg.isGenCheckWriteCheck() || alg.isGenCheck())) progress.displayGenCheckWrite();
            else if (alg.getComplete() && clearAlg && alg.isFetchCheck()) progress.displayFetchCheck();
            else if (alg.getComplete() && clearAlg) { // display results after execution
                progress.staticFields();
                if (!checkAlg()) progress.fillProgress(); // no match
                else progress.success();                  // match
            }
        }

        if (p.getRank() == 29 && lastTyped.equals("continue")) {
            p.writeProgress(1,0,p.getPurchaced(),p.getColour(),p.getAchievements());
            profile = false;
            triv = false;
            triv2 = false;
            oneGen = true;
            clearAlg = false;
            alg.setResult("No match!");
            p.getScreen().clear();
            request(p);
        }

        if (checkAlg()) { // check if there has been a match
            triv = true;
            pwd = g.getPlainTextPassword();
        }

        if (triv || triv2) {
            if (checkTriv()) { // if match + user has typed password correctly
                p.writeProgress(1,g.getReward(),p.getPurchaced(),p.getColour(),p.getAchievements());
                profile = false;
                triv = false;
                triv2 = false;
                oneGen = true;
                clearAlg = false;
                alg.setResult("No match!");
                p.getScreen().clear();
                request(p);
            }
        }
        updateAchievements(p);
    }

    private static void updateAchievements(Player p) throws Exception {
        String achievements = p.getAchievements();
        if (p.getRank() > 1) {
            achievements = '1'+achievements.substring(1);
        }
        if (p.getRank() > 9) {
            achievements = achievements.substring(0,1)+'1'+achievements.substring(2);
        }
        if (p.getRank() > 19) {
            achievements = achievements.substring(0,2)+'1'+achievements.substring(3);
        }
        if (p.getRank() == 28) {
            achievements = achievements.substring(0,3)+'1'+achievements.substring(4);
        }
        p.writeProgress(0,0.0f,p.getPurchaced(),p.getColour(),achievements);
    }

    private static boolean checkTriv() {
        return (triv && lastTyped.equals(g.getPlainTextPassword()) || triv2 && lastTyped.equals("req"));
    }

    private static void exeAlgorithm(Player p, Generate g) {
        p.getGraphics().fillRectangle(new TerminalPosition(9,15),new TerminalSize(62,4), ' ');
        algorithm = false;
        clearAlg = true;
        alg = new Algorithm(g,lastTyped,p);
        t1 = new Thread(alg);
        if (alg.validate()) {
            syntax = true;
            progress = new Progress(alg,p,g);
            t2 = new Thread(progress);
            t1.start();
            t2.start();
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

    private static void setColour(Player p) throws Exception {
        currentCol = lastTyped.substring(7);
        p.writeProgress(0, 0, p.getPurchaced(),currentCol, p.getAchievements());
        p.getGraphics().setForegroundColor(TextColor.ANSI.valueOf(currentCol));
        exeCommand(p);
        setup(p);
    }

    private static void syntaxScreen(Player p) {
        p.getGraphics().putString(2,3, "Algorithm syntax", SGR.BOLD);
        for (int k=0;k<EXECUTE.size();k++) {
            p.getGraphics().putString(3,4+k, EXECUTE.get(k));
        }
    }

    private static void helpScreen(Player p) {

        p.getGraphics().putString(2,3, "Commands", SGR.BOLD);

        for (int i=0;i<COMMANDS.size();i++)
            p.getGraphics().putString(3, 4 + i, COMMANDS.get(i));

        int j = 0;
        for (TextColor.ANSI fgc : TextColor.ANSI.values()) {
            if (!fgc.equals(TextColor.ANSI.DEFAULT) && !fgc.equals(TextColor.ANSI.BLACK) && !fgc.equals(TextColor.ANSI.BLUE)) {
                p.getGraphics().setForegroundColor(fgc);
                    if (fgc.toString().equals(currentCol))
                        p.getGraphics().putString(14,12+j, COLOURS.get(j), SGR.BLINK);
                    else
                        p.getGraphics().putString(14,12+j, COLOURS.get(j));
                j++;
            }
        }
    }

    private static boolean elValido() {
        return (validCommand() || lastTyped.equals("") || validCol() || lastTyped.equals("y") || validAlg
                || profile && lastTyped.equals("req") || validSyntax() && syntax || lastTyped.equals(pwd) ||
                lastTyped.equals("password123") || lastTyped.equals("qjuehdxf") || lastTyped.equals("h(qjuehdxf)") ||
                lastTyped.equals("h(white_gold)") || lastTyped.equals("h(hashingiscool)"));
    }

    private static void setup(Player p) throws Exception {
        p.getGraphics().setForegroundColor(TextColor.ANSI.valueOf(currentCol));
        p.getGraphics().putString(2,20, "last typed: ");
        p.getGraphics().putString(3, 22, ">", SGR.BOLD);
        p.getGraphics().drawLine(14,20,50,20, ' ');
        if (elValido()) p.getGraphics().putString(14, 20, lastTyped, SGR.ITALIC);
        else p.getGraphics().putString(14, 20, "Unknown command: "+ lastTyped, SGR.ITALIC);
        p.getGraphics().putString(2,1, "PRESS [TAB] FOR MAIN MENU | " +
                "TYPE 'help' + [ENTER] FOR COMMANDS | \u20BF" + String.valueOf(p.getBitcoin()), SGR.BOLD);
    }

    private static void drawFinalBoarder(Player p) {
        p.getGraphics().drawLine(8, 12, 71, 12, valueOf(Symbols.DOUBLE_LINE_HORIZONTAL));
        p.getGraphics().drawLine(8, 18, 71, 18, valueOf(Symbols.DOUBLE_LINE_HORIZONTAL));
        p.getGraphics().drawLine(7, 13, 7, 17, valueOf(Symbols.DOUBLE_LINE_VERTICAL));
        p.getGraphics().drawLine(72, 13, 72, 17, valueOf(Symbols.DOUBLE_LINE_VERTICAL));
        p.getGraphics().putString(7, 12, String.valueOf(Symbols.DOUBLE_LINE_TOP_LEFT_CORNER));
        p.getGraphics().putString(72, 12, String.valueOf(Symbols.DOUBLE_LINE_TOP_RIGHT_CORNER));
        p.getGraphics().putString(7, 18, String.valueOf(Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER));
        p.getGraphics().putString(72, 18, String.valueOf(Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER));
    }

    private static void drawBoarder(Player p) throws Exception {
        if (p.getRank() == 20) {
            for (int i = 0; i < g.PRE20DESC.size(); i++) {
                p.getGraphics().putString(3,4+i, g.PRE20DESC.get(i));
            }
            p.getGraphics().putString(3,17, "You need to purchase hobbies and jobs dictionaries before continuing [y]", SGR.BOLD, SGR.ITALIC);

        } else {
            p.getGraphics().drawLine(26, 5, 54, 5, valueOf(Symbols.DOUBLE_LINE_HORIZONTAL));
            p.getGraphics().drawLine(26, 14, 54, 14, valueOf(Symbols.DOUBLE_LINE_HORIZONTAL));
            p.getGraphics().drawLine(25, 6, 25, 14, valueOf(Symbols.DOUBLE_LINE_VERTICAL));
            p.getGraphics().drawLine(54, 6, 54, 14, valueOf(Symbols.DOUBLE_LINE_VERTICAL));
            p.getGraphics().putString(25, 5, String.valueOf(Symbols.DOUBLE_LINE_TOP_LEFT_CORNER));
            p.getGraphics().putString(54, 5, String.valueOf(Symbols.DOUBLE_LINE_TOP_RIGHT_CORNER));
            p.getGraphics().putString(25, 14, String.valueOf(Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER));
            p.getGraphics().putString(54, 14, String.valueOf(Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER));
            p.getGraphics().putString(40 - (g.getTitle().length()/2),6, g.getTitle(), SGR.BOLD);
            getRequired(p);
            if (p.getAllItmes().containsAll(g.getRequired())) p.getGraphics().putString(34,13, "Accept? [y]", SGR.BOLD);
            else p.getGraphics().putString(31,13, "Please visit store!", SGR.BOLD);
        }
    }

    private static void getRequired(Player p) throws Exception {
        if (p.getRank() > 3) {
            p.getGraphics().putString(44,8, "Reward:", SGR.BOLD);
            p.getGraphics().putString(44, 9, "\u20BF" + g.getReward(),SGR.BOLD, SGR.ITALIC);
            p.getGraphics().putString(29,8, "Required:", SGR.BOLD);
            for (int i = 0; i < g.getRequired().size(); i++)
                p.getGraphics().putString(29,9+i, g.getRequired().get(i), SGR.BOLD, SGR.ITALIC);
        }
        else {
            p.getGraphics().putString(33,9, "Reward:", SGR.BOLD);
            p.getGraphics().putString(41,9, "\u20BF" + g.getReward(),SGR.BOLD, SGR.ITALIC);

        }
    }
    private static boolean checkAlg() { return !alg.getResult().equals("No match!"); }

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
}
