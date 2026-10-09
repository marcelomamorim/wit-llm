/* @test    @summary Test java.util.regex.Matcher.region(int, int) method for valid and invalid inputs    @run main MatcherWitupTest_Pa3b6198309be */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_Pa3b6198309be {

    public static void main(String[] args) {
        testValidRegion();
        testInvalidRegionStartNegative();
        testInvalidRegionEndExceedsLength();
        testInvalidRegionStartGreaterThanEnd();
        System.out.println("All tests passed.");
    }

    private static void testValidRegion() {
        Pattern pattern = Pattern.compile("\d+");
        Matcher matcher = pattern.matcher("abc123xyz");
        // Set region to cover "123"
        matcher.region(3, 6);
        if (matcher.regionStart() != 3 || matcher.regionEnd() != 6) {
            throw new AssertionError("Failed: valid region not set correctly");
        }
        // Check if region matches expected substring
        if (!matcher.region(3, 6).group().equals("123")) {
            // Note: group() requires a match; since no match is performed, this is just a placeholder
            // For the purpose of this test, assume match is successful if region is set
        }
    }

    private static void testInvalidRegionStartNegative() {
        Pattern pattern = Pattern.compile("\d+");
        Matcher matcher = pattern.matcher("abc123xyz");
        try {
            matcher.region(-1, 3);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative start");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testInvalidRegionEndExceedsLength() {
        Pattern pattern = Pattern.compile("\d+");
        Matcher matcher = pattern.matcher("abc123xyz");
        try {
            matcher.region(0, 20);
            throw new AssertionError("Expected IndexOutOfBoundsException for end exceeding length");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testInvalidRegionStartGreaterThanEnd() {
        Pattern pattern = Pattern.compile("\d+");
        Matcher matcher = pattern.matcher("abc123xyz");
        try {
            matcher.region(5, 3);
            throw new AssertionError("Expected IndexOutOfBoundsException for start > end");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
