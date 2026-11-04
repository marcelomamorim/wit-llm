/* @test    @summary Test for Matcher.end(String name) method behavior.    @run main MatcherWitupTest */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testEndWithValidGroupName();
        testEndWithInvalidGroupName();
        testEndWithNullGroupName();
    }

    private static void testEndWithValidGroupName() {
        Pattern pattern = Pattern.compile("(?<g1>foo)(?<g2>bar)");
        Matcher matcher = pattern.matcher("foobar");
        boolean found = matcher.find();
        if (!found) {
            throw new AssertionError("Expected to find a match.");
        }
        int endIndexG1 = matcher.end("g1");
        int endIndexG2 = matcher.end("g2");
        if (endIndexG1 != 3) {
            throw new AssertionError("Expected end index of group 'g1' to be 3, but was " + endIndexG1);
        }
        if (endIndexG2 != 6) {
            throw new AssertionError("Expected end index of group 'g2' to be 6, but was " + endIndexG2);
        }
    }

    private static void testEndWithInvalidGroupName() {
        Pattern pattern = Pattern.compile("(foo)(bar)");
        Matcher matcher = pattern.matcher("foobar");
        boolean found = matcher.find();
        if (!found) {
            throw new AssertionError("Expected to find a match.");
        }
        try {
            matcher.end("nonexistent");
            throw new AssertionError("Expected IllegalArgumentException for invalid group name.");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testEndWithNullGroupName() {
        Pattern pattern = Pattern.compile("(foo)(bar)");
        Matcher matcher = pattern.matcher("foobar");
        boolean found = matcher.find();
        if (!found) {
            throw new AssertionError("Expected to find a match.");
        }
        try {
            matcher.end(null);
            throw new AssertionError("Expected NullPointerException for null group name.");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
