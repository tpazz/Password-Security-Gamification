package ui;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

import static ui.Helper.getLevel;
import static ui.Helper.resetProgress;

public class Menu {

    public void startScreen(WindowBasedTextGUI textGUI) throws Exception {
        message(textGUI, "DISCLAIMER", DISCLAIMER);
        menu(textGUI);
    }

    // long text constants /////////////////////////////////////////////////////////////////////////////////////////////
    private final String HOW2PLAY =
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

    private final String ABOUT =
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

    private final String DISCLAIMER =
            "This is an educational simulation of \n" +
            "password cracking and does not promote \n" +
            "computer or information misuse in any way.";

    private void menu(WindowBasedTextGUI gui) throws Exception {
        if (getLevel().equals("0")) { // fresh start or selecting 'New Game' will omit 'Continue' option
            new ActionListDialogBuilder()
                    .setTitle("MENU")
                    .setDescription("\n")
                    .addAction("New game", () -> {
                        try {
                            resetProgress();
                            gui.getScreen().close();
                            Profile profile = new Profile();
                            profile.start();
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
    private void message(WindowBasedTextGUI gui, String title, String message) {
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
}
