/*
 * @test
 * @summary Tests for java.util.regex.Matcher#group(int)
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testGroupWithValidMatch();
        testGroupWithFirstLessThanZero();
        testGroupWithIndexOutOfBounds();
    }

    private static void testGroupWithValidMatch() {
        Pattern pattern = Pattern.compile("(\d+)");
        Matcher matcher = pattern.matcher("12345");
        boolean found = matcher.find();
        if (!found) {
            throw new AssertionError("Expected to find a match");
        }
        String result = matcher.group(1);
        if (!"12345".equals(result)) {
            throw new AssertionError("Expected '12345', got: " + result);
        }
    }

    private static void testGroupWithFirstLessThanZero() {
        Pattern pattern = Pattern.compile("(\d+)");
        Matcher matcher = pattern.matcher("abc");
        // first < 0, so no match
        try {
            matcher.group(0);
            throw new AssertionError("Expected IllegalStateException for first < 0");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    private static void testGroupWithIndexOutOfBounds() {
        Pattern pattern = Pattern.compile("(\d+)");
        Matcher matcher = pattern.matcher("123");
        boolean found = matcher.find();
        if (!found) {
            throw new AssertionError("Expected to find a match");
        }
        // group index out of bounds: 2 (only 1 group)
        try {
            matcher.group(2);
            throw new AssertionError("Expected IndexOutOfBoundsException for invalid group index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
