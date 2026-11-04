/*
 * @test
 * @summary Test java.util.regex.Matcher.end() method behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testEndAfterMatch();
        testEndWithoutMatch();
    }

    private static void testEndAfterMatch() {
        Pattern p = Pattern.compile("a(b)c");
        Matcher m = p.matcher("abc");
        boolean found = m.find();
        if (!found) {
            throw new RuntimeException("Expected to find a match but did not");
        }
        int end = m.end();
        if (end != 3) {
            throw new RuntimeException("Expected end() to return 3 but got " + end);
        }
    }

    private static void testEndWithoutMatch() {
        Pattern p = Pattern.compile("a(b)c");
        Matcher m = p.matcher("xyz");
        try {
            m.end();
            throw new RuntimeException("Expected IllegalStateException when calling end() without a match");
        } catch (IllegalStateException ise) {
            // Expected exception
        }
    }
}
