package Generate;

import Player.Player;
import algorithms.Algorithm;
import algorithms.MD5;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Generate extends Generate_helper {

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
        if (rank == 1) {
            setDifficulty(INTRO);
            setDescription(LVL1DESC);
            setReward(generateReward(0.5f,0.6f));
        }
        else if (rank == 2) {
            setDifficulty(INTRO);
            setDescription(LVL2DESC);
            setReward(generateReward(0.6f,0.7f));
        }
        else if (rank == 3) {
            setDifficulty(INTRO);
            setDescription(LVL3DESC);
            setReward(generateReward(0.7f,0.8f));
        }
        else if (rank == 4) {
            Random r = new Random();
            setDifficulty(BEGGINER);
            setDescription(LVL4ESC);
            setAlgorithm(NUMBRUTE);
            setPlainTextPassword(String.valueOf(r.nextInt(9999)));
            setReward(generateReward(0.8f,1.0f));
        }
        // easy levels
        else if (rank == 5) {
            Random r = new Random();
            setDifficulty(BEGGINER);
            setDescription(LVL5ESC);
            setAlgorithm(NUMBRUTE);
            setPlainTextPassword(String.valueOf(r.nextInt(99999)));
            setReward(generateReward(0.9f,1.2f));
        }
        else if (rank == 6) {
            Random r = new Random();
            setDifficulty(BEGGINER);
            setDescription(LVL6ESC);
            setAlgorithm(NUMBRUTE);
            setPlainTextPassword(String.valueOf(r.nextInt(999999)));
            setReward(generateReward(1.0f,1.3f));

        }
        else if (rank == 7) {
            setDifficulty(BEGGINER);
            setDescription(LVL7ESC);
            setAlgorithm(ALPHABRUTE);
            String pp = generateCharSet(4,1);
            setPlainTextPassword(pp);
            System.out.println(pp);
            setReward(generateReward(1.2f,1.35f));

        }
        else if (rank == 8) {
            setDifficulty(BEGGINER);
            setDescription(LVL8ESC);
            setAlgorithm(ALPHABRUTE);
            String pp = generateCharSet(4, 2);
            System.out.println(pp);
            setPlainTextPassword(pp);
            setReward(generateReward(1.3f,1.5f));
        }
        else if (rank == 9) {
            setDifficulty(BEGGINER);
            setDescription(LVL9ESC);
            setAlgorithm(ALPHABRUTE);
            setPlainTextPassword(generateCharSet(5, 2));
            setReward(generateReward(1.5f,1.7f));
        }
    }

    private float generateReward(float min, float max) {
        Random r = new Random();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(3);
        return Float.valueOf(df.format(min + r.nextFloat() * (max - min)));
    }

    private String randNum() {
        ArrayList<Integer> list = new ArrayList<>();
        StringBuilder tmp = new StringBuilder();
        for (int i=0; i<4; i++) {
            list.add(i);
        }
        System.out.println(list);
        Collections.shuffle(list);
        for (int i = 0; i < 2; i++) {
            tmp.append(list.get(i));
        }
        System.out.println(tmp);
        return tmp.toString();
    }

    private String generateCharSet(int length, int sets) {
        Random r = new Random();
        Algorithm tmp = new Algorithm(null,null);
        StringBuilder sb = new StringBuilder();
        StringBuilder bs = new StringBuilder();
        String g = randNum();
        int i;
        for (int x = 0; x < sets; x++) {
            i = Integer.valueOf(g.substring(x,x+1));
            System.out.println(i);
            switch (i) {
                case 0:
                    sb.append(tmp.LOWERCASE);
                    break;
                case 1:
                    sb.append(tmp.UPPERCASE);
                    break;
                case 2:
                    sb.append(tmp.NUMBERS);
                    break;
                case 3:
                    sb.append(tmp.SPECIAL);
                    break;
            }
        }
        for (int b = 0; b < length; b++) {
            bs.append(sb.charAt(r.nextInt(sb.length())));
        }
        return bs.toString();
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
