package ui;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public abstract class UI {

    public static boolean msg = false;

    // long string constants
    public static final String HOW2PLAY =
            "The objective of this game is to crack passwords from profiles. There \n" +
                    "are 10 core levels, each one increasing in difficulty. \u20BFitcoin is \n" +
                    "awarded for each successful crack which can be used to purchase \n" +
                    "powerful algorithms to help you progress. Profiles are randomly \n" +
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

    // file R/W methods for progress
    private static File file = new File("src\\main\\java\\progress.txt");

    public static void resetProgress() throws Exception {
        FileWriter fw = new FileWriter(file);
        fw.write("0" + "\n" + "0.0");
        fw.close();
    }

    public static void writeFile(int i, float f) throws Exception {
        int level = i + Integer.valueOf(getLevel());
        float bitcoin = f + Float.valueOf(getBitcoin());
        FileWriter fw = new FileWriter(file);
        fw.write(Integer.toString(level) + "\n" + Float.toString(bitcoin));
        fw.close();
    }

    public static int getLevel() throws Exception {
        String tmp =  Files.readAllLines(Paths.get(String.valueOf(file))).get(0);
        return Integer.valueOf(tmp);
    }

    public static float getBitcoin() throws Exception {
        String tmp = Files.readAllLines(Paths.get(String.valueOf(file))).get(1);
        return Float.valueOf(tmp);
    }
}
