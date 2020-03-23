package ui;

import java.util.ArrayList;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

abstract class UI_Helper {

    protected static boolean msg = false;

    public static final ArrayList<String> METACOMMANDS = new ArrayList<String>() {{
        add("help");
        add("example");
        add("show items");
        add("clear");
        add("request");
    }};

        public static final ArrayList<String> COMMANDS = new ArrayList<String>() {{
        add("[Up Arrow]   -> grab last command");
        add("[Down Arrow] -> clear command");
        add("[ENTER]      -> execute command");
        add("request      -> request a profile");
        add("example      -> example crack");
        add("show items   -> display bought tools");
        add("hint         -> give password hint");
        add("setCol (col) -> set terminal text colour");
        add("         ↳");
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
        add("num_brute      [range]");
        add("alpha_brute    [cSpace] [length]");
        add("alpha_num      [cSpace] [length] [range]");
        add("dictionary     [dic]    [hash]");
        add("combinator_dic [dic1]   [dic2]   [hash]");
        add("hybrid         [range]  [dic]    [hash]");
        add("keyword        [str1]   [str2]   [str3]");
    }};

    public static final ArrayList<String> DESCRIPTION = new ArrayList<String>() {{
        add("  hash ~> hash value from profile");
        add("cSpace ~> char space | -a -s -as");
        add("length ~> char length | 4");
        add(" range ~> integer range | 9999");
        add("   dic ~> dictionary name");
        add("   str ~> arbitrary string");
    }};

    public static final String HOW2PLAY =
        "The objective of this game is to crack passwords from profiles. There \n" +
        "are 10 core levels, each one increasing in difficulty. \u20BFitcoin is \n" +
        "awarded for each successful crack which can be used to purchase \n" +
        "powerful algorithms to help you Progress. Profiles are randomly \n" +
        "generated with an associated password that is related to the profile \n" +
        "in some way, for instance: \n\n" +
        "First name: John \n" +
        "Last name: Smith \n" +
        "DoB: 15/07/1988 \n\n" +
        "Password: j0hnSm1th88 \n\n" +
        "If you are stuck on a particular profile, you can request another \n" +
        "or ask for a hint - a maximum of 3 profiles can be active at a time.";

    public static final String ABOUT =
        "This artifact is part of an undergraduate research project \n" +
        "created by Theo Koorehpaz that aims to increase password \n" +
        "security and awareness by exposing participants to powerful \n" +
        "password cracking techniques against common password datasets. \n\n" +
        "Project title:   Password Security Gamification \n" +
        "Lead Researcher: Theo Koorehpaz \n" +
        "Institute:       University of Sheffield \n" +
        "Supervisor:      Ramsay Taylor \n" +
        "Module code:     COMxxx \n" +
        "Department:      Computer Science";

    public static final String DISCLAIMER =
        "This is an educational simulation of \n" +
        "password cracking and does not promote \n" +
        "computer or information misuse in any way.";

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
