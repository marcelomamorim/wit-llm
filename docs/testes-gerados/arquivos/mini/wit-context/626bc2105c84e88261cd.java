/*
 * @test
 * @summary Test java.util.regex.Matcher.end() method behavior including exception on no match
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

    private static void testEndWithoutMatch() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("b");
        // No match attempted, end() should throw IllegalStateException
        try {
            m.end();
            throw new RuntimeException("Expected IllegalStateException when calling end() without a match");
        } catch (IllegalStateException ise) {
            // Expected exception
        }
    }
}
