package Generate;

import Player.Player;
import algorithms.MD5;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class Generate extends Generate_helper {

    private final String INTRO = "INTRO";
    private final String BEGGINER = "BEGGINER";
    private final String INTERMEDIATE = "INTERMEDIATE";
    private final String DIFFICULT = "HARD";
    private final String FINALLEVEL = "FINAL LEVEL";

    private int rank;
    private float reward;
    private boolean salt;
    private boolean pepper;
    private String algorithm;
    private String dictionary;
    private ArrayList<String> description;
    private String plainTextPassword;
    private String hashedPassword;
    private String difficulty;
    private String firstName;
    private String lastName;

    public Generate(Player p) throws Exception {
        this.rank = p.getRank();
        // introductory levels
        if      (rank == 1)  genTrivialLevel(LVL1DESC);
        else if (rank == 2)  genTrivialLevel(LVL2DESC);
        else if (rank == 3)  genTrivialLevel(LVL3DESC);
        // easy levels
        else if (rank == 4)  genEasyLevel("num_brute", LVL4ESC, 9999);
        else if (rank == 5)  genEasyLevel("num_brute", LVL5ESC, 999999);
        else if (rank == 6)  genEasyLevel("num_brute", LVL6ESC, 99999999);

        else if (rank == 7)  genEasyLevell("alpha-brute", LVL7ESC, "zzzzz");
        //else if (rank < 13)  genEasyLevel("alpha-num_brute", LVL4ESC);
        // medium levels
        else if (rank < 16)  genMediumLevel("dictionary"); // hash tables
        else if (rank < 19) { // introduction of salts makes hash tables redundant
            setSalt(true);
            setPepper(false);
            genMediumLevel("combinator_dic");
        }
        else if (rank < 22) { // introduction of peppers
            setSalt(false);
            setPepper(true);
            genMediumLevel("hybrid_dic");
        }
        // hard levels
        else if (rank < 25) { // salt + pepper
            setSalt(true);
            setPepper(true);
            genHardLevel("keyword");
        }
        // final (very hard) level
        else if (rank == 25) {
            genLastLevel(null);
        }
    }

    private float generateReward(float min, float max) {
        Random r = new Random();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(3);
        return Float.valueOf(df.format(min + r.nextFloat() * (max - min)));
    }

    private void genTrivialLevel(ArrayList<String> lvldesc) {
        setDifficulty(INTRO);
        setDescription(lvldesc);
        setReward(generateReward(0.5f,1.0f));
    }

    private void genEasyLevel(String alg, ArrayList<String> lvldesc, int r) {
        Random rnd = new Random();
        setDescription(lvldesc);
        setDifficulty(BEGGINER);
        setAlgorithm(alg);
        setPlainTextPassword(String.valueOf(rnd.nextInt(r)));
        setReward(generateReward(0.6f,1.2f));
    }

    private void genEasyLevell(String alg, ArrayList<String> lvldesc, String pwd) {
        Random rnd = new Random();
        setDescription(lvldesc);
        setDifficulty(BEGGINER);
        setAlgorithm(alg);
        setPlainTextPassword(pwd);
        setReward(generateReward(0.6f,1.2f));
    }

    private void genMediumLevel(String alg) {
        Random rand = null;
        setDescription(LVL1DESC);
        setAlgorithm(null);
        setDictionary(null);
        setFirstName("John");
        setLastName("Smith");
        setPlainTextPassword(String.valueOf(1000000001));
        setPepper(false);
        setSalt(false);
        setReward(Float.valueOf(getPlainTextPassword()));
    }

    private void genHardLevel(String alg) {
        Random rand = null;
        setDescription(LVL1DESC);
        setAlgorithm(null);
        setDictionary(null);
        setFirstName("John");
        setLastName("Smith");
        setPlainTextPassword(String.valueOf(1000000001));
        setPepper(false);
        setSalt(false);
        setReward(Float.valueOf(getPlainTextPassword()));
    }

    private void genLastLevel(String alg) {
        Random rand = null;
        setDescription(LVL1DESC);
        setAlgorithm(null);
        setDictionary(null);
        setFirstName("John");
        setLastName("Smith");
        setPlainTextPassword(String.valueOf(1000000001));
        setPepper(false);
        setSalt(false);
        setReward(Float.valueOf(getPlainTextPassword()));
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public float getReward() {
        return reward;
    }

    public void setReward(float reward) {
        this.reward = reward;
    }

    public boolean isSalt() {
        return salt;
    }

    public void setSalt(boolean salt) {
        this.salt = salt;
    }

    public boolean isPepper() {
        return pepper;
    }

    public void setPepper(boolean pepper) {
        this.pepper = pepper;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getDictionary() {
        return dictionary;
    }

    public void setDictionary(String dictionary) {
        this.dictionary = dictionary;
    }

    public ArrayList<String> getDescription() {
        return description;
    }

    public void setDescription(ArrayList<String> description) {
        this.description = description;
    }

    public String getPlainTextPassword() {
        return plainTextPassword;
    }

    public void setPlainTextPassword(String plainTextPassword) {
        this.plainTextPassword = plainTextPassword;
    }

    public String getHashedPassword() {
        return MD5.getHashPassword(plainTextPassword);
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
