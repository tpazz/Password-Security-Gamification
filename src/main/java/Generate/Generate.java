package Generate;

import Player.Player;
import algorithms.Algorithm;
import algorithms.MD5;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Generate extends Generate_helper {

    private int rank;
    private int passwordLength;
    private int birthDay;
    private int birthMonth;
    private int birthYear;
    private float reward;
    private String salt;
    private String profession;
    private String hobby;
    private String pepper;
    private String algorithm;
    private String dictionary;
    private ArrayList<String> description;
    private String plainTextPassword;
    private ArrayList<String> required = new ArrayList<>();
    private ArrayList<String> profileDescription = new ArrayList<>();
    private String title;
    private String firstName;
    private String lastName;

    public Generate(Player p) throws Exception {
        this.rank = p.getRank();
        if (rank == 1) {
            setTitle("Written passwords");
            setDescription(LVL1DESC);
            setReward(generateReward(0.5f,0.6f));
        }
        else if (rank == 2) {
            setTitle("Plain-text passwords");
            setDescription(LVL2DESC);
            setReward(generateReward(0.6f,0.7f));
        }
        else if (rank == 3) {
            setTitle(INTRO);
            setDescription(LVL3DESC);
            setReward(generateReward(0.7f,0.8f));
        }
        else if (rank == 4) {
            Random r = new Random();
            setTitle("Hash functions");
            setDescription(LVL4ESC);
            required.add("num_brute");
            setRequired(required);
            setAlgorithm(NUMBRUTE);
            setPlainTextPassword(String.valueOf(r.nextInt(9999)));
            setReward(generateReward(0.8f,1.0f));
        }
        // easy levels
        else if (rank == 5) {
            Random r = new Random();
            setTitle("Brute-force 1");
            required.add("num_brute");
            setRequired(required);
            setDescription(LVL5ESC);
            setAlgorithm(NUMBRUTE);
            setPlainTextPassword(String.valueOf(r.nextInt(99999)));
            setReward(generateReward(0.9f,1.1f));
        }

        else if (rank == 6) {
            Random r = new Random();
            setTitle("Brute-force 2");
            setDescription(LVL6ESC);
            required.add("num_brute");
            setRequired(required);
            setAlgorithm(NUMBRUTE);
            setPlainTextPassword(String.valueOf(r.nextInt(999999)));
            setReward(generateReward(1.0f,1.2f));
        }

        else if (rank == 7) {
            setTitle("Brute-force 3");
            setDescription(LVL7ESC);
            required.add("alpha_brute");
            setRequired(required);
            setAlgorithm(ALPHABRUTE);
            setPlainTextPassword(generateCharSet(4,1));
            setReward(generateReward(1.1f,1.4f));
        }

        else if (rank == 8) {
            setTitle("Brute-force 4");
            setDescription(LVL8ESC);
            required.add("alpha_brute");
            setRequired(required);
            setAlgorithm(ALPHABRUTE);
            setPlainTextPassword(generateCharSet(3, 2));
            setReward(generateReward(1.2f,1.5f));
        }

        else if (rank == 9) {
            setTitle("Brute-force 5");
            setDescription(LVL9ESC);
            required.add("alpha_brute");
            setRequired(required);
            setAlgorithm(ALPHABRUTE);
            setPlainTextPassword(generateCharSet(4, 2));
            setReward(generateReward(1.3f,1.6f));
        }

        else if (rank == 10) { // english
            setTitle("Dictionary attack 1");
            setDescription(LVL10ESC);
            required.add("dic");
            required.add("english");
            setRequired(required);
            setAlgorithm(DICTIONARY);
            setPlainTextPassword(getRandPassword("english"));
            setReward(generateReward(1.4f,1.8f));
        }
        else if (rank == 11) {
            setTitle("Hash tables 1");
            setDescription(LVL11ESC);
            required.add("dic");
            required.add("english");
            setRequired(required);
            setAlgorithm(DICTIONARY);
            setPlainTextPassword(getRandPassword("english"));
            setReward(generateReward(1.5f,1.9f));
        }
        else if (rank == 12) {
            setTitle("Hash tables 2");
            setDescription(LVL12ESC);
            required.add("dic");
            required.add("english");
            setRequired(required);
            setAlgorithm(DICTIONARY);
            setPlainTextPassword(getRandPassword("english"));
            setReward(generateReward(1.6f,2.0f));
        }
        else if (rank == 13) {
            setSalt(generateCharSet(4,2));
            setTitle("Salts");
            setDescription(LVL13ESC);
            required.add("dic");
            required.add("english");
            setRequired(required);
            setAlgorithm(DICTIONARY);
            setPlainTextPassword(getRandPassword("english"));
            setReward(generateReward(1.7f,2.1f));
        }
        else if (rank == 14) { // first + surnames
            setSalt(generateCharSet(4,2));
            setTitle("Dictionary attack 2");
            setDescription(LVL14ESC);
            required.add("comb_dic");
            required.add("fnames");
            required.add("snames");
            setRequired(required);
            setAlgorithm(COMBINATOR);
            setPlainTextPassword(getRandPassword("fnames") + getRandPassword("snames"));
            setReward(generateReward(1.8f,2.3f));
        }
        else if (rank == 15) {
            Random r = new Random();
            setPepper(String.valueOf(r.nextInt(9)));
            setTitle("Peppers 1");
            setDescription(LVL15ESC);
            required.add("comb_dic");
            required.add("fnames");
            required.add("snames");
            setRequired(required);
            setAlgorithm(COMBINATOR);
            setPlainTextPassword(getRandPassword("fnames") + getRandPassword("snames"));
            setReward(generateReward(1.9f,2.4f));
        }
        else if (rank == 16) {
            setPepper(generateCharSet(1,2));
            setTitle("Peppers 2");
            setDescription(LVL16ESC);
            required.add("comb_dic");
            required.add("fnames");
            required.add("snames");
            setRequired(required);
            setAlgorithm(COMBINATOR);
            setPlainTextPassword(getRandPassword("fnames") + getRandPassword("snames"));
            setReward(generateReward(2.0f,2.5f));
        }
        else if (rank == 17) { // 10k
            Random r = new Random();
            setTitle("Dictionary attack 3");
            setDescription(LVL17ESC);
            required.add("hybrid_dic");
            required.add("10kmc");
            setRequired(required);
            setAlgorithm(HYBRID);
            setPlainTextPassword(getRandPassword("10kmc") + String.valueOf(r.nextInt(2000)));
            setReward(generateReward(2.1f,2.7f));
        }
        else if (rank == 18) { // 10k
            Random r = new Random();
            setPepper(generateCharSet(1,2));
            setTitle("Dictionary attack 4");
            required.add("hybrid_dic");
            required.add("10kmc");
            setRequired(required);
            setDescription(LVL18ESC);
            setAlgorithm(HYBRID);
            setPlainTextPassword(getRandPassword("10kmc") + String.valueOf(r.nextInt(2000)) + isPepper());
            setReward(generateReward(2.2f,2.8f));
        }
        else if (rank == 19) { // 10k
            Random r = new Random();
            setPepper(generateCharSet(1,2));
            setSalt(generateCharSet(4,2));
            setTitle("Salt & Pepper");
            required.add("hybrid_dic");
            required.add("10kmc");
            setRequired(required);
            setDescription(LVL19ESC);
            setAlgorithm(HYBRID);
            setPlainTextPassword(getRandPassword("10kmc") + String.valueOf(r.nextInt(100)) + isPepper());
            setReward(generateReward(2.3f,2.9f));
        }
        else if (rank == 20) { // num_brute
            required.add("hobbies");
            required.add("jobs");
            setRequired(required);
            generateProfileTemplate();
            setDescription(profileDescription);
            setPlainTextPassword(String.valueOf(getBirthDay()) + String.valueOf(getBirthMonth()) + String.valueOf(getBirthYear()));
            setPasswordLength(getPlainTextPassword().length());
            profileDescription.add("> Password length: " + getPasswordLength());
        }
        else if (rank == 21) { // alpha_brute
            generateProfileTemplate();
            setDescription(profileDescription);
            setPlainTextPassword(getFirstName().substring(0,1) + getLastName().substring(0,1) + getProfession().substring(0,1) + String.valueOf(getBirthYear()).substring(2,4));
            setPasswordLength(getPlainTextPassword().length());
            profileDescription.add("> Password length: " + getPasswordLength());
        }
        else if (rank == 22) { // pepper dic
            generateProfileTemplate();
            setPepper(generateCharSet(1,2));
            setDescription(profileDescription);
            setPlainTextPassword(getProfession().replaceAll("\\s+","") + isPepper());
            setPasswordLength(getPlainTextPassword().length());
            profileDescription.add("> Password length: " + getPasswordLength());
        }
        else if (rank == 23) { // comb_dic
            generateProfileTemplate();
            setDescription(profileDescription);
            setPlainTextPassword(getLastName() + getProfession().replaceAll("\\s+",""));
            setPasswordLength(getPlainTextPassword().length());
            profileDescription.add("> Password length: " + getPasswordLength());
        }
        else if (rank == 24) { // hybrid_dic
            generateProfileTemplate();
            setDescription(profileDescription);
            setPlainTextPassword(getLastName() + String.valueOf(getBirthYear()));
            setPasswordLength(getPlainTextPassword().length());
            profileDescription.add("> Password length: " + getPasswordLength());
        }
        else if (rank == 25) { // salt + pepper dic
            generateProfileTemplate();
            setSalt(generateCharSet(4,2));
            setPepper(generateCharSet(1,2));
            setDescription(profileDescription);
            setPlainTextPassword(getLastName() + isPepper());
            setPasswordLength(getPlainTextPassword().length());
            profileDescription.add("> Password length: " + getPasswordLength());
        }
        else if (rank == 26) { // comb_dic salt + pepper
            generateProfileTemplate();
            setSalt(generateCharSet(4,2));
            setPepper(generateCharSet(1,2));
            setDescription(profileDescription);
            setPlainTextPassword(getHobby().replaceAll("\\s+","") + getRandPassword("10kmc") + isPepper());
            setPasswordLength(getPlainTextPassword().length());
            profileDescription.add("> Password length: " + getPasswordLength());
        }
        else if (rank == 27) { // hybrid_dic salt
            generateProfileTemplate();
            setSalt(generateCharSet(4,2));
            setDescription(profileDescription);
            setPlainTextPassword(getFirstName() + String.valueOf(getBirthDay()) + String.valueOf(getBirthMonth()));
            setPasswordLength(getPlainTextPassword().length());
            profileDescription.add("> Password length: " + getPasswordLength());
        }
        else if (rank == 28) {
            setDescription(THANKYOU);
        }
        System.out.println(getPlainTextPassword());
    }

    private void generateProfileTemplate() throws Exception {
        setFirstName(getRandPassword("fnames"));
        setLastName(getRandPassword("snames"));
        setHobby(getRandPassword("hobbies"));
        setProfession(getRandPassword("jobs"));
        setReward(generateReward(5.0f,10.0f));
        setBirthDay();
        setBirthMonth();
        setBirthYear();
        profileDescription.add("> Name: " + getFirstName() + " " + getLastName().substring(0,1).toUpperCase() + getLastName().substring(1));
        profileDescription.add("> Hobby: " + getHobby());
        profileDescription.add("> Profession: " + getProfession());
        profileDescription.add("> DOB: " + String.valueOf(getBirthDay() + "/" + getBirthMonth() + "/" + getBirthYear()));
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
        Algorithm tmp = new Algorithm(null,null,null);
        StringBuilder sb = new StringBuilder();
        StringBuilder bs = new StringBuilder();
        String g = randNum();
        int i;
        for (int x = 0; x < sets; x++) {
            i = Integer.valueOf(g.substring(x,x+1));
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


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getReward() {
        return reward;
    }

    public void setReward(float reward) {
        this.reward = reward;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String isPepper() {
        return pepper;
    }

    public void setPepper(String pepper) {
        this.pepper = pepper;
    }

    public ArrayList<String> getRequired() {
        return required;
    }

    public void setRequired(ArrayList<String> required) {
        this.required = required;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public int getPasswordLength() {
        return passwordLength;
    }

    public void setPasswordLength(int passwordLength) {
        this.passwordLength = passwordLength;
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
        if (getSalt() != null) return MD5.getSaltHashPassword(plainTextPassword, getSalt().getBytes());
        else return MD5.getHashPassword(plainTextPassword);
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getBirthDay() {
        return birthDay;
    }

    public int getBirthMonth() {
        return birthMonth;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public void setBirthDay() {
        Random r = new Random();
        this.birthDay = r.nextInt(27) + 1;
    }

    public void setBirthMonth() {
        Random r = new Random();
        this.birthMonth = r.nextInt(11) + 1;
    }

    public void setBirthYear() {
        Random r = new Random();
        this.birthYear = r.nextInt((2020 - 1920) + 1) + 1920;
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
