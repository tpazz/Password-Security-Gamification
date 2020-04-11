package algorithms;

import Generate.Generate;
import Player.Player;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.sun.xml.internal.ws.api.ha.StickyFeature;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Currency;
import java.util.Hashtable;

public class Algorithm implements Runnable {

    public final char[] LOWERCASE = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    public final char[] UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public final char[] NUMBERS = "0123456789".toCharArray();
    public final char[] SPECIAL = "!@#$%^&*()-_+=~`[]{}|:;<>,.?/".toCharArray();
    public final char[] ALLCHARS = ("abcdefghijklmnopqrstuvwxyzAEIOU0123456789" +
            "!@#$%^&*()-_+=~`[]{}|:;<>,.?/BCDFGHJKLMNPQRSTVWXYZ").toCharArray();

    private char[] CHARSET;
    private Generate generate;
    private Player player;
    private String pepper;
    private String command;
    private String hashedPassword;
    private String algorithm;
    private String dictionary;
    private String dicCommand;
    private String dictionary2;
    private String alpha1;
    private String alpha2;
    public String foundHash;
    public boolean fetched = false;
    public boolean genCheckWriteCheck = false;
    public boolean writeCheck = false;
    public boolean fetchCheck = false;
    public boolean standard = false;
    public boolean salt = false;
    public boolean peppoink = false;
    public boolean genCheck = false;
    private String salty;
    private String match = "No match!";
    private String current = "";
    private String str1;
    private String str2;
    private String str3;
    private int length;
    private int hybridLength;
    private boolean brute = false;
    public boolean hashTable = false;
    public boolean lookup = false;
    public boolean writeTable = false;
    private long startTime1;
    private long startTime2;
    private long startTime3;
    private String startTime11;
    private String startTime22;
    private String startTime33;

    private volatile double range;
    private volatile int i = 0;
    private volatile boolean complete = false;
    private volatile String result = "No match!";

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
            switch (algorithm) {

                case "num_brute":
                    range = Integer.parseInt(command.substring(start + 1));
                    break;

                case "alpha_brute":
                    alpha1 = String.valueOf(command.substring(start + 1, start + 3));
                    if (command.length() > 16) {
                        alpha2 = String.valueOf(command.substring(start + 4, start + 6));
                        length = Integer.valueOf(command.substring(start + 7));
                    } else {
                        length = Integer.valueOf(command.substring(start + 4));
                    }

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
                            CHARSET = ALLCHARS;
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
                    range = Math.pow(CHARSET.length, length);
                    break;

                case "dic":
                    String ch = "x";
                    int i = 0;
                    while (!ch.equals(" ")) {
                        ch = command.substring(start+1+i,start+2+i);
                        i++;
                    }
                    dictionary = command.substring(start + 1, start+i).trim();
                    int tmp = start + dictionary.length();
                    dicCommand = String.valueOf(command.substring(tmp + 2, tmp +4));

                    if (player.getRank() < 14) {
                        switch (dicCommand) {
                            case "-g":
                                genCheckWriteCheck = true;
                                break;
                            case "-f":
                                fetchCheck = true;
                                break;
                            case "-n":
                                standard = true;
                                break;
                            case "-s":
                                salt = true;
                                break;
                            default:
                                valid = false;
                        }
                        if (standard) {
                            if (player.getRank() < 10 || (player.getRank() > 10 && player.getRank() < 13)) valid = false;
                        }
                        if (salt) {
                            if (player.getRank() < 13) valid = false;
                        }
                        if (genCheckWriteCheck) {
                            if (player.getRank() < 11 || player.getRank() == 12 || player.getRank() == 13) valid = false;
                        }
                        if (fetchCheck) {
                            if (!checkTMP() || player.getRank() < 12) valid = false;
                        }
                        if (!checkDictionary(dictionary)) valid = false;
                        if (salt) salty = command.substring(tmp + dicCommand.length() + 3);

                    } else {
                        if (dicCommand.equals("-g")) {
                            genCheck = true;
                        } else {
                            saltAndPepper(tmp+2);
                        }
                    }
                    break;

                case "comb_dic":
                    String c = "x";
                    int x = 0;
                    while (!c.equals(" ")) {
                        c = command.substring(start+1+x,start+2+x);
                        x++;
                    }
                    dictionary = command.substring(start + 1, start+x).trim();
                    int y = start+dictionary.length()+2;
                    String p = "x";
                    while (!p.equals(" ")) {
                        p = command.substring(y,y+1);
                        y++;
                    }
                    dictionary2 = command.substring(start + dictionary.length()+2, y).trim();
                    dicCommand = command.substring(y,y+2);

                    if (command.substring(y).equals("-g")) {
                        genCheck = true;
                    } else {
                        valid = saltAndPepper(y);
                    }

                    if (!checkDictionary(dictionary)) valid = false;
                    if (!checkDictionary(dictionary2)) valid = false;

                    break;

                case "hybrid_dic":
                    String h = "x";
                    int g = 0;
                    while (!h.equals(" ")) {
                        h = command.substring(start+1+g,start+2+g);
                        g++;
                    }
                    System.out.println(command.substring(start + 1));
                    dictionary = command.substring(start + 1, start+g).trim();

                    tmp = start + dictionary.length() + 2;
                    String m = "m";
                    int u = 0;
                    while (!m.equals(" ")) {
                        m = command.substring(tmp+u,tmp+u+1);
                        u++;
                    }
                    hybridLength = Integer.valueOf(command.substring(tmp,tmp+u-1));
                    CHARSET = NUMBERS;
                    tmp = tmp + u -2;
                    System.out.println(command.substring(tmp));
                    dicCommand = String.valueOf(command.substring(tmp + 2, tmp +4));

                    if (command.substring(tmp+2).equals("-g")) genCheck = true;
                    else valid = saltAndPepper(tmp);

                    if (!checkDictionary(dictionary)) {
                        valid = false;
                    }
                    break;

            }
        } catch (Exception e) {
            System.out.println(e);
            valid = false;
        }
        return valid;
    }

    public boolean saltAndPepper(int tmp) {
        boolean valid = true;
        if (dicCommand.equals("-s") && command.contains("-p")) {
            salt = true;
            String k = "x";
            int v = tmp + dicCommand.length() +3;
            while (!k.equals(" ")) {
                k = command.substring(v,v+1);
                v++;
            }
            salty = command.substring(tmp + dicCommand.length() + 3, v);
            if (command.substring(v, v+2).equals("-p")) {
                peppoink = true;
                pepper = command.substring(v+3);
                getPepper();
            }
        } else if (dicCommand.equals("-p") && command.contains("-s")) {
            peppoink = true;
            pepper = command.substring(tmp + dicCommand.length() +3, tmp + dicCommand.length()+4);

            if (command.substring(tmp + dicCommand.length() +5, tmp + dicCommand.length() +7).equals("-s")) {
                salt = true;
                salty = command.substring(tmp+dicCommand.length()+8);
            }
        } else if (dicCommand.equals("-s") && !command.contains("-p")) {
            salt = true;
            salty = command.substring(tmp + dicCommand.length() +3);
        } else if (dicCommand.equals("-p") && !command.contains("-s")) {
            peppoink = true;
            pepper = command.substring(tmp + dicCommand.length() +3);
        } else if (dicCommand.equals("-n")) {
            standard = true;
        }
        else if (!dicCommand.equals("-n")) {
            valid = false;
        }
        return valid;
    }

    public void getPepper() {
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

    public boolean isGenCheck() {
        return genCheck;
    }

    public boolean isFetchCheck() {
        return fetchCheck;
    }

    public boolean isStandard() {
        return standard;
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
                //for (int k = 4; k <= length; k++) {
                result = alpha_brute("", 0, length);
                    //if (!result.equals("No match!")) break;
                //}
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
            case "keyword":
                result = keyword();
                break;
        }
    }

    private boolean checkTMP() {
        File file = new File("src\\main\\java\\Hash_Tables\\" + dictionary + "_Hash_Table.tmp");
        return file.exists();
    }

    private boolean checkDictionary(String dic) {
        File file = new File("src\\main\\java\\Dictionaries\\" + dic + ".txt");
        System.out.println(file);
        return file.exists();
    }

    public String getCurrentPlainText() {
        return String.valueOf(current);
    }

    public String getCurrentHash() {
        if (salt) return MD5.getSaltHashPassword(current,salty.getBytes());
        else return MD5.getHashPassword(current);
    }

    public String num_brute() {
        for (i = 0; i <= range; i++) {
            current = String.valueOf(i);
            if (MD5.getHashPassword(current).equals(hashedPassword)) {
                return generate.getPlainTextPassword();
            }
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
                for (int j = pos; j < CHARSET.length; j++) {
                    alpha_brute(str + CHARSET[j], j, length -1);
                }
            }
        }
        return match;
    }

    private void updateUI() throws Exception {
        player.getScreen().refresh();
    }

    public String dictionary() throws Exception {
        if (genCheck && player.getRank() > 13) {
            result = generateCheckWrite(false);
        } else {
            if (player.getRank() != 11 && player.getRank() != 12 && (standard || salt)) {
                result = checkHash();
            }
            else if (genCheckWriteCheck && player.getRank() != 12 && player.getRank() != 13) {
                System.out.println("here");
                result = generateCheckWrite(false);
            }
            else if (fetchCheck) {
                result = fetchCheck();
                lookup = true;
                startTime22 = (float) (System.currentTimeMillis() - startTime2)/1000 + "s";
            }
        }

        return result;
    }

    private String generateCheckWrite(boolean combinator) throws Exception {
        String tmp;
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

    public boolean isGenCheckWriteCheck() {
        return genCheckWriteCheck;
    }

    public long getStartTime1() {
        return startTime1;
    }

    public long getStartTime2() {
        return startTime2;
    }

    public long getStartTime3() {
        return startTime3;
    }

    public float getStartTime11() {
        return (float) (System.currentTimeMillis() - startTime1)/1000;
    }

    public float getStartTime22() {
        return (float) (System.currentTimeMillis() - startTime2)/1000;
    }

    public float getStartTime33() {
        return (float) (System.currentTimeMillis() - startTime3)/1000;
    }

    public boolean isHashTable() {
        return hashTable;
    }

    public boolean isLookup() {
        return lookup;
    }

    public boolean isWriteTable() {
        return writeTable;
    }

    public String combinator_dic() throws Exception {
        if (genCheck) {
            match = generateCheckWrite(true);
        } else {
            FileInputStream file1 = new FileInputStream("src\\main\\java\\Dictionaries\\" + dictionary + ".txt");
            FileInputStream file2 = new FileInputStream("src\\main\\java\\Dictionaries\\" + dictionary2 + ".txt");
            BufferedReader br1 = new BufferedReader(new InputStreamReader(file1));
            BufferedReader br2 = new BufferedReader(new InputStreamReader(file2));
            String st1;
            String st2;
            if (salt && peppoink) {
                for (int j = 0; j < CHARSET.length; j++) {
                    while ((st1 = br1.readLine()) != null) {
                        while ((st2 = br2.readLine()) != null) {
                            i++;
                            current = CHARSET[j] + st1 + st2;
                            if (MD5.getSaltHashPassword(current, salty.getBytes()).equals(hashedPassword)) {
                                match = current;
                                return match;
                            }
                        }
                        file2.getChannel().position(0);
                        br2 = new BufferedReader(new InputStreamReader(file2));
                    }
                    file1.getChannel().position(0);
                    br1 = new BufferedReader(new InputStreamReader(file1));
                }
            }
            else if (peppoink) {
                for (int z = 0; z < CHARSET.length; z++) {
                    while ((st1 = br1.readLine()) != null) {
                        while ((st2 = br2.readLine()) != null) {
                            i++;
                            current = CHARSET[z]+st1+st2;
                            if (MD5.getHashPassword(current).equals(hashedPassword)) {
                                match = current;
                                return match;
                            }
                        }
                        file2.getChannel().position(0);
                        br2 = new BufferedReader(new InputStreamReader(file2));
                    }
                    file1.getChannel().position(0);
                    br1 = new BufferedReader(new InputStreamReader(file1));
                }
            } else if (salt) {
                while ((st1 = br1.readLine()) != null) {
                    while ((st2 = br2.readLine()) != null) {
                        i++;
                        current = st1+st2;
                        if (MD5.getSaltHashPassword(current, salty.getBytes()).equals(hashedPassword)) {
                            match = current;
                            return match;
                        }
                    }
                    file2.getChannel().position(0);
                    br2 = new BufferedReader(new InputStreamReader(file2));
                }
            }
            else {
                while ((st1 = br1.readLine()) != null) {
                    while ((st2 = br2.readLine()) != null) {
                        i++;
                        current = st1+st2;
                        if (MD5.getHashPassword(current).equals(hashedPassword)) {
                            match = current;
                            return match;
                        }
                    }
                    file2.getChannel().position(0);
                    br2 = new BufferedReader(new InputStreamReader(file2));
                }
            }
        }
        return match;
    }
    public String hybrid() throws Exception {
        if (genCheck) {
            match = generateCheckWrite( false);
        } else {
            FileInputStream file1 = new FileInputStream("src\\main\\java\\Dictionaries\\" + dictionary + ".txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(file1));
            String st;
            if (salt && peppoink) {
                for (int j = 0; j < CHARSET.length; j++) {
                    while ((st = br.readLine()) != null) {
                        for (int k = 0; k < hybridLength; k++) {
                            i++;
                            current = CHARSET[j] + st + String.valueOf(k);
                            if (MD5.getSaltHashPassword(current, salty.getBytes()).equals(hashedPassword)) {
                                match = current;
                                return match;
                            }
                        }
                    }
                    file1.getChannel().position(0);
                    br = new BufferedReader(new InputStreamReader(file1));
                }
            } else if (peppoink) {
                for (int j = 0; j < CHARSET.length; j++) {
                    while ((st = br.readLine()) != null) {
                        for (int k = 0; k < hybridLength; k++) {
                            i++;
                            current = CHARSET[j] + st + String.valueOf(k);
                            if (MD5.getHashPassword(current).equals(hashedPassword)) {
                                match = current;
                                return match;
                            }
                        }
                    }
                    file1.getChannel().position(0);
                    br = new BufferedReader(new InputStreamReader(file1));
                }
            } else if (salt) {
                while ((st = br.readLine()) != null) {
                    for (int k = 0; k < hybridLength; k++) {
                        i++;
                        current = st + String.valueOf(k);
                        if (MD5.getSaltHashPassword(current, salty.getBytes()).equals(hashedPassword)) {
                            match = current;
                            return match;
                        }
                    }
                }
            } else {
                while ((st = br.readLine()) != null) {
                    for (int k = 0; k < hybridLength; k++) {
                        i++;
                        current = st + String.valueOf(k);
                        if (MD5.getHashPassword(current).equals(hashedPassword)) {
                            match = current;
                            return match;
                        }
                    }
                }
            }
        }
        return match;
    }
    public String keyword() {

        return null;
    }

    public String getAlgorithm() {
        return algorithm;
    }
    public int getI() { return i; }
    public double getRange() { return range; }
    public boolean getComplete() { return complete; }
    public String getResult() { return result; }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public void run() {
        try { execute(); }
        catch (Exception e) { e.printStackTrace(); }
    }

    // generates hash table, searches for password in generated table, writes
    // and saves hash table for faster lookup on subsequent searches
    private String genCheck(Hashtable h) throws Exception {
        if (h.containsKey(hashedPassword)) {
            match = (String) h.get(hashedPassword);
            return match;
        } else {
            return match;
        }
    }

    public boolean isFetched() {
        return fetched;
    }

    // fetches the saved hash table, searches for password in fetched table
    private String fetchCheck() throws Exception {
        startTime1 = System.currentTimeMillis();
        Hashtable h = getHashTable();
        fetched = true;
        startTime11 = (float) (System.currentTimeMillis() - startTime1)/1000+ "s";
        startTime2 = System.currentTimeMillis();
        if (h.containsKey(hashedPassword)) {
            result = (String) h.get(hashedPassword);
            return result;
        } else {
            return result;
        }
    }

    public long lineCount() throws Exception {
        Path path = Paths.get("src\\main\\java\\Dictionaries\\" + dictionary + ".txt");
        Path path2 = Paths.get("src\\main\\java\\Dictionaries\\" + dictionary2 + ".txt");
        if (algorithm.equals("hybrid_dic") && peppoink) {
            return Files.lines(path).count() * hybridLength * CHARSET.length;
        }
        else if (algorithm.equals("hybrid_dic")) {
            return Files.lines(path).count() * hybridLength;
        }
        else if (algorithm.equals("combinator_dic") && peppoink) {
            return Files.lines(path).count() * Files.lines(path2).count() * CHARSET.length;
        }
        else if (algorithm.equals("combinator_dic")) {
            return Files.lines(path).count() * Files.lines(path2).count();
        }
        else {
            return Files.lines(path).count();
        }
    }

    // compute and check hash for each word in supplied dictionary
    private String checkHash() throws Exception {
        if (generate.getSalt() != null) hashedPassword = MD5.getSaltHashPassword(generate.getPlainTextPassword(),generate.getSalt().getBytes());
        File file = new File("src\\main\\java\\Dictionaries\\" + dictionary + ".txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        if (salt) {
            while ((st = br.readLine()) != null) {
                current = st;
                i++;
                if (MD5.getSaltHashPassword(st, salty.getBytes()).equals(hashedPassword)) {
                    match = st;
                    return match;
                }
            }
        }
        else {
            while ((st = br.readLine()) != null) {
                current = st;
                i++;
                if (MD5.getHashPassword(st).equals(hashedPassword)) {
                    match = st;
                    return match;
                }
            }
        }
        return match;
    }

    private Hashtable generateHashTable(boolean combinator) throws Exception {
        Hashtable hashTable = new Hashtable<>();
        FileInputStream file1 = new FileInputStream("src\\main\\java\\Dictionaries\\" + dictionary + ".txt");
        BufferedReader br1 = new BufferedReader(new InputStreamReader(file1));
        String st;
        if (combinator) {
            FileInputStream file2 = new FileInputStream("src\\main\\java\\Dictionaries\\" + dictionary2 + ".txt");
            BufferedReader br2 = new BufferedReader(new InputStreamReader(file2));
            String st2;
            while ((st = br1.readLine()) != null) {
                while ((st2 = br2.readLine()) != null) {
                    i++;
                    hashTable.put(MD5.getHashPassword(st+st2), st);
                }
                file2.getChannel().position(0);
                br2 = new BufferedReader(new InputStreamReader(file2));
            }
        } else {
            while ((st = br1.readLine()) != null) {
                i++;
                hashTable.put(MD5.getHashPassword(st), st);
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
        Hashtable h = (Hashtable)in.readObject();
        return h;
    }
    public boolean isSalt() {
        return this.salt;
    }
    public String getSalty() {
        return salty;
    }
}