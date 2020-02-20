import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

public class Main {

    static File file = new File("src\\main\\java\\progress.txt");

    public static void main(String[] args) throws Exception {
        // Setup terminal and screen layers
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        BasicWindow window = new BasicWindow();

        final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);

        new MessageDialogBuilder()
                .setTitle("Acknowledgement")
                .setText("\n" + "This is a password cracking game." + "\n" +
                         "All of the techniques used in this game" + "\n" +
                         "have been crafted to accurately resemble" + "\n" +
                         "sophisticated cracking algorithms and" + "\n" +
                         "how they can be used against legitimate" + "\n" +
                         "password data sets." + "\n" +
                         "\n" + "The objective of this game is to increase" + "\n" +
                         "password security and awareness and not to " + "\n" +
                         "promote hacking in any way." + "\n" +
                         "\n" + "Enjoy!")
                .addButton(MessageDialogButton.OK)
                .build()
                .showDialog(textGUI);

        //clearFile();
        //writeFile(8,9.12f);
        newGameMenu(textGUI);
    }

    private static void newGameMenu(WindowBasedTextGUI gui) {
        new ActionListDialogBuilder()
                .setTitle("Main Menu")
                .setDescription("\n")
                .addAction("New Game", new Runnable() {
                    @Override
                    public void run() {
                        // Do 1st thing...
                    }
                })
                .addAction("How to play", new Runnable() {
                    @Override
                    public void run() {
                        // Do 2nd thing...
                    }
                })
                .build()
                .showDialog(gui);
    }

    private static void contGameMenu(WindowBasedTextGUI gui) {
        new ActionListDialogBuilder()
                .setTitle("Main Menu")
                .setDescription("\n")
                .addAction("Continue", new Runnable() {
                    @Override
                    public void run() {
                        // Do 1st thing...
                    }
                })
                .addAction("New game", new Runnable() {
                    @Override
                    public void run() {
                        // Do 2nd thing...
                    }
                })
                .addAction("How to play", new Runnable() {
                    @Override
                    public void run() {
                        // Do 3rd thing...
                    }
                })
                .build()
                .showDialog(gui);
    }

    private static void clearFile() throws Exception {
        File file = new File("src\\main\\java\\progress.txt");
        PrintWriter writer = new PrintWriter(file);
        writer.print("");
        writer.close();
    }

    private static void writeFile(int i, float f) throws Exception {
        String level, bitcoin;
        if (file.length() == 0) {
            FileOutputStream fos = new FileOutputStream(file);
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeInt(i);
            dos.writeFloat(f);
        }
        else {
            BufferedReader br = new BufferedReader(new FileReader(file));
            level = br.readLine();
            bitcoin = br.readLine();
            clearFile();
            FileOutputStream fos = new FileOutputStream(file);
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeInt(Integer.parseInt(level) + i);
            dos.writeFloat(Float.parseFloat(bitcoin + f));
        }
    }
}