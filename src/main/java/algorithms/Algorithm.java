package algorithms;

import Generate.Generate;

public class Algorithm implements Runnable {

    private final char[] LOWERCASE = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private final char[] UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private final char[] NUMBERS = "0123456789".toCharArray();
    private final char[] SPECIAL = "!@#$%^&*()-_+=~`[]{}|:;<>,.?/".toCharArray();
    private final char[] ALLCHARS = "abcdefghijklmnopqrstuvwxyzAEIOU0123456789!@#$%^&*()-_+=~`[]{}|:;<>,.?/BCDFGHJKLMNPQRSTVWXYZ".toCharArray();

    private char[] CHARSET;
    private Generate generate;
    private String command;
    private String hashedPassword;
    private String algorithm;
    private String dictionary;
    private String dictionary2;
    private String alpha1;
    private String alpha2;
    private String match = "No match!";
    private String current = "";
    private String str1;
    private String str2;
    private String str3;
    private int length;

    private volatile double range;
    private volatile int i = 0;
    private volatile boolean complete = false;
    private volatile String result = "No match!";

    public Algorithm(Generate g, String c) {
        this.generate = g;
        this.command = c;
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

                case "dictionary":
                    dictionary = command.substring(start + 1);
                    break;

                case "combinator_dic":
                    int delim2 = command.substring(start + 1).indexOf(' ');
                    dictionary = command.substring(start + 1, delim2 - 1);
                    dictionary2 = command.substring(delim2 + 1);
                    break;

                case "hybrid":
                    delim2 = command.substring(start + 1).indexOf(' ');
                    dictionary = command.substring(start + 1, delim2 - 1);
                    range = Integer.parseInt(command.substring(delim2 + 1));
                    break;

                case "keyword":
                    delim2 = command.substring(start + 1).indexOf(' ');
                    int delim3 = command.substring(delim2 + 1).indexOf(' ');
                    str1 = command.substring(start + 1, delim2 - 1);
                    str2 = command.substring(delim2 + 1, delim3 - 1);
                    str3 = command.substring(delim3);
                    break;
            }
        } catch (Exception e) {
            valid = false;
        }
        return valid;
    }

    public void execute() {
        hashedPassword = generate.getHashedPassword();
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
            case "alpha_num":
                result = alpha_num();
                break;
            case "dictionary":
                result = dictionary();
                break;
            case "combinator_dic":
                result = combinator_dic();
                break;
            case "hybrid":
                result = hybrid();
                break;
            case "keyword":
                result = keyword();
                break;
        }
    }

    public String getCurrentPlainText() {
        return String.valueOf(current);
    }

    public String getCurrentHash() {
        return MD5.getHashPassword(current);
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
                complete = true;
                match = str;
            }
        } else {
            if (pos != 0) {
                pos = 0;
            }
            if (!complete) {
                for (int j = pos; j < CHARSET.length; j++) {
                    alpha_brute(str + CHARSET[j], j, length -1);
                }
            }
        }
        return match;
    }
    public String alpha_num() {

        return null;
    }
    public String dictionary() {

        return null;
    }
    public String combinator_dic() {

        return null;
    }
    public String hybrid() {

        return null;
    }
    public String keyword() {

        return null;
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
        try {
            execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
