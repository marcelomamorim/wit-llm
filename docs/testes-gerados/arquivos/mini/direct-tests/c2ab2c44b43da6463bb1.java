/*
 * @test
 * @summary Test java.util.regex.Matcher.end() method behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testEndAfterMatch();
        testEndWithoutMatchThrows();
    }

    private static void testEndAfterMatch() {
        Pattern p = Pattern.compile("a(b)c");
        Matcher m = p.matcher("abc");
        if (!m.find()) {
            throw new RuntimeException("Expected to find a match");
        }
        int end = m.end();
        if (end != 3) {
            throw new RuntimeException("Expected end() to return 3 but got " + end);
        }
    }

    private static void testEndWithoutMatchThrows() {
        Pattern p = Pattern.compile("a(b)c");
        Matcher m = p.matcher("abc");
        try {
            m.end();
            throw new RuntimeException("Expected IllegalStateException when calling end() before match");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
