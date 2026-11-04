/* @test    @summary Test for Matcher.start() method in java.util.regex.Matcher    @run main MatcherWitupTest */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testStartMethodWithMatch();
        testStartMethodWithoutMatch();
    }

    static void testStartMethodWithMatch() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abc");
        if (matcher.find()) {
            int startIndex = matcher.start();
            if (startIndex != 0) {
                throw new AssertionError("Expected start index 0, but got " + startIndex);
            }
        } else {
            throw new AssertionError("Expected matcher to find a match.");
        }
    }

    static void testStartMethodWithoutMatch() {
        Pattern pattern = Pattern.compile("xyz");
        Matcher matcher = pattern.matcher("abc");
        if (matcher.find()) {
            throw new AssertionError("Did not expect matcher to find a match.");
        } else {
            try {
                matcher.start();
                throw new AssertionError("Expected IllegalStateException when calling start() without match.");
            } catch (IllegalStateException e) {
                // Expected exception
            }
        }
    }
}
