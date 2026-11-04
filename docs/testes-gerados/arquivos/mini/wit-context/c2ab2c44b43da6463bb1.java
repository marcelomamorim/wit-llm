/*
 * @test
 * @summary Test java.util.regex.Matcher.end() method behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testEndWithoutMatch();
        testEndAfterMatch();
    }

    private static void testEndWithoutMatch() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("");
        // No match attempted, first < 0, expect IllegalStateException
        try {
            m.end();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException ise) {
            // Expected exception
        }
    }

    private static void testEndAfterMatch() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("a");
        boolean found = m.find();
        if (!found) {
            throw new RuntimeException("Setup failure: expected to find a match");
        }
        int end = m.end();
        if (end != 1) {
            throw new RuntimeException("Expected end() to return 1 but got " + end);
        }
    }
}
