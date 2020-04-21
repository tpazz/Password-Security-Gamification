package algorithms;

import Generate.Generate;
import Player.Player;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Hashtable;

public class Algorithm implements Runnable {

    private char[] CHARSET;
    public final char[] LOWERCASE = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    public final char[] UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public final char[] NUMBERS = "0123456789".toCharArray();
    public final char[] SPECIAL = "!@#$%^&*()-_+=~`[]{}|:;<>,.?/".toCharArray();
    public final char[] ALLCHARS = ("abcdefghijklmnopqrstuvwxyzAEIOU0123456789" +
            "!@#$%^&*()-_+=~`[]{}|:;<>,.?/BCDFGHJKLMNPQRSTVWXYZ").toCharArray();

    private String dictionary;
    private String dictionary2;
    private Generate generate;
    private Player player;
    private FileInputStream file1;
    private BufferedReader br1;
    private BufferedReader br2;
    private FileInputStream file2;

    private String command;
    private String hashedPassword;
    private String algorithm;

    private String alpha1;
    private String alpha2;
    private String salt;
    private String match = "No match!";
    private String current = "";
    private String startTime11;
    private String startTime22;
    private String startTime33;

    private boolean fetched = false;
    private boolean genCheckWriteCheck = false;
    private boolean fetchCheck = false;
    private boolean standard = false;
    private boolean hasSalt = false;
    private boolean hasPepper = false;
    private boolean genCheck = false;
    private boolean brute = false;
    private boolean hashTable = false;
    private boolean lookup = false;
    private boolean writeTable = false;

    private int length;
    private int hybridLength;

    private long startTime1;
    private long startTime2;
    private long startTime3;

    private volatile double range;
    private volatile int i = 0;
    private volatile boolean complete = false;
    private volatile String result = "No match!";

    @Override
    public void run() {
        try { execute(); }
        catch (Exception e) { e.printStackTrace(); }
    }

    public Algorithm(Generate g, String c, Player p) {
        this.generate = g;
        this.command = c;
        this.player = p;
    }

    public boolean validate() {
        boolean valid = true;
        try {
            int dlim = command.indexOf(' ');
            algorithm = command.substring(0, dlim);
            int start = algorithm.length();
            String c = "x";
            int i = 0;
            int tmp;
            String dicCommand;
            switch (algorithm) {

                case "num_brute":
                    range = Integer.parseInt(command.substring(start + 1));
                    break;

                case "alpha_brute":
                    alpha1 = String.valueOf(command.substring(start + 1, start + 3));
                    if (command.length() > 16) {
                        alpha2 = String.valueOf(command.substring(start + 4, start + 6));
                        length = Integer.valueOf(command.substring(start + 7));
                    } else length = Integer.valueOf(command.substring(start + 4));

                    switch (alpha1) {
                        case "-l":
                            CHARSET = LOWERCASE;
                            break;
                        case "-u":
                            CHARSET = UPPERCASE;
                            break;
                        case "-n":
                            CHARSET = NUMBERS;
                            break;
                        case "-s":
                            CHARSET = SPECIAL;
                            break;
                        case "-a":
                            if (player.getRank() > 19) CHARSET = ALLCHARS;
                            else valid = false;
                            break;
                    }

                    if (!alpha1.equals(alpha2) && !alpha1.equals("-a") && command.length() > 16) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(CHARSET);
                        switch (alpha2) {
                            case "-l":
                                 sb.append(LOWERCASE);
                                CHARSET = sb.toString().toCharArray();
                                break;
                            case "-u":
                                sb.append(UPPERCASE);
                                CHARSET = sb.toString().toCharArray();
                                break;
                            case "-n":
                                sb.append(NUMBERS);
                                CHARSET = sb.toString().toCharArray();
                                break;
                            case "-s":
                                sb.append(SPECIAL);
                                CHARSET = sb.toString().toCharArray();
                                break;
                        }
                    }
                    if (player.getRank() > 19 && !Arrays.equals(CHARSET, NUMBERS)) {
                        for (int q = 0; q < length; q++) {
                            range += Math.pow(CHARSET.length, length - q);
                        }
                    } else range = Math.pow(CHARSET.length, length);
                    break;

                case "dic":
                    c = "x";
                    while (!c.equals(" ")) { c = command.substring(start+1+i,start+2+i); i++; }
                    dictionary = command.substring(start + 1, start+i).trim();

                    tmp = start + dictionary.length();
                    dicCommand = String.valueOf(command.substring(tmp + 2, tmp +4));

                    if (player.getRank() < 14) {
                        switch (dicCommand) {
                            case "-w":
                                genCheckWriteCheck = true;
                                break;
                            case "-f":
                                fetchCheck = true;
                                break;
                            case "-n":
                                standard = true;
                                break;
                            case "-s":
                                hasSalt = true;
                                break;
                            default:
                                valid = false;
                        }
                        if (hasSalt) salt = command.substring(tmp + dicCommand.length() + 3);
                        if (fetchCheck && !checkTMP()) valid = false;
                    } else {
                        if (dicCommand.equals("-g")) genCheck = true;
                        else hasSaltAndPepper(tmp+2, dicCommand);
                    }
                    if (!checkDictionary(dictionary)) valid = false;
                    else {
                        file1 = new FileInputStream("src\\main\\java\\Dictionaries\\" + dictionary + ".txt");
                        br1 = new BufferedReader(new InputStreamReader(file1));
                    }
                    break;

                case "comb_dic":
                    i = 0;
                    while (!c.equals(" ")) { c = command.substring(start+1+i,start+2+i); i++; }
                    dictionary = command.substring(start + 1, start+i).trim();

                    i = start+dictionary.length()+2;
                    c = "x";
                    while (!c.equals(" ")) { c = command.substring(i,i+1); i++; }
                    dictionary2 = command.substring(start + dictionary.length()+2, i).trim();
                    dicCommand = command.substring(i,i+2);

                    if (!checkDictionary(dictionary) || !checkDictionary(dictionary2)) valid = false;
                    else {
                        file1 = new FileInputStream("src\\main\\java\\Dictionaries\\" + dictionary + ".txt");
                        br1 = new BufferedReader(new InputStreamReader(file1));
                        file2 = new FileInputStream("src\\main\\java\\Dictionaries\\" + dictionary2 + ".txt");
                        br2 = new BufferedReader(new InputStreamReader(file2));
                    }

                    if (command.substring(i).equals("-g")) genCheck = true;
                    else valid = hasSaltAndPepper(i, dicCommand);
                    break;

                case "hybrid_dic":
                    c = "x";
                    i = 0;
                    while (!c.equals(" ")) { c = command.substring(start+1+i,start+2+i); i++; }
                    dictionary = command.substring(start + 1, start+i).trim();
                    tmp = start + dictionary.length() + 2;

                    c = "m";
                    i = 0;
                    while (!c.equals(" ")) { c = command.substring(tmp+i,tmp+i+1); i++; }
                    hybridLength = Integer.valueOf(command.substring(tmp,tmp+i-1));

                    CHARSET = NUMBERS;
                    tmp = tmp + i - 2;
                    dicCommand = String.valueOf(command.substring(tmp + 2, tmp +4));

                    if (!checkDictionary(dictionary)) valid = false;
                    else {
                        file1 = new FileInputStream("src\\main\\java\\Dictionaries\\" + dictionary + ".txt");
                        br1 = new BufferedReader(new InputStreamReader(file1));
                    }
                    if (command.substring(tmp+2).equals("-g")) genCheck = true;
                    else valid = hasSaltAndPepper(tmp+2, dicCommand);
                    break;
            }

        } catch (Exception e) { valid = false; }
        return valid;
    }

    private boolean hasSaltAndPepper(int tmp, String dicCommand) {
        String x = "x";
        String pepper;
        boolean valid = true;
        if (dicCommand.equals("-s") && command.contains("-p")) {
            hasSalt = true;
            int i = tmp+dicCommand.length()+3;
            while (!x.equals(" ")) { x = command.substring(i,i+1); i++; }
            salt = command.substring(tmp+dicCommand.length()+1,i-1);

            if (command.substring(i,i+2).equals("-p")) {
                hasPepper = true;
                pepper = command.substring(i+3);
                getPepper(pepper); }

        } else if (dicCommand.equals("-p") && command.contains("-s")) {
            hasPepper = true;
            pepper = command.substring(tmp+dicCommand.length()+1,tmp+dicCommand.length()+2);
            getPepper(pepper);

            if (command.substring(tmp+dicCommand.length()+3,tmp+dicCommand.length()+5).equals("-s")) {
                hasSalt = true;
                salt = command.substring(tmp+dicCommand.length()+6); }

        } else if (dicCommand.equals("-s") && !command.contains("-p")) {
            hasSalt = true;
            salt = command.substring(tmp+dicCommand.length()+1);

        } else if (dicCommand.equals("-p") && !command.contains("-s")) {
            hasPepper = true;
            pepper = command.substring(tmp+dicCommand.length()+1);
            getPepper(pepper); }

        else if (dicCommand.equals("-n")) standard = true;
        else if (!dicCommand.equals("-n")) valid = false;
        return valid;
    }

    private void getPepper(String pepper) {
        switch (pepper) {
            case "l":
                CHARSET = LOWERCASE;
                break;
            case "u":
                CHARSET = UPPERCASE;
                break;
            case "n":
                CHARSET = NUMBERS;
                break;
            case "s":
                CHARSET = SPECIAL;
                break;
        }
    }

    public void execute() throws Exception {
        if (generate.getSalt() != null) hashedPassword = MD5.getSaltHashPassword(generate.getPlainTextPassword(),generate.getSalt().getBytes());
        else hashedPassword = generate.getHashedPassword();

        switch (algorithm) {

            case "num_brute":
                result = num_brute();
                complete = true;
                break;

            case "alpha_brute":
                if (player.getRank() > 19 && !Arrays.equals(CHARSET, NUMBERS)) {
                    for (int k = 1; k <= length; k++) {
                        result = alpha_brute("", 0, k);
                        if (!result.equals("No match!")) break; } }
                else result = alpha_brute("", 0, length);
                complete = true;
                break;

            case "dic":
                result = dictionary();
                complete = true;
                break;

            case "comb_dic":
                result = combinator_dic();
                complete = true;
                break;

            case "hybrid_dic":
                result = hybrid();
                complete = true;
                break;
        }
    }

    private String num_brute() {
        for (i = 0; i <= range; i++) {
            current = String.valueOf(i);
            if (MD5.getHashPassword(current).equals(hashedPassword))
                return generate.getPlainTextPassword();
        }
        return "No match!";
    }

    private String alpha_brute(String str, int pos, int length) {
        if (length == 0) {
            current = str;
            i++;
            if (MD5.getHashPassword(current).equals(hashedPassword)) {
                brute = true;
                match = str;
            }
        } else {
            if (pos != 0) {
                pos = 0;
            }
            if (!brute) {
                for (int j = pos; j < CHARSET.length; j++)
                    alpha_brute(str + CHARSET[j], j, length -1);
            }
        }
        return match;
    }

    // compute and check hash for each word in supplied dictionary
    private String dictionary() throws Exception {
        String st;
        if (genCheck || genCheckWriteCheck) result = generateCheckWrite(false);
        else if (fetchCheck) result = fetchCheck();
        else {
            if (hasSalt & hasPepper) {
                for (int j = 0; j < CHARSET.length; j++) {
                    while ((st = br1.readLine()) != null) {
                        current = st.replaceAll("\\s+","") + CHARSET[j];
                        i++;
                        if (MD5.getSaltHashPassword(st, salt.getBytes()).equals(hashedPassword)) {
                            match = st;
                            return match; } }
                    file1.getChannel().position(0);
                    br1 = new BufferedReader(new InputStreamReader(file1)); } }

            else if (hasSalt) {
                while ((st = br1.readLine()) != null) {
                    current = st.replaceAll("\\s+","");
                    i++;
                    if (MD5.getSaltHashPassword(current, salt.getBytes()).equals(hashedPassword)) {
                        match = st;
                        return match; } } }

            else if (hasPepper) {
                for (int j = 0; j < CHARSET.length; j++) {
                    while ((st = br1.readLine()) != null) {
                        current = st.replaceAll("\\s+","") + CHARSET[j];
                        i++;
                        if (MD5.getHashPassword(current).equals(hashedPassword)) {
                            match = st;
                            return match; } }
                    file1.getChannel().position(0);
                    br1 = new BufferedReader(new InputStreamReader(file1)); } }

            else {
                while ((st = br1.readLine()) != null) {
                    current = st.replaceAll("\\s+","");
                    i++;
                    if (MD5.getHashPassword(current).equals(hashedPassword)) {
                        match = st;
                        return match; } } }
        }
        return match;
    }

    private String combinator_dic() throws Exception {
        if (genCheck) match = generateCheckWrite(true);
        else {
            String st1;
            String st2;
            if (hasSalt && hasPepper) {
                for (int j = 0; j < CHARSET.length; j++) {
                    while ((st1 = br1.readLine()) != null) {
                        while ((st2 = br2.readLine()) != null) {
                            i++;
                            current = (st1 + st2).replaceAll("\\s+","") + CHARSET[j];
                            if (MD5.getSaltHashPassword(current, salt.getBytes()).equals(hashedPassword)) {
                                match = current;
                                return match; } }
                        file2.getChannel().position(0);
                        br2 = new BufferedReader(new InputStreamReader(file2)); }
                    file1.getChannel().position(0);
                    br1 = new BufferedReader(new InputStreamReader(file1)); } }

            else if (hasPepper) {
                for (int z = 0; z < CHARSET.length; z++) {
                    while ((st1 = br1.readLine()) != null) {
                        while ((st2 = br2.readLine()) != null) {
                            i++;
                            current = (st1+st2).replaceAll("\\s+","") + CHARSET[z];
                            if (MD5.getHashPassword(current).equals(hashedPassword)) {
                                match = current;
                                return match; } }
                        file2.getChannel().position(0);
                        br2 = new BufferedReader(new InputStreamReader(file2)); }
                    file1.getChannel().position(0);
                    br1 = new BufferedReader(new InputStreamReader(file1)); }

            } else if (hasSalt) {
                while ((st1 = br1.readLine()) != null) {
                    while ((st2 = br2.readLine()) != null) {
                        i++;
                        current = (st1+st2).replaceAll("\\s+","");
                        if (MD5.getSaltHashPassword(current, salt.getBytes()).equals(hashedPassword)) {
                            match = current;
                            return match; } }
                    file2.getChannel().position(0);
                    br2 = new BufferedReader(new InputStreamReader(file2)); } }

            else {
                while ((st1 = br1.readLine()) != null) {
                    while ((st2 = br2.readLine()) != null) {
                        i++;
                        current = (st1+st2).replaceAll("\\s+","");
                        if (MD5.getHashPassword(current).equals(hashedPassword)) {
                            match = current;
                            return match; } }
                    file2.getChannel().position(0);
                    br2 = new BufferedReader(new InputStreamReader(file2)); } }
        }
        return match;
    }

    private String hybrid() throws Exception {
        if (genCheck) match = generateCheckWrite( false);
        else {
            String st;
            if (hasSalt && hasPepper) {
                for (int j = 0; j < CHARSET.length; j++) {
                    while ((st = br1.readLine()) != null) {
                        for (int k = 0; k < hybridLength; k++) {
                            i++;
                            current = st.replaceAll("\\s+","") + String.valueOf(k) + CHARSET[j];
                            if (MD5.getSaltHashPassword(current, salt.getBytes()).equals(hashedPassword)) {
                                match = current;
                                return match; } } }
                    file1.getChannel().position(0);
                    br1 = new BufferedReader(new InputStreamReader(file1)); }

            } else if (hasPepper) {
                for (int j = 0; j < CHARSET.length; j++) {
                    while ((st = br1.readLine()) != null) {
                        for (int k = 0; k < hybridLength; k++) {
                            i++;
                            current = st.replaceAll("\\s+","") + String.valueOf(k) + CHARSET[j];
                            if (MD5.getHashPassword(current).equals(hashedPassword)) {
                                match = current;
                                return match; } } }
                    file1.getChannel().position(0);
                    br1 = new BufferedReader(new InputStreamReader(file1)); }

            } else if (hasSalt) {
                while ((st = br1.readLine()) != null) {
                    for (int k = 0; k < hybridLength; k++) {
                        i++;
                        current = st.replaceAll("\\s+","") + String.valueOf(k);
                        if (MD5.getSaltHashPassword(current, salt.getBytes()).equals(hashedPassword)) {
                            match = current;
                            return match; } } }

            } else {
                while ((st = br1.readLine()) != null) {
                    for (int k = 0; k < hybridLength; k++) {
                        i++;
                        current = st.replaceAll("\\s+","") + String.valueOf(k);
                        if (MD5.getHashPassword(current).equals(hashedPassword)) {
                            match = current;
                            return match; } } } }
        }
        return match;
    }

    private String genCheck(Hashtable h) {
        if (h.containsKey(hashedPassword)) {
            match = (String) h.get(hashedPassword);
            return match;
        } else
            return match;
    }

    // fetches the saved hash table, searches for password in fetched table
    private String fetchCheck() throws Exception {
        startTime1 = System.currentTimeMillis();
        Hashtable h = getHashTable();
        fetched = true;
        startTime11 = (float) (System.currentTimeMillis() - startTime1)/1000+ "s";

        startTime2 = System.currentTimeMillis();
        result = genCheck(h);
        lookup = true;
        return result;
    }

    private String generateCheckWrite(boolean combinator) throws Exception {
        startTime1 = System.currentTimeMillis();
        Hashtable h = generateHashTable(combinator);
        hashTable = true;

        startTime2 = System.currentTimeMillis();
        result = genCheck(h);
        lookup = true;

        if (genCheckWriteCheck) {
            startTime3 = System.currentTimeMillis();
            writeHashTable(h);
            writeTable = true;
        }
        return result;
    }

    private Hashtable generateHashTable(boolean combinator) throws Exception {
        Hashtable hashTable = new Hashtable<>();
        String st;
        if (combinator) {
            String st2;
            while ((st = br1.readLine()) != null) {
                while ((st2 = br2.readLine()) != null) {
                    i++;
                    hashTable.put(MD5.getHashPassword((st+st2).replaceAll("\\s+","")), st.replaceAll("\\s+",""));
                }
                file2.getChannel().position(0);
                br2 = new BufferedReader(new InputStreamReader(file2));
            }
        } else {
            while ((st = br1.readLine()) != null) {
                i++;
                hashTable.put(MD5.getHashPassword(st.replaceAll("\\s+","")), st.replaceAll("\\s+",""));
            }
        }
        return hashTable;
    }

    private void writeHashTable(Hashtable ht) throws Exception {
        FileOutputStream fos = new FileOutputStream("src\\main\\java\\Hash_Tables\\" + dictionary + "_Hash_Table.tmp");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(ht);
    }

    private Hashtable getHashTable() throws Exception {
        FileInputStream fileIn = new FileInputStream("src\\main\\java\\Hash_Tables\\" + dictionary + "_Hash_Table.tmp");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        return (Hashtable)in.readObject();
    }

    public long lineCount() throws Exception {
        Path path = Paths.get("src\\main\\java\\Dictionaries\\" + dictionary + ".txt");
        Path path2 = Paths.get("src\\main\\java\\Dictionaries\\" + dictionary2 + ".txt");
        if (algorithm.equals("hybrid_dic") && hasPepper) {
            return Files.lines(path).count() * hybridLength * CHARSET.length;
        }
        else if (algorithm.equals("hybrid_dic")) {
            return Files.lines(path).count() * hybridLength;
        }
        else if (algorithm.equals("comb_dic") && hasPepper) {
            return Files.lines(path).count() * Files.lines(path2).count() * CHARSET.length;
        }
        else if (algorithm.equals("comb_dic")) {
            return Files.lines(path).count() * Files.lines(path2).count();
        }
        else if (algorithm.equals("dic") && hasPepper) {
            return Files.lines(path).count() * CHARSET.length;
        }
        else {
            return Files.lines(path).count();
        }
    }

    private boolean checkTMP() {
        File file = new File("src\\main\\java\\Hash_Tables\\" + dictionary + "_Hash_Table.tmp");
        return file.exists();
    }

    private boolean checkDictionary(String dic) {
        boolean valid = true;
        File file = new File("src\\main\\java\\Dictionaries\\" + dic + ".txt");
        return file.exists();
    }

    public String getCurrentHash() {
        if (hasSalt) return MD5.getSaltHashPassword(current,salt.getBytes());
        else return MD5.getHashPassword(current);
    }

    public boolean isFetched() { return fetched; }

    public String getCurrentPlainText() { return String.valueOf(current); }

    public boolean isSalt() { return this.hasSalt; }

    public String getSalt() { return salt; }

    public String getAlgorithm() { return algorithm; }

    public int getI() { return i; }

    public double getRange() { return range; }

    public boolean getComplete() { return complete; }

    public String getResult() { return result; }

    public void setResult(String result) { this.result = result; }

    public boolean isGenCheck() { return genCheck; }

    public boolean isFetchCheck() { return fetchCheck; }

    public boolean isStandard() { return standard; }

    public boolean isGenCheckWriteCheck() { return genCheckWriteCheck; }

    public float getStartTime11() { return (float) (System.currentTimeMillis() - startTime1)/1000; }

    public float getStartTime33() { return (float) (System.currentTimeMillis() - startTime3)/1000; }

    public boolean isHashTable() { return hashTable; }

    public boolean isLookup() { return lookup; }

    public boolean isWriteTable() { return writeTable; }

}