package Generate;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public abstract class Generate_helper {

    protected final String INTRO = "INTRO";
    protected final String BEGGINER = "BEGGINER";
    protected final String INTERMEDIATE = "INTERMEDIATE";
    protected final String DIFFICULT = "HARD";
    protected final String FINALLEVEL = "FINAL LEVEL";
    protected final String NUMBRUTE = "num_brute";
    protected final String ALPHABRUTE = "alpha_brute";
    protected final String DICTIONARY = "dictionary";
    protected final String COMBINATOR = "combinator_dic";
    protected final String HYBRID = "hybrid_dic";
    protected final String KEYWORD = "keyword";

    public final ArrayList<String> LVL1DESC = new ArrayList<String>() {{
       add("Passwords do not always have to be obtained systematically. One of the");
       add("easiest and most straight forward ways of obtaining a password is to find");
       add("it written down on a piece of paper or stored in a text file on a");
       add("computer, which can make it very easy for someone to gain possession ");
       add("of a password without having to crack it.");
       add("");
       add("Your boss has left his desk to get a coffee. You notice there are a few");
       add("post-it notes stuck around his monitor, one of which has 'g-mail password'");
       add("written on the front, and on the other side is written 'password123'.");
       add("");
       add("Enter the password.");
    }};

    public final ArrayList<String> LVL2DESC = new ArrayList<String>() {{
        add("Passwords are almost never stored as plain-text in a database or other");
        add("storage medium. However, it is still a possibility amongst small entities");
        add("or other organisations that are unaware of the security implications of");
        add("storing un-hashed user credentials.");
        add("");
        add("user_id | user_name   | user_pwd    | user_email            | user_tel");
        add("-------------------------------------------------------------------------");
        add("   1    | adam_park2  | hippy1998   | adam@gmail.com        | 07654228176");
        add("   2    | davethefave | letmein     | david_p@hotmail.co.uk | 07828773610");
        add("   3    | aushles6    | nJj&d@9FG   | ashley@hotmail.co.uk  | 07263562354");
        add("   4    | tommy_7     | qjuehdxf    | tommy_shelb@yahoo.com | 07263356164");
        add("   5    | elsham666   | windows96   | eshali_opi@gmail.com  | 07218439761");
        add("   6    | pazzyboi96  | white_gold  | tpaz@sheffield.ac.uk  | 07817367833");
        add("");
        add("Locate and enter the password for Tommy Shelby.");
    }};

    public final ArrayList<String> LVL3DESC = new ArrayList<String>() {{
        add("Hashing is a technique that can be used to scramble data so that it is ");
        add("impossible for a human to read. A hash function will take a string of");
        add("arbitrary size and through a series of mathematical operations, output a");
        add("seemingly random string of characters. A property of hash functions is that");
        add("they one-way pseudorandom, meaning that you cannot work backwards from the");
        add("hash output. So, in order to verify the original text you must put it through");
        add("the same hash function and see if the output matches the hash you have.");
        add("");
        add("Try hashing some of the plain-text passwords to match the hash below.");
        add("The syntax for hashing is 'h( [text] )' for example, h(password123).");
        add("");
        add("qjuehdxf");
        add("letmein123           hash to match: 29a5002cf73a9852da4b12811c0897cc");
        add("hashingiscool            your hash:");
        add("white_gold");
    }};

    public final ArrayList<String> LVL4ESC = new ArrayList<String>() {{
        add("Brute-force techniques are the bread and butter of all systematic cracking");
        add("algorithms. Pure brute-force approaches involve iterating through all");
        add("possible combinations of a password until a match is found - in theory,");
        add("they are guaranteed to eventually find the password but the sheer number of");
        add("possible combinations can make it infeasible.");
        add("");
        add("Using the algorithm syntax presented in the help page for num_brute,");
        add("try and match the following hash and enter the plaintext password:");
    }};

    public final ArrayList<String> LVL5ESC = new ArrayList<String>() {{
        add("As you can see, computers can crack 4-digit numerical passwords almost");
        add("instantly. Perhaps increasing the length of the password will prove to");
        add("be a bit more challenging...");
        add("");
        add("Using the algorithm syntax presented in the help page for num_brute,");
        add("try and match the following hash and enter the plaintext password:");
    }};

    public final ArrayList<String> LVL6ESC = new ArrayList<String>() {{
        add("Despite taking slightly longer, 6-digit numerical passwords are still no");
        add("match for computers to crack. However, each time we add a digit to the");
        add("password the number of possible combinations increases exponentially and ");
        add("will take 10 times more time to search the entire search space.");
        add("");
        add("Using the algorithm syntax presented in the help page for num_brute,");
        add("try and match the following hash and enter the plaintext password:");
    }};

    public final ArrayList<String> LVL7ESC = new ArrayList<String>() {{
        add("Brute-force algorithms do not just apply to numerical passwords. They can");
        add("be applied to any type of data set (or sets) such as the English alphabet,");
        add("which works in base 26 instead of base 10 like decimal numbers do.");
        add("");
        add("Using the algorithm syntax presented in the help page for alpha_brute,");
        add("try and match the following hash and enter the plaintext password:");
    }};

    public final ArrayList<String> LVL8ESC = new ArrayList<String>() {{
        add("Although there are 26 letters in the English alphabet, if we also consider");
        add("uppercase letters, special characters or numbers into our password we have");
        add("52, 55 and 36 possible combinations for each character respectively.");
        add("");
        add("Using the algorithm syntax presented in the help page for alpha_brute,");
        add("experiment with combinations of different data sets to try and match the");
        add("following hash and enter the plain-text password:");
    }};

    public final ArrayList<String> LVL9ESC = new ArrayList<String>() {{
        add("If we consider all 'typeable' characters on a standard keyboard, we have a");
        add("total of 96 possibilities for each character - which will amount to ");
        add("782,757,789,696 combinations for just a 6-character password. This rapid growth");
        add("of complexity is known as the combinatorial explosion; many websites enforce");
        add("their users to have long passwords and use a variety of different character");
        add("sets for this reason so that it would be infeasible for a hacker to obtain your");
        add("your password through brute-force alone.");
        add("");
        add("Using the algorithm syntax presented in the help page for alpha_brute,");
        add("try and match the following hash and enter the plaintext password:");
    }};

    // HASH TABLES x3
    // SALT x3
    // PEPPER x3

    protected String getRandFirstName() throws Exception {
        Random r = new Random();
        File file = new File("src\\main\\java\\Dictionaries\\first-names.txt");
        int l = r.nextInt(Files.readAllLines(Paths.get(String.valueOf(file))).size());
        return Files.readAllLines(Paths.get(String.valueOf(file))).get(l);
    }

    protected String getRandSurName() throws Exception {
        Random r = new Random();
        File file = new File("src\\main\\java\\Dictionaries\\surnames.txt");
        int l = r.nextInt(Files.readAllLines(Paths.get(String.valueOf(file))).size());
        return Files.readAllLines(Paths.get(String.valueOf(file))).get(l);
    }
    private String getRandPassword() throws Exception {
        Random r = new Random();
        File file = new File("src\\main\\java\\Dictionaries\\first-names.txt");
        int l = r.nextInt(Files.readAllLines(Paths.get(String.valueOf(file))).size());
        return Files.readAllLines(Paths.get(String.valueOf(file))).get(l);
    }

}
