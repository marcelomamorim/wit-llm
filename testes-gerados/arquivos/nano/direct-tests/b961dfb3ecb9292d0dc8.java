/* @test    @summary Test for Matcher.start() method behavior when no match is available    @run main MatcherWitupTest */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testStartWithoutMatch();
        testStartWithMatch();
    }

    static void testStartWithoutMatch() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("def");
        boolean found = matcher.find();
        if (found) {
            throw new AssertionError("Expected no match, but found one.");
        }
        try {
            matcher.start();
            throw new AssertionError("Expected IllegalStateException when calling start() without match.");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }

    static void testStartWithMatch() {
        Pattern pattern = Pattern.compile("a.");
        Matcher matcher = pattern.matcher("abc");
        boolean found = matcher.find();
        if (!found) {
            throw new AssertionError("Expected a match, but none found.");
        }
        int startIndex = matcher.start();
        if (startIndex != 0) {
            throw new AssertionError("Expected start index 0, but got " + startIndex);
        }
    }
}
