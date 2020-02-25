import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    static File file = new File("src\\main\\java\\progress.txt");

    public static void main(String[] args) throws Exception {
        // Setup terminal and screen layers
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
        message(textGUI, "Acknowledgement", ACKNOWLEDGEMENT);
        menu(textGUI);
    }

    private static void menu(WindowBasedTextGUI gui) throws Exception {
        if (getLevel().equals("0")) { // fresh start or selecting 'New Game' will omit 'Continue' option
            new ActionListDialogBuilder()
                    .setTitle("MENU")
                    .setDescription("\n")
                    .addAction("New game", () -> {
                        try {
                            resetProgress();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    })
                    .addAction("How to play", () -> message(gui, "HOW TO PLAY", HOW2PLAY))
                    .addAction("About", () -> message(gui, "ABOUT", ABOUT))
                    .setCanCancel(false)
                    .build()
                    .showDialog(gui);
        }
        else {
            new ActionListDialogBuilder()
                    .setTitle("Main Menu")
                    .setDescription("\n")
                    .addAction("Continue", () -> {

                    })
                    .addAction("New game", () -> {
                        try {
                            resetProgress();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    })
                    .addAction("How to play", () -> message(gui, "HOW TO PLAY", HOW2PLAY))
                    .addAction("About", () -> message(gui, "ABOUT", ABOUT))
                    .setCanCancel(false)
                    .build()
                    .showDialog(gui);
        }
    }

    // helper messageDialogBuilder to display custom message
    private static void message(WindowBasedTextGUI gui, String title, String message) {
        new MessageDialogBuilder()
                .setTitle(title)
                .setText("\n" + message)
                .addButton(MessageDialogButton.OK)
                .build()
                .showDialog(gui);
        try {
            menu(gui);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // file I/O helpers ////////////////////////////////////////////////////////////////////////////////////////////////
    private static void resetProgress() throws Exception {
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

    public static String getLevel() throws Exception {
        return Files.readAllLines(Paths.get(String.valueOf(file))).get(0);
    }

    public static String getBitcoin() throws Exception {
        return Files.readAllLines(Paths.get(String.valueOf(file))).get(1);
    }

    // long text constants /////////////////////////////////////////////////////////////////////////////////////////////
    private final static String HOW2PLAY =
            "You are playing the role of a black hat hacker that cracks passwords" + "\n" +
            "from profiles in exchange for \u20BFitcoin." +"\n\n" +
            "Profiles are randomly generated with an associated password that" + "\n" +
            "is related to the profile in some way, for instance:" + "\n\n" +
            "First name: John" + "\n" +
            "Last name: Smith" + "\n" +
            "DoB: 15/07/1988" + "\n\n" +
            "Password: j0hnSm1th88" + "\n\n" +
            "Passwords will become more challenging to crack as you progress" + "\n" +
            "but will yield a higher \u20BF reward. Profiles can be requested" + "\n" +
            "in exchange for a small deposit fee which is lost if you choose to" + "\n" +
            "delete the request, and up to 3 profiles can be held at a time." + "\n\n" +
            "\u20BF can be used to purchase essential cracking tools that will" + "\n" +
            "aid you in cracking passwords.";


    private final static String ABOUT =
            "This artifact is part of an undergraduate research project led by" + "\n" +
            "Theo Koorehpaz at the University of Sheffield. The objective is to" + "\n" +
            "increase password security and awareness by the means of Gamification." + "\n\n" +
            "Supervisor: Ramsay Taylor" + "\n" +
            "Module code: COMxxx" + "\n" +
            "Department of Computer Science";

    private final static String ACKNOWLEDGEMENT =
            "This is a password cracking game." + "\n" +
            "All of the techniques used in this game" + "\n" +
            "have been crafted to accurately resemble" + "\n" +
            "sophisticated cracking algorithms and" + "\n" +
            "how they can be used against legitimate" + "\n" +
            "password data sets." + "\n" +
            "\n" + "The objective of this game is to increase" + "\n" +
            "password security and awareness and not to " + "\n" +
            "promote hacking in any way." + "\n" +
            "\n" + "Enjoy!";
}