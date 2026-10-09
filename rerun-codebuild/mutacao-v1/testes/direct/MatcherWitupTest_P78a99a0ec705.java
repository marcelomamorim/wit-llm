/*
 * @test
 * @summary Test for Matcher.group(String name) method behavior.
 * @run main MatcherWitupTest_P78a99a0ec705
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_P78a99a0ec705 {
    public static void main(String[] args) {
        testGroupWithExistingNamedGroup();
        testGroupWithNonExistingNamedGroup();
        testGroupWithEmptyPattern();
        testGroupWithNullName();
    }

    private static void testGroupWithExistingNamedGroup() {
        String patternStr = "(?<name>abc)";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher("abc");
        if (!matcher.matches()) {
            throw new AssertionError("Expected matcher to match input.");
        }
        String result = matcher.group("name");
        if (result == null || !result.equals("abc")) {
            throw new AssertionError("Expected group "name" to be 'abc', but got: " + result);
        }
    }

    private static void testGroupWithNonExistingNamedGroup() {
        String patternStr = "(?<foo>abc)";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher("abc");
        if (!matcher.matches()) {
            throw new AssertionError("Expected matcher to match input.");
        }
        String result = matcher.group("nonexistent");
        if (result != null) {
            throw new AssertionError("Expected null for non-existing group, but got: " + result);
        }
    }

    private static void testGroupWithEmptyPattern() {
        String patternStr = "";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher("");
        if (!matcher.matches()) {
            throw new AssertionError("Expected empty pattern to match empty input.");
        }
        String result = matcher.group("name");
        if (result != null) {
            throw new AssertionError("Expected null for group in empty pattern, but got: " + result);
        }
    }

    private static void testGroupWithNullName() {
        String patternStr = "abc";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher("abc");
        if (!matcher.matches()) {
            throw new AssertionError("Expected matcher to match input.");
        }
        try {
            matcher.group(null);
            throw new AssertionError("Expected NullPointerException when passing null as group name.");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
