/*
 * @test
 * @summary Test the end(String name) method of java.util.regex.Matcher for various group names.
 * @run main MatcherWitupTest_P7913f03a62de
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_P7913f03a62de {
    public static void main(String[] args) {
        testEndWithValidGroupName();
        testEndWithInvalidGroupName();
        testEndWithNullGroupName();
    }

    private static void testEndWithValidGroupName() {
        Pattern pattern = Pattern.compile("(?<foo>abc)(?<bar>def)");
        Matcher matcher = pattern.matcher("abcdef");
        if (!matcher.find()) {
            throw new AssertionError("Failed to find match in testEndWithValidGroupName");
        }
        int endFoo = matcher.end("foo");
        int endBar = matcher.end("bar");
        if (endFoo != 3) {
            throw new AssertionError("Expected end of group 'foo' to be 3, but got " + endFoo);
        }
        if (endBar != 6) {
            throw new AssertionError("Expected end of group 'bar' to be 6, but got " + endBar);
        }
    }

    private static void testEndWithInvalidGroupName() {
        Pattern pattern = Pattern.compile("(abc)");
        Matcher matcher = pattern.matcher("abc");
        if (!matcher.find()) {
            throw new AssertionError("Failed to find match in testEndWithInvalidGroupName");
        }
        try {
            matcher.end("nonexistent");
            throw new AssertionError("Expected IllegalArgumentException for invalid group name");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testEndWithNullGroupName() {
        Pattern pattern = Pattern.compile("(abc)");
        Matcher matcher = pattern.matcher("abc");
        if (!matcher.find()) {
            throw new AssertionError("Failed to find match in testEndWithNullGroupName");
        }
        try {
            matcher.end(null);
            throw new AssertionError("Expected NullPointerException for null group name");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
