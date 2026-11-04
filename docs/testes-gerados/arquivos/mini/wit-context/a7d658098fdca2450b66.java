/*
 * @test
 * @summary Test java.util.regex.Matcher.start() method behavior including exception path
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testStartReturnsCorrectIndex();
        testStartThrowsIllegalStateExceptionIfNoMatch();
    }

    private static void testStartReturnsCorrectIndex() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("abc");
        boolean found = m.find();
        if (!found) {
            throw new RuntimeException("Setup failure: expected to find a match");
        }
        int start = m.start();
        if (start != 0) {
            throw new RuntimeException("Expected start() to return 0 but got " + start);
        }
    }

    private static void testStartThrowsIllegalStateExceptionIfNoMatch() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("bc");
        // No match attempted, so start() should throw IllegalStateException
        boolean threw = false;
        try {
            m.start();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException when calling start() without a successful match");
        }
    }
}
