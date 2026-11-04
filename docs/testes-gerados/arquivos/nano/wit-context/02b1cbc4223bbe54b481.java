/*
 * @test
 * @summary Tests for java.util.regex.Matcher.end(int group)
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testEndWithValidGroup();
        testEndWithNegativeGroup();
        testEndWithGroupExceeding();
    }

    private static void testEndWithValidGroup() {
        Pattern pattern = Pattern.compile("(a)(b)");
        Matcher matcher = pattern.matcher("ab");
        if (matcher.find()) {
            int end0 = matcher.end(0);
            int end1 = matcher.end(1);
            int end2 = matcher.end(2);
            if (end0 != 2 || end1 != 1 || end2 != 2) {
                throw new AssertionError("Unexpected end positions for valid groups: " + end0 + ", " + end1 + ", " + end2);
            }
        } else {
            throw new AssertionError("Matcher should find a match");
        }
    }

    private static void testEndWithNegativeGroup() {
        Pattern pattern = Pattern.compile("(a)");
        Matcher matcher = pattern.matcher("a");
        if (matcher.find()) {
            try {
                matcher.end(-1);
                throw new AssertionError("Expected IndexOutOfBoundsException for negative group");
            } catch (IndexOutOfBoundsException e) {
                // expected
            }
        } else {
            throw new AssertionError("Matcher should find a match");
        }
    }

    private static void testEndWithGroupExceeding() {
        Pattern pattern = Pattern.compile("(a)");
        Matcher matcher = pattern.matcher("a");
        if (matcher.find()) {
            int groupCount = matcher.groupCount();
            try {
                matcher.end(groupCount + 1);
                throw new AssertionError("Expected IndexOutOfBoundsException for group exceeding groupCount");
            } catch (IndexOutOfBoundsException e) {
                // expected
            }
        } else {
            throw new AssertionError("Matcher should find a match");
        }
    }
}
