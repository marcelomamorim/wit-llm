/*
 * @test
 * @summary Tests for java.util.regex.Matcher.end(int)
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testEndWithValidGroup();
        testEndWithFirstLessThanZero();
        testEndWithGroupOutOfRange();
    }

    private static void testEndWithValidGroup() {
        Pattern pattern = Pattern.compile("(a)(b)");
        Matcher matcher = pattern.matcher("ab");
        boolean found = matcher.find();
        if (!found) {
            throw new AssertionError("Expected to find a match");
        }
        int endIndex = matcher.end(1);
        if (endIndex != 1) {
            throw new AssertionError("Expected end index 1 for group 1, but got " + endIndex);
        }
        endIndex = matcher.end(2);
        if (endIndex != 2) {
            throw new AssertionError("Expected end index 2 for group 2, but got " + endIndex);
        }
    }

    private static void testEndWithFirstLessThanZero() {
        Pattern pattern = Pattern.compile("a");
        Matcher matcher = pattern.matcher("a");
        boolean found = matcher.find();
        if (!found) {
            throw new AssertionError("Expected to find a match");
        }
        try {
            matcher.end(-1);
            throw new AssertionError("Expected IllegalStateException for first < 0");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    private static void testEndWithGroupOutOfRange() {
        Pattern pattern = Pattern.compile("(a)");
        Matcher matcher = pattern.matcher("a");
        boolean found = matcher.find();
        if (!found) {
            throw new AssertionError("Expected to find a match");
        }
        try {
            matcher.end(2); // group index out of range
            throw new AssertionError("Expected IndexOutOfBoundsException for group out of range");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
