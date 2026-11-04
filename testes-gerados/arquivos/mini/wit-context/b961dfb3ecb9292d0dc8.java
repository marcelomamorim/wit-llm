/*
 * @test
 * @summary Test java.util.regex.Matcher.start() method behavior including exception on no match
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testStartAfterMatch();
        testStartBeforeMatchThrows();
    }

    private static void testStartAfterMatch() {
        Pattern p = Pattern.compile("a(b)c");
        Matcher m = p.matcher("abc");
        boolean found = m.find();
        if (!found) {
            throw new RuntimeException("Setup failed: pattern did not match input");
        }
        int start = m.start();
        if (start != 0) {
            throw new RuntimeException("Expected start() to return 0 but got " + start);
        }
    }

    private static void testStartBeforeMatchThrows() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("b");
        // No match attempted, so first < 0 internally
        try {
            m.start();
            throw new RuntimeException("Expected IllegalStateException when calling start() before match");
        } catch (IllegalStateException ise) {
            // Expected exception
        }
    }
}
