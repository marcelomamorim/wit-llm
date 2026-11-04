/*
 * @test
 * @summary Tests for java.util.regex.Matcher.start(int group)
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testStartWithValidGroup();
        testStartWithNegativeGroup();
        testStartWithGroupExceedingCount();
    }

    private static void testStartWithValidGroup() {
        Pattern pattern = Pattern.compile("(a)(b)");
        Matcher matcher = pattern.matcher("ab");
        if (matcher.find()) {
            int start0 = matcher.start(0);
            int start1 = matcher.start(1);
            int start2 = matcher.start(2);
            if (start0 != 0 || start1 != 0 || start2 != 1) {
                throw new AssertionError("Unexpected start indices for valid groups: " + start0 + ", " + start1 + ", " + start2);
            }
        } else {
            throw new AssertionError("Matcher did not find a match when expected.");
        }
    }

    private static void testStartWithNegativeGroup() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abc");
        if (matcher.find()) {
            try {
                matcher.start(-1);
                throw new AssertionError("Expected IndexOutOfBoundsException for negative group index.");
            } catch (IndexOutOfBoundsException e) {
                // Expected exception
            }
        } else {
            throw new AssertionError("Matcher did not find a match for negative group test.");
        }
    }

    private static void testStartWithGroupExceedingCount() {
        Pattern pattern = Pattern.compile("(a)(b)");
        Matcher matcher = pattern.matcher("ab");
        if (matcher.find()) {
            int groupCount = matcher.groupCount();
            try {
                matcher.start(groupCount + 1);
                throw new AssertionError("Expected IndexOutOfBoundsException for group index exceeding count.");
            } catch (IndexOutOfBoundsException e) {
                // Expected exception
            }
        } else {
            throw new AssertionError("Matcher did not find a match for group exceeding count test.");
        }
    }
}
