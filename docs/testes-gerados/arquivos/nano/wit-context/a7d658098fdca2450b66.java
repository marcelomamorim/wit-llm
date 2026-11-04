/*
 * @test
 * @summary Test for java.util.regex.Matcher.start() method.
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testStartWithMatch();
        testStartWithoutMatch();
    }

    private static void testStartWithMatch() {
        Pattern pattern = Pattern.compile("a*");
        Matcher matcher = pattern.matcher("aaa");
        if (matcher.find()) {
            int startIndex = matcher.start();
            if (startIndex != 0) {
                throw new AssertionError("Expected start() to return 0, but got " + startIndex);
            }
        } else {
            throw new AssertionError("Expected matcher to find a match.");
        }
    }

    private static void testStartWithoutMatch() {
        Pattern pattern = Pattern.compile("b+");
        Matcher matcher = pattern.matcher("aaa");
        try {
            matcher.start();
            throw new AssertionError("Expected IllegalStateException when calling start() without match.");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
