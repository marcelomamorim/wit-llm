/* @test    @summary Test for java.util.regex.Matcher.end(String) method to verify exception paths and behavior.    @run main MatcherWitupTest_P7913f03a62de */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_P7913f03a62de {

    public static void main(String[] args) {
        testEndWithNullName();
        testEndWithInvalidGroupName();
        testEndWithValidGroupName();
    }

    private static void testEndWithNullName() {
        try {
            // Create a matcher with a simple pattern
            Pattern pattern = Pattern.compile("(a)(b)");
            Matcher matcher = pattern.matcher("ab");
            // Attempt to call end with null, expecting NullPointerException
            matcher.end(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testEndWithInvalidGroupName() {
        try {
            Pattern pattern = Pattern.compile("(?<foo>a)");
            Matcher matcher = pattern.matcher("a");
            if (matcher.find()) {
                // Use a non-existent group name
                matcher.end("nonexistent");
                throw new AssertionError("Expected IllegalArgumentException not thrown");
            } else {
                throw new AssertionError("Matcher did not find a match");
            }
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testEndWithValidGroupName() {
        try {
            Pattern pattern = Pattern.compile("(?<foo>a)(b)");
            Matcher matcher = pattern.matcher("ab");
            if (matcher.find()) {
                int endIndex = matcher.end("foo");
                if (endIndex != 1) {
                    throw new AssertionError("Unexpected end index: " + endIndex);
                }
            } else {
                throw new AssertionError("Matcher did not find a match");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
