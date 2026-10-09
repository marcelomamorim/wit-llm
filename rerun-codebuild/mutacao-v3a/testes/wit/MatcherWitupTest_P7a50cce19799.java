/*
 * @test
 * @summary Tests the region(int, int) method of java.util.regex.Matcher.
 * @run main MatcherWitupTest_P7a50cce19799
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_P7a50cce19799 {
    public static void main(String[] args) {
        testValidRegion();
        testStartNegative();
        testStartExceedsLength();
        testEndNegative();
        testEndExceedsLength();
        testStartGreaterThanEnd();
    }
    
    private static void testValidRegion() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcdef");
        try {
            matcher.region(1, 4);
            if (matcher.regionStart() != 1 || matcher.regionEnd() != 4) {
                throw new AssertionError("Valid region test failed");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid region test", e);
        }
    }
    
    private static void testStartNegative() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcdef");
        try {
            matcher.region(-1, 3);
            throw new AssertionError("Expected IndexOutOfBoundsException for start negative");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
    }
    
    private static void testStartExceedsLength() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcdef");
        try {
            matcher.region(7, 8);
            throw new AssertionError("Expected IndexOutOfBoundsException for start exceeding length");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
    }
    
    private static void testEndNegative() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcdef");
        try {
            matcher.region(2, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for end negative");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
    }
    
    private static void testEndExceedsLength() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcdef");
        try {
            matcher.region(2, 10);
            throw new AssertionError("Expected IndexOutOfBoundsException for end exceeding length");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
    }
    
    private static void testStartGreaterThanEnd() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcdef");
        try {
            matcher.region(4, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for start > end");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        }
    }
}
