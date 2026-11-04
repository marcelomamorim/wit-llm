/*
 * @test
 * @summary Test java.util.regex.Matcher.start() method behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testStartAfterMatch();
        testStartWithoutMatch();
    }

    private static void testStartAfterMatch() {
        Pattern p = Pattern.compile("a(b)c");
        Matcher m = p.matcher("abc");
        if (!m.find()) {
            throw new RuntimeException("Expected to find a match");
        }
        int start = m.start();
        if (start != 0) {
            throw new RuntimeException("Expected start() to return 0 but got " + start);
        }
    }

    private static void testStartWithoutMatch() {
        Pattern p = Pattern.compile("a(b)c");
        Matcher m = p.matcher("xyz");
        try {
            m.start();
            throw new RuntimeException("Expected IllegalStateException when calling start() without a match");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
