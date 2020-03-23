package Generate;

import Player.Player;
import algorithms.MD5;

import java.util.Random;

public class Generate extends Generate_helper {

    private int rank;
    private float reward;
    private boolean salt;
    private boolean pepper;
    private String algorithm;
    private String dictionary;
    private String description;
    private String plainTextPassword;
    private String hashedPassword;
    private String firstName;
    private String lastName;

    public Generate(Player p) throws Exception {
        this.rank = p.getRank();
        switch (rank) {
            case 0:
                genLevel1();
            break;
            case 1:
                genLevel2();
            break;
        }
    }

    private int genRand() {
        Random rand = new Random();
        return rand.nextInt(10000);
    }
    private void genLevel2() {
        Random rand = null;
        setDescription(LVL1DESC);
        setAlgorithm(null);
        setDictionary(null);
        setFirstName("test");
        setLastName("nsdfsef");
        setPlainTextPassword(String.valueOf(99));
        setPepper(false);
        setSalt(false);
        setReward(Float.valueOf(getPlainTextPassword()));
    }

    private void genLevel1() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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
