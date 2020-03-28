package Generate;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public abstract class Generate_helper {

    public final ArrayList<String> LVL1DESC = new ArrayList<String>() {{
       add("Passwords do not always have to be obtained systematically. One of the");
       add("easiest and most straight forward ways of obtaining a password is to find");
       add("it written down on a piece of paper or stored in a text file on a");
       add("computer, which can make it very easy for someone to gain possession ");
       add("of a password without having to crack it.");
       add("");
       add("Your boss has left his desk to get a coffee. You notice there are a few");
       add("post-it notes stuck around his monitor, one of which has 'g-mail password'");
       add("written on the front, on the other side is written 'password123'.");
       add("");
       add("Enter the password.");
    }};

    public final ArrayList<String> LVL2DESC = new ArrayList<String>() {{
        add("Passwords are almost never stored as plain-text in a database or other");
        add("storage medium. However, it is still a possibility amongst small businesses");
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
        add("hash output. To verify the original text, you must put it through the same");
        add("hash function and see if the output matches the hash you have.");
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
        add("Brute-force techniques are the bread and butter of all systematic");
        add("cracking algorithms. Pure brute-force approaches involve iterating through");
        add("all possible combinations of a password until a match is found. ");
        add("Theoretically, they are guaranteed to eventually find the password but the");
        add("number of possible combinations can make it infeasible, even with the ");
        add("most powerful computers.");
        add("");
        add("Numerical brute-force algorithms are extremely proficient at cracking");
        add("passcodes on mobile devices. Using the algorithm syntax presented in the");
        add("help page, try to crack the following device: iPhone 4S, 4 digit passcode");
    }};

    public final ArrayList<String> LVL5ESC = new ArrayList<String>() {{
        add("Computers make very short work of 4 digit passcodes. Some social media");
        add("websites such as Facebook use 6-digit numerical codes for two-factor ");
        add("authentication; try and break into the following account:");
        add("");
        add("Two-factor authentication required");
        add("You've asked us to require a 6-digit login code when someone tries to");
        add("access your account from a new device or browser.");
        add("Enter the 6-digit code from your code generator or third-party app");
    }};

    public final ArrayList<String> LVL6ESC = new ArrayList<String>() {{
        add("Evidently, computers excel at cracking 'short' numerical passwords.");
        add("However, each time we add a digit to the password, the number of possible");
        add("combinations increases exponentially and will take 10 times more time");
        add("to search the entire search space. Passport numbers are typically 8-digits");
        add("long. Try and crack the passport number for the following profile:");
        add("");
        add("Nationality: UNITED STATES OF AMERICA");
        add("Date of birth: 22 Jan 1974");
        add("Place of birth: Mumbai, INDIA");
        add("Passport number: ");
    }};

    public final ArrayList<String> LVL7ESC = new ArrayList<String>() {{
        add("Brute-force algorithms do not just apply to numerical passwords.");
        add("They can be applied to any type of data set (or sets) such as the");
        add("English alphabet, which works in base 26 instead of base 10 like");
        add("decimal numbers do.");
        add("");
        add("Try cracking the following 4 character lowercase password:");
    }};

    public final ArrayList<String> LVL8ESC = new ArrayList<String>() {{
        add("Although there are 26 letters in the English alphabet, if we also");
        add("consider both upper and lowercase letters we have 52 possible combinations");
        add("for each character.");
    }};

    public final ArrayList<String> LVL9ESC = new ArrayList<String>() {{
        add("Finally, if we also include numbers and special characters into the password");
        add("then we have a total of 96 possible combinations for each character. A");
        add("password of this data set with just 4 characters will contain 84,934,656");
        add("combinations! This is why many websites will ask for certain requirements");
        add("when creating a password so that it would be infeasible for a hacker to brute-");
        add("force it.");
    }};

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
