/*
 * @test
 * @summary Tests for java.util.regex.Matcher.group(String)
 * @run main MatcherWitupTest_P91d91cf5b0a5
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_P91d91cf5b0a5 {

    public static void main(String[] args) {
        testGroupWithValidName();
        testGroupWithNonMatchingName();
        testGroupWithNullName();
        testGroupWithInvalidGroupName();
    }

    public static void testGroupWithValidName() {
        Pattern pattern = Pattern.compile("(?<name>abc)");
        Matcher matcher = pattern.matcher("abc");
        if (matcher.matches()) {
            String result = matcher.group("name");
            if (!"abc".equals(result)) {
                throw new AssertionError("Expected 'abc', got: " + result);
            }
        } else {
            throw new AssertionError("Matcher did not match the input.");
        }
    }

    public static void testGroupWithNonMatchingName() {
        Pattern pattern = Pattern.compile("def");
        Matcher matcher = pattern.matcher("abc");
        if (matcher.matches()) {
            try {
                matcher.group("name");
                throw new AssertionError("Expected IllegalStateException was not thrown.");
            } catch (IllegalStateException e) {
                // Expected exception
            }
        }
    }

    public static void testGroupWithNullName() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abc");
        if (matcher.matches()) {
            try {
                matcher.group(null);
                throw new AssertionError("Expected NullPointerException was not thrown.");
            } catch (NullPointerException e) {
                // Expected exception
            }
        }
    }

    public static void testGroupWithInvalidGroupName() {
        Pattern pattern = Pattern.compile("(?<name>abc)");
        Matcher matcher = pattern.matcher("abc");
        if (matcher.matches()) {
            try {
                matcher.group("invalid");
                throw new AssertionError("Expected IllegalArgumentException was not thrown.");
            } catch (IllegalArgumentException e) {
                // Expected exception
            }
        }
    }
}
