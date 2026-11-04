/* @test    @summary Test for Matcher.end() method behavior when no match is available    @run main MatcherWitupTest */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testEndWithoutMatch();
        testEndWithMatch();
    }

    static void testEndWithoutMatch() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("def");
        boolean found = matcher.find();
        if (found) {
            throw new AssertionError("Expected no match, but found one.");
        }
        try {
            matcher.end();
            throw new AssertionError("Expected IllegalStateException when calling end() without match.");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }

    static void testEndWithMatch() {
        Pattern pattern = Pattern.compile("a.");
        Matcher matcher = pattern.matcher("abc");
        boolean found = matcher.find();
        if (!found) {
            throw new AssertionError("Expected a match, but none found.");
        }
        int endIndex = matcher.end();
        if (endIndex != 2) {
            throw new AssertionError("Expected end index 2, but got " + endIndex);
        }
    }
}
