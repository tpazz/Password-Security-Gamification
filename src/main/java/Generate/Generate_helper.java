package Generate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Generate_helper {

    public static final ArrayList<String> x = new ArrayList<String>() {{
       add("hello");
       add("test");
    }};

    public static final List<String> s = Arrays.asList("hello", "test");

    public static final String LVL1DESC =
            "Passwords are almost never stored as plain-text.\n" +
            "However, it is still a possibility amongst businesses\n" +
            "or other entities that are unaware of the security implications\n" +
            "of storing un-hashed user credentials in an excel file.";

    public static final String LVL2DESC =
            "Brute-force attacks are the most common form of password\n" +
            "cracking. They are extremely effective for breaking into\n" +
            "devices with numerical passwords as they can iterate through\n" +
            "every combination extremely quickly";

    public static final String LVL3DESC =
            "Theoretically, brute-force attacks are guaranteed to find\n" +
            "any password given enough time. However, ";

    public static final String LVL4DESC =
            "Although brute-force attacks are guaranteed to find ";
}
