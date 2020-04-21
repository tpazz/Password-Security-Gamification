package ui;

import java.util.ArrayList;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

abstract class UI_Helper {

    protected static boolean msg = false;

    public static final String ACHIEVEMENT1 = "Crack your first password";
    public static final String ACHIEVEMENT2 = "Complete all brute-force levels";
    public static final String ACHIEVEMENT3 = "Complete all dictionary attack levels";
    public static final String ACHIEVEMENT4 = "Complete core game";
    public static final String ACHIEVEMENT5 = "Create a top-10 common password";
    public static final String ACHIEVEMENT6 = "Create a password with a Score of 1";
    public static final String ACHIEVEMENT7 = "Create a password with a Score of 2";
    public static final String ACHIEVEMENT8 = "Create a password with a Score of 3";
    public static final String ACHIEVEMENT9 = "Create a password with at least 60 entropy";

    public static final ArrayList<String> ACHIEVEMENTS = new ArrayList<String>() {{
        add(ACHIEVEMENT1);
        add(ACHIEVEMENT2);
        add(ACHIEVEMENT3);
        add(ACHIEVEMENT4);
        add(ACHIEVEMENT5);
        add(ACHIEVEMENT6);
        add(ACHIEVEMENT7);
        add(ACHIEVEMENT8);
        add(ACHIEVEMENT9);
    }};

    public static final ArrayList<String> METACOMMANDS = new ArrayList<String>() {{
        add("help");
        add("example");
        add("show items");
        add("syntax");
        add("req");
    }};

    public static final ArrayList<String> VALIDCOMMANDS = new ArrayList<String>() {{
        add("help");
        add("example");
        add("show items");
        add("clear");
        add("req");
        add("y");
        add("syntax");
    }};

        public static final ArrayList<String> COMMANDS = new ArrayList<String>() {{
        add("[Up Arrow]   -> grab last command");
        add("[Down Arrow] -> clear command");
        add("[ENTER]      -> execute command");
        add("[Esc]        -> terminate algorithm in progress");
        add("syntax       -> display algorithm syntax");
        add("req          -> request a profile/return to profile page");
        add("show items   -> display bought tools");
        add("setCol (COL) -> set terminal text colour (case sensitive)");
        add("         ↳ ");
    }};

    public static final ArrayList<String> COLOURS = new ArrayList<String>() {{
        add("RED");
        add("GREEN");
        add("YELLOW");
        add("MAGENTA");
        add("CYAN");
        add("WHITE");
    }};

        public static final ArrayList<String> EXECUTE = new ArrayList<String>() {{
        add("num_brute   [range]                ~ num_brute 9999");
        add("alpha_brute [cType] [cType] [len]  ~ alpha_brute -l 4");
        add("dic         [dic]   [extn]         ~ dic english -n");
        add("comb_dic    [dic1]  [dic2]  [extn] ~ comb_dic fnames snames -s asdf");
        add("hybrid_dic  [range] [dic]   [extn] ~ hybrid 9999 english -p n");
        add("");
        add("cType  > char space      ~ -l -u -n -s, lower/upper/numerical/special");
        add("length > char length     ~ 4 = all possible combinations of length 4");
        add("range  > integer range   ~ 9999 = combinations from 0 - 9999");
        add("dic    > dictionary name ~ excluding .dic or .txt, e.g. dictionary surnames");
        add("extn   > extension       ~ -n for standard, -g to generate hash table");
        add("                           -s [salt], -p [pepper] => l | u | n | s");
        add("example >> dic english -s asdf; dic english -p n; dic english -g;");
        add("");
        add("-s, -p, -g are optional and replace -n ~ example: dic english -s @#2Q -p s");
    }};

    public static final String HOW2PLAY =
        "The objective of this game is to learn about password security\n" +
        "by cracking various types of passwords, ranging from simple\n" +
        "brute-force numerical passcodes to complex multi-dictionary and\n" +
        "hybrid passwords. The first 19 levels will walk you through \n" +
        "several cracking techniques and countermeasures in order to \n" +
        "familiarise yourself with the mechanics of the algorithms and\n" +
        "how they work. Levels 20-27 will involve attempting to crack\n" +
        "randomly generated hashed passwords using all of the acquired\n" +
        "knowledge from previous levels and items from the store, which\n" +
        "can be bought with \u20BFitcoin earned by completing the levels\n" +
        "and creating strong passwords based on their entropy value from\n" +
        "the Password Strength page.\n\n" +
        "Select 'Terminal' from the Main Menu to start. Enjoy!";

    public static final String ABOUT =
        "This artifact is part of an undergraduate research project \n" +
        "created by Theo Koorehpaz that aims to increase password \n" +
        "security and awareness by exposing participants to powerful \n" +
        "password cracking techniques against password datasets. \n\n" +
        "Project title:   Password Security Gamification \n" +
        "Lead Researcher: Theo Koorehpaz \n" +
        "Institute:       University of Sheffield \n" +
        "Supervisor:      Ramsay Taylor \n" +
        "Module code:     COM3610 \n" +
        "Department:      Computer Science";

    // helper messageDialogBuilder to display custom message
    public static void message(WindowBasedTextGUI gui, String title, String message) {
        new MessageDialogBuilder()
                .setTitle(title)
                .setText("\n" + message)
                .addButton(MessageDialogButton.OK)
                .build()
                .showDialog(gui);
        msg = true;
    }
}
