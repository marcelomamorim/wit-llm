/*
 * @test
 * @summary Test java.util.regex.Matcher.start() method behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testStartAfterMatch();
        testStartBeforeMatch();
    }

    private static void testStartAfterMatch() {
        Pattern p = Pattern.compile("a(b)c");
        Matcher m = p.matcher("abc");
        boolean found = m.find();
        if (!found) {
            throw new RuntimeException("Expected to find a match but did not");
        }
        int start = m.start();
        if (start != 0) {
            throw new RuntimeException("Expected start() to return 0 but got " + start);
        }
    }

    private static void testStartBeforeMatch() {
        Pattern p = Pattern.compile("a(b)c");
        Matcher m = p.matcher("abc");
        try {
            m.start();
            throw new RuntimeException("Expected IllegalStateException when calling start() before match");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
