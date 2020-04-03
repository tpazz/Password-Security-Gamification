package metadata;

import Generate.Generate;
import Player.Player;
import algorithms.Algorithm;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.text.DecimalFormat;

public class Progress implements Runnable {

    public boolean inProgress = false;
    Algorithm algorithm;
    Player player;
    Generate generate;
    private int p = 0;
    private float step = 0.02f;
    float frac;
    double range;
    private float tmp = 0;
    private long startTime;
    private float time;

    public Progress(Algorithm a, Player p, Generate g) {
        this.algorithm = a;
        this.player = p;
        this.generate = g;
    }

    public void display() {
        player.getGraphics().putString(24,18,String.valueOf(algorithm.getI()+1) + " / " +
                new DecimalFormat("#").format(range), SGR.ITALIC);
        player.getGraphics().putString(55,18, String.format("%.1f",frac*100) + "%", SGR.ITALIC);
    }

    @Override
    public void run() {
        inProgress = true;
        range = algorithm.getRange();
        if (algorithm.getAlgorithm().equals("num_brute")) range += 1;
        startTime = System.currentTimeMillis();
        staticFields();
        try {
            while (!algorithm.getComplete()) {
                time = (float) (System.currentTimeMillis() - startTime)/1000;
                player.getGraphics().putString(56,16, String.valueOf(time));
                player.getGraphics().putString(20,15, algorithm.getCurrentPlainText() + " -> " + algorithm.getCurrentHash());
                frac = (float) (algorithm.getI()/range);
                if (frac >= step)  {
                    step += 0.02f;
                    player.getGraphics().putString(10+p,17, String.valueOf(Symbols.SOLID_SQUARE));
                    p++; }
                if (Math.round(System.currentTimeMillis() - startTime) % 1000 == 0) {
                    player.getGraphics().putString(20, 16, String.format("%,.0f",algorithm.getI() - tmp) + "        ");
                    tmp = algorithm.getI(); }
                display();
                player.getScreen().refresh();
            }
            if (algorithm.getResult().equals("No match!")) fillProgress();
            else success();
        } catch (Exception e) {
            System.out.println(e);
        }
        inProgress = false;
    }

    public void success() throws IOException {
        frac = (float) (algorithm.getI()/range);
        player.getGraphics().drawLine(10,17, 10 + (int) Math.floor(frac*50), 17, Symbols.SOLID_SQUARE);
        player.getGraphics().putString(62,17, "Match!", SGR.BLINK);
        player.getGraphics().putString(56,16, String.valueOf(time));
        player.getGraphics().putString(24,18,String.valueOf(algorithm.getI()+1) + " / " + new DecimalFormat("#").format(range),SGR.ITALIC);
        player.getGraphics().putString(55,18, String.format("%.1f",frac*100) + "%",SGR.ITALIC);
        player.getGraphics().putString(20, 16, String.format("%,.0f",algorithm.getI() - tmp) + "   ");
        player.getGraphics().putString(20,15,  generate.getPlainTextPassword() + " -> " + generate.getHashedPassword(), SGR.BOLD);
        player.getGraphics().putString(36,22, "Type the password to advance to next level");
        player.getScreen().refresh();
    }

    public void staticFields() {
        player.getGraphics().putString(10,15, "Password: ");
        player.getGraphics().putString(10,16, "Hashes/s: ");
        player.getGraphics().putString(40,16, "Time elapsed/s: ");
        player.getGraphics().putString(10,18, "Search-space: ", SGR.ITALIC);
    }

    public void fillProgress() throws IOException {
        player.getGraphics().drawLine(10,17, 60, 17, Symbols.SOLID_SQUARE);
        player.getGraphics().putString(20, 16, String.format("%,.0f",algorithm.getI() - tmp) + "   ");
        player.getGraphics().putString(56,16, String.valueOf(time));
        player.getGraphics().putString(20,15, algorithm.getCurrentPlainText() + " -> " + algorithm.getCurrentHash());
        player.getGraphics().putString(62,17, algorithm.getResult(), SGR.BLINK);
        player.getGraphics().putString(24,18,new DecimalFormat("#").format(range) + " / " + new DecimalFormat("#").format(range),SGR.ITALIC);
        player.getGraphics().putString(55,18, "100.0%", SGR.ITALIC);
        player.getScreen().refresh();
    }
}
