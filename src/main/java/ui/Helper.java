package ui;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.terminal.Terminal;

public abstract class Helper {

    public static boolean msg = false;
    public static boolean firstMsg = true;

    WindowBasedTextGUI gui;
    Terminal ter;

    // long text constants /////////////////////////////////////////////////////////////////////////////////////////////
    public final String HOW2PLAY =
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

    public final String ABOUT =
            "This artifact is part of an undergraduate research project \n" +
                    "created by Theo Koorehpaz that aims to increase password \n" +
                    "security and awareness by exposing participants to powerful \n" +
                    "password cracking techniques against common password datasets. \n\n" +
                    "Project title:   Password Security Gamification \n" +
                    "Lead Researcher: Theo Koorehpaz \n" +
                    "Institute:       University of Sheffield \n" +
                    "Supervisor:      Ramsay Taylor \n" +
                    "Module code:     COMxxx \n" +
                    "Department of Computer Science";

    public final String DISCLAIMER =
            "This is an educational simulation of \n" +
                    "password cracking and does not promote \n" +
                    "computer or information misuse in any way.";

    // file R/W methods for progress ///////////////////////////////////////////////////////////////////////////////////
    private static File file = new File("src\\main\\java\\progress.txt");

    public void resetProgress() throws Exception {
        FileWriter fw = new FileWriter(file);
        fw.write("0" + "\n" + "0.0");
        fw.close();
    }

    public void writeFile(int i, float f) throws Exception {
        int level = i + Integer.valueOf(getLevel());
        float bitcoin = f + Float.valueOf(getBitcoin());
        FileWriter fw = new FileWriter(file);
        fw.write(Integer.toString(level) + "\n" + Float.toString(bitcoin));
        fw.close();
    }

    public String getLevel() throws Exception {
        return Files.readAllLines(Paths.get(String.valueOf(file))).get(0);
    }

    public String getBitcoin() throws Exception {
        return Files.readAllLines(Paths.get(String.valueOf(file))).get(1);
    }

    // helper messageDialogBuilder to display custom message
    public void message(WindowBasedTextGUI gui, String title, String message) {
        new MessageDialogBuilder()
                .setTitle(title)
                .setText("\n" + message)
                .addButton(MessageDialogButton.OK)
                .build()
                .showDialog(gui);
        msg = true;
    }
}
