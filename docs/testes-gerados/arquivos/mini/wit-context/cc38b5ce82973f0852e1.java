/*
 * @test
 * @summary Test java.util.regex.Matcher.end(String) method for exception and normal cases
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testEndWithNullName();
        testEndWithInvalidName();
        testEndWithValidName();
    }

    private static void testEndWithNullName() {
        Pattern p = Pattern.compile("(a)(?<name>b)");
        Matcher m = p.matcher("ab");
        m.find();
        try {
            m.end(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testEndWithInvalidName() {
        Pattern p = Pattern.compile("(a)(?<name>b)");
        Matcher m = p.matcher("ab");
        m.find();
        try {
            m.end("invalidName");
            throw new RuntimeException("Expected IllegalArgumentException for invalid group name");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testEndWithValidName() {
        Pattern p = Pattern.compile("(a)(?<name>b)");
        Matcher m = p.matcher("ab");
        boolean found = m.find();
        if (!found) {
            throw new RuntimeException("Expected to find a match");
        }
        int endIndex = m.end("name");
        if (endIndex != 2) {
            throw new RuntimeException("Expected end index 2 for group 'name', got " + endIndex);
        }
    }
}
