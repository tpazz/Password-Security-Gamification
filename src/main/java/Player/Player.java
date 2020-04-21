package Player;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Player {

    private File file = new File("src\\main\\java\\player\\Progress.txt");
    private WindowBasedTextGUI windowBasedTextGUI;
    private Terminal terminal;
    private Screen screen;
    private TextGraphics textGraphics;

    public Player(WindowBasedTextGUI gui, Terminal ter, Screen scn, TextGraphics tg) {
        this.windowBasedTextGUI = gui;
        this.terminal = ter;
        this.screen = scn;
        this.textGraphics = tg;
    }

    public TextGraphics getGraphics() {
        return textGraphics;
    }

    public ArrayList<String> getAlgorithms() throws Exception {
        ArrayList<String> items = new ArrayList<>();
        for (int i = 0; i < getPurchaced().length()-6; i++) {
            char c = getPurchaced().charAt(i);
            if (c == '1')
                items.add(getItem(i));
        }
        return items;
    }

    public ArrayList<String> getDictionaries() throws Exception {
        ArrayList<String> items = new ArrayList<>();
        String dics = getPurchaced().substring(5);
        for (int i = 5; i < getPurchaced().length(); i++) {
            char c = dics.charAt(i-5);
            if (c == '1')
                items.add(getItem(i));
        }
        return items;
    }

    public ArrayList<String> getAllItmes() throws Exception {
        ArrayList<String> items = new ArrayList<>();
        items.addAll(getAlgorithms());
        items.addAll(getDictionaries());
        return items;
    }

    private String getItem(int i) {
        String item = null;
        switch (i) {
            case 0:
                item = "num_brute";
                break;
            case 1:
                item = "alpha_brute";
                break;
            case 2:
                item = "dic";
                break;
            case 3:
                item = "comb_dic";
                break;
            case 4:
                item = "hybrid_dic";
                break;
            case 5:
                item = "english";
                break;
            case 6:
                item = "fnames";
                break;
            case 7:
                item = "snames";
                break;
            case 8:
                item = "10kmc";
                break;
            case 9:
                item = "hobbies";
                break;
            case 10:
                item = "jobs";
                break;
        }
        return item;
    }

    public void resetProgress() throws Exception {
        FileWriter fw = new FileWriter(file);
        fw.write("1\n0.0\n0000000000\nGREEN\n00000");
        fw.close();
    }

    public void writeProgress(int i, float f, String a, String c, String h) throws Exception {
        int level = i + getRank();
        float bitcoin = Math.round((f + getBitcoin()) * 1000f) / 1000f;
        FileWriter fw = new FileWriter(file);
        fw.write(Integer.toString(level) + "\n" + Float.toString(bitcoin) + "\n" + a + "\n" + c + "\n" + h);
        fw.close();
    }

    public int getRank() throws Exception {
        String tmp =  Files.readAllLines(Paths.get(String.valueOf(file))).get(0);
        return Integer.valueOf(tmp);
    }

    public float getBitcoin() throws Exception {
        String tmp = Files.readAllLines(Paths.get(String.valueOf(file))).get(1);
        return Float.valueOf(tmp);
    }

    public String getPurchaced() throws Exception {
        return Files.readAllLines(Paths.get(String.valueOf(file))).get(2);
    }

    public String getColour() throws Exception {
        return Files.readAllLines(Paths.get(String.valueOf(file))).get(3);
    }

    public String getAchievements() throws Exception {
        return Files.readAllLines(Paths.get(String.valueOf(file))).get(4);
    }

    public WindowBasedTextGUI getGUI() {
        return windowBasedTextGUI;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public Screen getScreen() {
        return screen;
    }

    public void clearWindow() {
        windowBasedTextGUI.removeWindow(windowBasedTextGUI.getActiveWindow());
    }
}