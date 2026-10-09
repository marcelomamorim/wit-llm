/*
 * @test
 * @summary Tests for java.util.regex.Matcher.region(int, int)
 * @run main MatcherWitupTest_Pc41703e4f53c
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_Pc41703e4f53c {

    public static void main(String[] args) {
        testRegionValidRange();
        testRegionStartNegative();
        testRegionStartExceedsLength();
        testRegionEndNegative();
        testRegionEndExceedsLength();
        testRegionStartGreaterThanEnd();
    }

    static void testRegionValidRange() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcabc");
        matcher.region(0, 3);
        if (matcher.regionStart() != 0 || matcher.regionEnd() != 3) {
            throw new AssertionError("Valid range test failed");
        }
    }

    static void testRegionStartNegative() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcabc");
        try {
            matcher.region(-1, 3);
            throw new AssertionError("Expected IndexOutOfBoundsException for start negative");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testRegionStartExceedsLength() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcabc");
        try {
            matcher.region(10, 12);
            throw new AssertionError("Expected IndexOutOfBoundsException for start exceeding length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testRegionEndNegative() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcabc");
        try {
            matcher.region(0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for end negative");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testRegionEndExceedsLength() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcabc");
        try {
            matcher.region(0, 20);
            throw new AssertionError("Expected IndexOutOfBoundsException for end exceeding length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testRegionStartGreaterThanEnd() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcabc");
        try {
            matcher.region(4, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for start > end");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
