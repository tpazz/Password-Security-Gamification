package metadata;

import Generate.Generate;
import Player.Player;
import algorithms.Algorithm;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.Symbols;

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
    private float time2;
    private float time3;

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

    private void updateUI() throws Exception {
        player.getScreen().refresh();
    }

    @Override
    public void run() {
        inProgress = true;
        try {
            if (algorithm.getAlgorithm().equals("dic") || algorithm.getAlgorithm().equals("comb_dic") ||
                    algorithm.getAlgorithm().equals("hybrid_dic")) range = algorithm.lineCount();
            else range = algorithm.getRange();

            if (algorithm.isGenCheck() || algorithm.isGenCheckWriteCheck()) {
                while (!algorithm.getComplete()) {
                    player.getGraphics().putString(3, 16, "Generating hash table - ");
                    while (!algorithm.isHashTable()) {
                        frac = (float) (algorithm.getI() / range) * 100;
                        player.getGraphics().putString(27, 16, String.format("%.1f", frac) + "% " +
                                algorithm.getStartTime11() + "s", SGR.ITALIC);
                        updateUI();
                    }
                    time = algorithm.getStartTime11();
                    player.getGraphics().putString(3, 16, "Generating hash table - ", SGR.BOLD);
                    player.getGraphics().putString(27,16, "100.0% " + time + "s", SGR.BOLD, SGR.ITALIC);
                    player.getGraphics().putString(43, 16, "[DONE]", SGR.BOLD);
                    player.getGraphics().putString(3, 17, "Looking up hash");
                    updateUI();
                    // generate
                    while (!algorithm.isLookup()) {}
                    displaySuccFail();

                    if (!algorithm.getResult().equals("No match!") && algorithm.isGenCheckWriteCheck()) {
                        while (!algorithm.isWriteTable()) {
                            player.getGraphics().putString(3, 18, "Writing hash table in memory - " + algorithm.getStartTime33() + "s");
                            player.getScreen().refresh();
                        }
                        time2 = (algorithm.getStartTime33());
                        displayAdvance();
                    }
                    player.getScreen().refresh();
                }
            } else if (algorithm.isFetchCheck()) {
                player.getGraphics().putString(3,16, "Fetching hash table -");
                while (!algorithm.isFetched()) {
                    player.getGraphics().putString(25,16, algorithm.getStartTime11() + "s", SGR.ITALIC);
                    updateUI();
                }
                time = algorithm.getStartTime11();
                displayFetchCheck();
                while (!algorithm.isLookup()) {}
                displaySuccFail();
                updateUI();
            }
            else {
                if (algorithm.getAlgorithm().equals("num_brute")) range += 1;
                startTime = System.currentTimeMillis();
                staticFields();

                while (!algorithm.getComplete()) {
                    time = (float) (System.currentTimeMillis() - startTime) / 1000;
                    player.getGraphics().putString(56, 16, String.valueOf(time));
                    if (algorithm.isSalt())
                        player.getGraphics().putString(20, 15, algorithm.getSalty() + algorithm.getCurrentPlainText() + " -> " + algorithm.getCurrentHash() + "                       ");
                    else
                        player.getGraphics().putString(20, 15, algorithm.getCurrentPlainText() + " -> " + algorithm.getCurrentHash() + "                       ");
                    frac = (float) (algorithm.getI() / range);
                    if (frac >= step) {
                        step += 0.02f;
                        player.getGraphics().putString(10 + p, 17, String.valueOf(Symbols.SOLID_SQUARE));
                        p++;
                    }
                    if (Math.round(System.currentTimeMillis() - startTime) % 1000 == 0) {
                        player.getGraphics().putString(20, 16, String.format("%,.0f", algorithm.getI() - tmp) + "        ");
                        tmp = algorithm.getI();
                    }
                    display();
                    player.getScreen().refresh();
                }
                if (algorithm.getResult().equals("No match!")) fillProgress();
                else success();
            }
        } catch (Exception e) {
                System.out.println(e);
            }
        inProgress = false;
    }

    public void success() throws IOException {
        frac = (float) (algorithm.getI()/range);
        player.getGraphics().drawLine(10,17, 10 + (int) Math.floor(frac*50), 17, Symbols.SOLID_SQUARE);
        player.getGraphics().putString(62,17, "Match!", SGR.BLINK, SGR.BOLD);
        player.getGraphics().putString(56,16, String.valueOf(time));
        player.getGraphics().putString(24,18,String.valueOf(algorithm.getI()+1) + " / " + new DecimalFormat("#").format(range),SGR.ITALIC);
        player.getGraphics().putString(55,18, String.format("%.1f",frac*100) + "%",SGR.ITALIC);
        player.getGraphics().putString(20, 16, String.format("%,.0f",algorithm.getI() - tmp) + "   ");
        if (generate.getSalt() != null) player.getGraphics().putString(20,15,  generate.getSalt() + generate.getPlainTextPassword() + " -> " + generate.getHashedPassword() + "                          ", SGR.BOLD);
        else player.getGraphics().putString(20,15,  generate.getPlainTextPassword() + " -> " + generate.getHashedPassword() + "                          ", SGR.BOLD);
        player.getGraphics().putString(50,22, "Type the password to advance", SGR.BOLD, SGR.ITALIC);
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
        if (algorithm.isSalt()) player.getGraphics().putString(20,15,algorithm.getSalty() + algorithm.getCurrentPlainText() + " -> " + algorithm.getCurrentHash() + "                          ");
        else player.getGraphics().putString(20,15, algorithm.getCurrentPlainText() + " -> " + algorithm.getCurrentHash() + "                          ");
        player.getGraphics().putString(62,17, algorithm.getResult(), SGR.BLINK, SGR.BOLD);
        player.getGraphics().putString(24,18,new DecimalFormat("#").format(range) + " / " + new DecimalFormat("#").format(range),SGR.ITALIC);
        player.getGraphics().putString(55,18, "100.0%", SGR.ITALIC);
        player.getScreen().refresh();
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    public void displayGenCheckWrite() throws Exception {
        player.getGraphics().putString(3, 16, "Generating hash table - ", SGR.BOLD);
        player.getGraphics().putString(27,16, "100.0% " + time + "s  ", SGR.BOLD, SGR.ITALIC);
        player.getGraphics().putString(43, 16, "[DONE]", SGR.BOLD);
        player.getGraphics().putString(3, 17, "Looking up hash", SGR.BOLD);
        displaySuccFail();
        if (!algorithm.getResult().equals("No match!") && algorithm.isGenCheckWriteCheck()) {
            displayAdvance();
        }
        player.getScreen().refresh();
    }

    public void displayFetchCheck() throws Exception {
        player.getGraphics().putString(3,16, "Fetching hash table -", SGR.BOLD);
        player.getGraphics().putString(25,16, time + "s  ", SGR.BOLD, SGR.ITALIC);
        player.getGraphics().putString(43, 16, "[DONE]", SGR.BOLD);
        player.getGraphics().putString(3,17, "Looking up hash", SGR.BOLD);
        displaySuccFail();
        updateUI();
    }
    public void displaySuccFail() {
        player.getGraphics().putString(3,17, "Looking up hash", SGR.BOLD);
        if (!algorithm.getResult().equals("No match!")) {
            player.getGraphics().putString(43, 17, "[SUCCESS] -> ", SGR.BOLD, SGR.BLINK);
            player.getGraphics().putString(56, 17,  generate.getPlainTextPassword(), SGR.BOLD);
            if (!algorithm.isGenCheckWriteCheck()) player.getGraphics().putString(50,22, "Type the password to advance", SGR.BOLD, SGR.ITALIC);
        } else if (algorithm.getResult().equals("No match!")) {
            player.getGraphics().putString(43, 17, "[FAILED]", SGR.BOLD, SGR.BLINK);
        }
    }

    public void displayAdvance() {
        player.getGraphics().putString(3, 18, "Writing hash table in memory -", SGR.BOLD);
        player.getGraphics().putString(43, 18, "[DONE]", SGR.BOLD);
        player.getGraphics().putString(34, 18, time2 + "s  ", SGR.BOLD, SGR.ITALIC);
        player.getGraphics().putString(50,22, "Type the password to advance", SGR.BOLD, SGR.ITALIC);
    }
}
