package ui;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Helper {

    // file R/W methods for progress ///////////////////////////////////////////////////////////////////////////////////
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

    public static String getLevel() throws Exception {
        return Files.readAllLines(Paths.get(String.valueOf(file))).get(0);
    }

    public static String getBitcoin() throws Exception {
        return Files.readAllLines(Paths.get(String.valueOf(file))).get(1);
    }
}
