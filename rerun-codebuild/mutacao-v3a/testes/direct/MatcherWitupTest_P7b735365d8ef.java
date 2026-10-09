/*
 * @test
 * @summary Test the end(String name) method of java.util.regex.Matcher.
 * @run main MatcherWitupTest_P7b735365d8ef
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_P7b735365d8ef {

    public static void main(String[] args) {
        testEndWithExistingGroupName();
        testEndWithNonExistingGroupName();
    }

    static void testEndWithExistingGroupName() {
        Pattern pattern = Pattern.compile("(?<g1>a)(b)");
        Matcher matcher = pattern.matcher("ab");
        if (matcher.find()) {
            int endIndex = matcher.end("g1");
            assert endIndex == 1 : "Expected end index 1 for group 'g1', but got " + endIndex;
        } else {
            throw new AssertionError("Matcher did not find a match.");
        }
    }

    static void testEndWithNonExistingGroupName() {
        Pattern pattern = Pattern.compile("(a)(b)");
        Matcher matcher = pattern.matcher("ab");
        if (matcher.find()) {
            try {
                matcher.end("nonexistent");
                throw new AssertionError("Expected IllegalArgumentException for non-existing group name.");
            } catch (IllegalArgumentException e) {
                // Expected exception
            }
        } else {
            throw new AssertionError("Matcher did not find a match.");
        }
    }
}
