package metadata;

import Player.Player;
import algorithms.Algorithm;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.Symbols;
import java.io.IOException;
import java.text.DecimalFormat;

public class Progress implements Runnable {

    Algorithm algorithm;
    Player player;
    private int p = 0;
    private float step = 0.02f;
    float frac;
    double range;
    private float tmp = 0;

    public Progress(Algorithm a, Player p) {
        this.algorithm = a;
        this.player = p;
    }

    private void display() {
        player.getGraphics().putString(24,18,String.valueOf(algorithm.getI()) + " / " +
                new DecimalFormat("#").format(range+1));
        player.getGraphics().putString(55,18, String.format("%.1f",frac*100) + "%");
    }

    @Override
    public void run() {
        player.getGraphics().putString(10,15, "Password: ");
        player.getGraphics().putString(10,16, "Hashes/s: ");
        player.getGraphics().putString(40,16, "Time elapsed/s: ");
        player.getGraphics().putString(10,18, "Search-space: ");
        range = algorithm.getRange();
        long startTime = System.currentTimeMillis();
        try {
            while (!algorithm.getComplete()) {
//                System.out.println(algorithm.getI()); // 9999
//                System.out.println(algorithm.getResult());
//                System.out.println(algorithm.getRange());
//                System.out.println(algorithm.getCurrentHash());
//                System.out.println(algorithm.getComplete());
                player.getGraphics().putString(56,16, String.valueOf((float)(System.currentTimeMillis() - startTime)/1000));
                player.getGraphics().putString(20,15, algorithm.getCurrentPlainText() + " -> " + algorithm.getCurrentHash());
                frac = (float) (algorithm.getI()/range);
                if (frac >= step)  {
                    step += 0.02f;
                    player.getGraphics().putString(10+p,17, String.valueOf(Symbols.SOLID_SQUARE));
                    p++; }
                if (Math.round(System.currentTimeMillis() - startTime) % 1000 == 0) {
                    player.getGraphics().putString(20, 16, String.format("%,.0f",algorithm.getI() - tmp) + "   ");
                    tmp = algorithm.getI(); }
                display();
                player.getScreen().refresh();
            }
            if (algorithm.getResult().equals("No match!")) fillProgress();
            else success();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void success() throws IOException {
        frac = (float) (algorithm.getI()/range);
        player.getGraphics().putString(62,17, "Match!", SGR.BLINK);
        player.getGraphics().putString(24,18,String.valueOf(algorithm.getI()) + " / " + new DecimalFormat("#").format(range+1));
        player.getGraphics().putString(55,18, String.format("%.1f",frac*100) + "%");
        player.getGraphics().putString(20,15, algorithm.getCurrentPlainText() + " -> " + algorithm.getCurrentHash(), SGR.BOLD);
        player.getGraphics().putString(45,22, "Type 'request' for next challenge");
        player.getScreen().refresh();
    }

    private void fillProgress() throws IOException {
        System.out.println("here");
        for (int i = 0; i <= 50; i++) {
            player.getGraphics().putString(10+i, 17, String.valueOf(Symbols.SOLID_SQUARE));
        }
        player.getGraphics().putString(20,15, algorithm.getCurrentPlainText() + " -> " + algorithm.getCurrentHash());
        player.getGraphics().putString(62,17, algorithm.getResult(), SGR.BLINK);
        player.getGraphics().putString(24,18,String.valueOf(algorithm.getI()) + " / " + new DecimalFormat("#").format(range+1));
        player.getGraphics().putString(55,18, "100.0%");
        player.getScreen().refresh();
    }
}
