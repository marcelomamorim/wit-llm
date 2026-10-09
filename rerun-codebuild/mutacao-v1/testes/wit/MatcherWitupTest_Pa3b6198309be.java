/* @test    @summary Tests for java.util.regex.Matcher.region(int, int) method handling boundary conditions and exceptions    @run main MatcherWitupTest_Pa3b6198309be */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_Pa3b6198309be {

    public static void main(String[] args) {
        testRegionValid();
        testRegionStartNegative();
        testRegionStartExceedsLength();
        testRegionEndNegative();
        testRegionEndExceedsLength();
        testRegionStartGreaterThanEnd();
        System.out.println("All tests passed.");
    }

    private static void testRegionValid() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcde");
        try {
            matcher.region(1, 3);
            if (matcher.regionStart() != 1 || matcher.regionEnd() != 3) {
                throw new AssertionError("Failed: valid region setting");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testRegionValid", e);
        }
    }

    private static void testRegionStartNegative() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcde");
        boolean thrown = false;
        try {
            matcher.region(-1, 3);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for start negative");
        }
    }

    private static void testRegionStartExceedsLength() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcde");
        boolean thrown = false;
        try {
            matcher.region(6, 8);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for start > length");
        }
    }

    private static void testRegionEndNegative() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcde");
        boolean thrown = false;
        try {
            matcher.region(1, -2);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for end negative");
        }
    }

    private static void testRegionEndExceedsLength() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcde");
        boolean thrown = false;
        try {
            matcher.region(2, 10);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for end > length");
        }
    }

    private static void testRegionStartGreaterThanEnd() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcde");
        boolean thrown = false;
        try {
            matcher.region(4, 2);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for start > end");
        }
    }
}
