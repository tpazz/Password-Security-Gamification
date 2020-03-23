package algorithms;

import Generate.Generate;

public class Algorithm implements Runnable {

    private Generate generate;
    private String command;
    private String hashedPassword;
    private String algorithm;
    private String dictionary;
    private String dictionary2;
    private String alpha;
    private String str1;
    private String str2;
    private String str3;
    private int length;

    private volatile int range;
    private volatile int i;
    private volatile boolean complete = false;
    private volatile String result;

    public Algorithm(Generate g, String c) {
        this.generate = g;
        this.command = c;
    }

    public boolean validate() {
        boolean valid = true;
        try {
            int dlim = command.indexOf(' ');
            algorithm = command.substring(0,dlim);
            int start = algorithm.length();
            switch (algorithm) {

                case "num_brute":
                    range = Integer.parseInt(command.substring(start+1));
                    break;

                case "alpha_brute":
                    alpha = String.valueOf(command.substring(start+1, start+2));
                    length = Integer.valueOf(command.substring(start+4));
                    if ((length >0 && length <= 5) && (alpha.equals("-a") || alpha.equals("-s") || alpha.equals("-as")))
                        valid = true;
                    break;

                case "alpha_num":
                    alpha = String.valueOf(command.substring(start+1, start+2));
                    length = Integer.valueOf(command.substring(start+4, start+5));
                    range = Integer.valueOf(command.substring(start+7));
                    if ((length >0 && length <= 5) && (alpha.equals("-a") || alpha.equals("-s") || alpha.equals("-as")))
                        valid = true;
                    break;

                case "dictionary":
                    dictionary = command.substring(start+1);
                    break;

                case "combinator_dic":
                    int delim2 = command.substring(start+1).indexOf(' ');
                    dictionary = command.substring(start+1,delim2-1);
                    dictionary2 = command.substring(delim2+1);
                    break;

                case "hybrid":
                    delim2 = command.substring(start+1).indexOf(' ');
                    dictionary = command.substring(start+1,delim2-1);
                    range = Integer.parseInt(command.substring(delim2+1));
                    break;

                case "keyword":
                    delim2 = command.substring(start+1).indexOf(' ');
                    int delim3 = command.substring(delim2+1).indexOf(' ');
                    str1 = command.substring(start+1,delim2-1);
                    str2 = command.substring(delim2+1,delim3-1);
                    str3 = command.substring(delim3);
                    break;
            }
        } catch (Exception e) { valid = false;}
        return valid;
    }

    public void execute() throws Exception {
        hashedPassword = generate.getHashedPassword();
        switch (algorithm) {
            case "num_brute":
                result = num_brute();
                break;
            case "alpha_brute":
                result = alpha_brute();
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
        return String.valueOf(i);
    }

    public String getCurrentHash() {
        return MD5.getHashPassword(String.valueOf(i));
    }

    public String num_brute() {
        for (i = 0; i < range; i++) {
            if (MD5.getHashPassword(String.valueOf(i)).equals(hashedPassword)) {
                complete = true;
                return generate.getPlainTextPassword();
            }
        }
        complete = true;
        return "No match!";
    }

    public String alpha_brute() {

        return null;
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
    public int getRange() { return range; }
    public boolean getComplete() { return complete; }
    public String getResult() { return result; }

    @Override
    public void run() {
        try {
            execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
