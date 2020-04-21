package Generate;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public abstract class Generate_helper {

    protected final String INTRO = "INTRO";
    protected final String NUMBRUTE = "num_brute";
    protected final String ALPHABRUTE = "alpha_brute";
    protected final String DICTIONARY = "dictionary";
    protected final String COMBINATOR = "combinator_dic";
    protected final String HYBRID = "hybrid_dic";

    public final ArrayList<String> LVL1DESC = new ArrayList<String>() {{
       add("Passwords do not always have to be obtained systematically. One of the");
       add("easiest and most straight forward ways of obtaining a password is to find");
       add("it written down on a piece of paper or stored in a text file on a computer");
       add("which can make it very easy for someone to gain possession of a password");
       add("without having to crack it.");
       add("");
       add("Your boss has left his desk to get a coffee. You notice there are a few");
       add("post-it notes stuck around his monitor, one of which has 'log-in password'");
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
        add("Hashing is a technique that can be used to scramble data so that it is");
        add("impossible for a human to read. A hash function will take a string of");
        add("arbitrary size and through a series of mathematical operations, output a");
        add("seemingly random string of characters. A property of hash functions is");
        add("that they one-way pseudorandom, meaning that you cannot work backwards ");
        add("from the output. So, in order to verify the original text you must pass it");
        add("to the same hash function and see if the output matches the hash you have.");
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
        add("Brute-force techniques are the bread and butter of systematic cracking");
        add("algorithms. Pure brute-force approaches involve iterating through all");
        add("possible combinations of a password until a match is found - in theory,");
        add("they are guaranteed to eventually find the password but the sheer number ");
        add("of possible combinations can make it infeasible.");
        add("");
        add("Using the algorithm syntax presented in the help page for num_brute,");
        add("try and match the following hash and enter the plaintext password:");
    }};

    public final ArrayList<String> LVL5ESC = new ArrayList<String>() {{
        add("As you can see, computers can crack 4-digit numerical passwords almost");
        add("instantly. Perhaps increasing the length of the password will prove to be");
        add("a bit more challenging...");
        add("");
        add("Using the algorithm syntax presented in the help page for num_brute,");
        add("try and match the following hash and enter the plaintext password:");
    }};

    public final ArrayList<String> LVL6ESC = new ArrayList<String>() {{
        add("Despite taking slightly longer, 6-digit numerical passwords are still no");
        add("match for computers to crack. However, each time we add a digit to the");
        add("password the number of possible combinations increases exponentially and");
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
        add("Consider all 'typeable' characters on a standard keyboard, we have a total");
        add("total of 96 possibilities for each character - which will amount to ");
        add("782,757,789,696 combinations for just a 6-character password. Websites");
        add("enforce the use of long and random passwords for this reason so that");
        add("hackers won't be able to crack your password through brute-force alone.");
        add("");
        add("Using the algorithm syntax presented in the help page for alpha_brute,");
        add("experiment with different data sets and lengths to match the following hash:");
    }};

    public final ArrayList<String> LVL10ESC = new ArrayList<String>() {{
        add("Another technique for cracking passwords is through dictionary attacks.");
        add("They work by iterating over commonly used words or phrases to guess a");
        add("password, and are generally considered to be much more effective than");
        add("brute-force due to the simple fact that most passwords consist of words");
        add("rather than random characters.");
        add("");
        add("Using the algorithm syntax presented in the help page for dic,");
        add("try and match the following hash and enter the plaintext password:");
    }};

    public final ArrayList<String> LVL11ESC = new ArrayList<String>() {{
        add("Instead of having to compute and check the hash each time for every word");
        add("in a dictionary, we can store the plain-text password along with its ");
        add("associated hash as a pair in a hash table so that we can look up the hash");
        add("much quicker in subsequent searches.");
        add("");
        add("Using the algorithm syntax presented in the help page for dic, append -w");
        add("to generate, search and write a hash table to match the following hash:");
    }};

    public final ArrayList<String> LVL12ESC = new ArrayList<String>() {{
        add("As you can see, looking up a hash key in a table is extremely fast. This");
        add("is because hash-tables are able to lookup data on the order O(1); in other");
        add("words 1 operation, independent of the size. Of course there is no free");
        add("lunch here as there is a trade-off between time and memory when it comes");
        add("to generating hash tables, so pre-computed hashes can be very practical.");
        add("");
        add("Using the algorithm syntax presented in the help page for dic, append -f");
        add("to fetch the pre-computed hash table and match the following hash:");
    }};

    public final ArrayList<String> LVL13ESC = new ArrayList<String>() {{
        add("One way to prevent the use of hash-tables is by using salts. A salt is a");
        add("non-secret value which is appended to the password before it gets hashed,");
        add("thus outputting a different hash from just the password itself: stored");
        add("hash = hash(salt + password). They are non-secret because they are stored");
        add("alongside the hash as plain-text.");
        add("");
        add("Using the algorithm syntax presented in the help page for dic,");
        add("add the salt to the algorithm and match the following hash:");
    }};

    public final ArrayList<String> LVL14ESC = new ArrayList<String>() {{
        add("Although salting renders hash tables useless, brute-force and dictionary");
        add("attacks will still be an issue assuming an attacker takes into account");
        add("the salt and knows where to put it in their guesses. Combinator dictionary");
        add("attacks will append each word to every word in its own dictionary, or use");
        add("multiple dictionaries which can be very effective for 2-word passwords.");
        add("");
        add("Using the algorithm syntax presented in the help page for comb_dic,");
        add("try and match the following hash and enter the plaintext password:");
    }};

    public final ArrayList<String> LVL15ESC = new ArrayList<String>() {{
        add("Peppers are another tool used to prevent hash-table look-ups. A pepper is");
        add("a very short random string added to the password before it is hashed, but");
        add("unlike salts their value is not stored. In order to determine the hashed");
        add("password+pepper, every combination of the pepper will have to be computed");
        add("until a match is found.");
        add("");
        add("Using the algorithm syntax presented in the help page for comb_dic,");
        add("add a numerical pepper to the algorithm and match the following hash:");
    }};

    public final ArrayList<String> LVL16ESC = new ArrayList<String>() {{
        add("As you can see, peppers are very effective against brute-force attacks.");
        add("Adding just one secret letter to the password means that an extra 26 (or");
        add("52 if both upper and lowercase are used) hashes would need to be computed");
        add("for each possible password, thus taking 26 (or 52) times longer to cycle");
        add("through every password");
        add("");
        add("Using the algorithm syntax presented in the help page for comb_dic,");
        add("experiment adding different peppers to match the following hash:");
    }};

    public final ArrayList<String> LVL17ESC = new ArrayList<String>() {{
        add("Hybrid dictionary attacks are a combination of brute-force and dictionary");
        add("attacks. They work by either appending or prepending a (numerical) brute-");
        add("force keyspace to each of the words from the dictionary.");
        add("");
        add("Using the algorithm syntax presented in the help page for hybrid_dic,");
        add("append the numerical keyspace to the algorithm to match the following hash:");
    }};

    public final ArrayList<String> LVL18ESC = new ArrayList<String>() {{
        add("Both combinator and hybrid dictionary attacks are effective as they tackle");
        add("common password traits. Joining two words together or having a word ");
        add("followed by a sequence of numbers (e.g. a date) are examples of habitual ");
        add("pitfalls that attackers can take advantage of.");
        add("");
        add("Using the algorithm syntax presented in the help page for hybrid_dic,");
        add("experiment with different peppers to match the following hash:");
    }};

    public final ArrayList<String> LVL19ESC = new ArrayList<String>() {{
        add("Any form of dictionary attack is only as effective as the dictionary it is");
        add("using. In addition to using an effective dictionary, combining salts and");
        add("peppers to passwords ensures a good level of security that will make life");
        add("difficult for an attacker to obtain a password.");
        add("");
        add("Using the algorithm syntax presented in the help page for hybrid_dic,");
        add("add the salt and experiment with different peppers to match the following");
        add("hash:");
    }};

    public final ArrayList<String> PRE20DESC = new ArrayList<String>() {{
        add("You have now completed all the pre-cursor levels! The following section");
        add("will involve having to crack passwords from randomly generated profiles.");
        add("");
        add("The following features have been added:");
        add(" > alpha_brute now computes all combinations up to specified length");
        add(" > alpha_brute can now use all 96 combined character types (-a)");
        add(" > salts and/or peppers can be added to any DICTIONARY attack");
        add(" > hash tables can be generated for any DICTIONARY attack (-g)");
        add(" > writing and fetching hash tables has been DISABLED (-w, -f)");
        add("");
        add("The password will be associated with the generated profile. It can be");
        add("cracked using any of the algorithms and/or dictionaries - good luck!");
    }};

    public final ArrayList<String> THANKYOU = new ArrayList<String>() {{
        add("You have reached the end of the levels! Thank you for taking part in this");
        add("research, hopefully you have learned something new and now understand the");
        add("semantics behind a strong password. If you haven't already, try and unlock");
        add("the rest of the achievements by creating various passwords in the Password");
        add("Strength page from the Main Menu.");
        add("");
        add("Please remember take part in the short post-game questionnaire ☺");
    }};

    protected String getRandPassword(String dictionary) throws Exception {
        Random r = new Random();
        File file = new File("src\\main\\java\\Dictionaries\\" + dictionary + ".txt");
        int l = r.nextInt(Files.readAllLines(Paths.get(String.valueOf(file))).size());
        return Files.readAllLines(Paths.get(String.valueOf(file))).get(l);
    }
}
