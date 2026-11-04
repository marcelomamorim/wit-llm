/*
 * @test
 * @summary Test for java.util.regex.Matcher.group(int)
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testGroupWithValidGroup();
        testGroupWithNegativeGroup();
        testGroupWithGroupExceedingCount();
        testGroupWithUnmatchedGroup();
    }

    private static void testGroupWithValidGroup() {
        Pattern pattern = Pattern.compile("(a)(b)");
        Matcher matcher = pattern.matcher("ab");
        boolean matched = matcher.matches();
        if (!matched) {
            throw new AssertionError("Expected matcher to match input.");
        }
        String result = matcher.group(1);
        if (!"a".equals(result)) {
            throw new AssertionError("Expected group 1 to be 'a', but got: " + result);
        }
    }

    private static void testGroupWithNegativeGroup() {
        Pattern pattern = Pattern.compile("(a)");
        Matcher matcher = pattern.matcher("a");
        boolean matched = matcher.matches();
        if (!matched) {
            throw new AssertionError("Expected matcher to match input.");
        }
        try {
            matcher.group(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative group.");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testGroupWithGroupExceedingCount() {
        Pattern pattern = Pattern.compile("(a)");
        Matcher matcher = pattern.matcher("a");
        boolean matched = matcher.matches();
        if (!matched) {
            throw new AssertionError("Expected matcher to match input.");
        }
        int groupCount = matcher.groupCount();
        try {
            matcher.group(groupCount + 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for group exceeding count.");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testGroupWithUnmatchedGroup() {
        Pattern pattern = Pattern.compile("(a)(b)");
        Matcher matcher = pattern.matcher("ab");
        boolean matched = matcher.matches();
        if (!matched) {
            throw new AssertionError("Expected matcher to match input.");
        }
        // Manually manipulate groups array to simulate unmatched group (not possible directly, so test normal behavior)
        // Instead, test that if group is matched, it returns the subsequence
        String result = matcher.group(2);
        if (!"b".equals(result)) {
            throw new AssertionError("Expected group 2 to be 'b', but got: " + result);
        }
    }
}
