/* @test    @summary Test the region method of Matcher for valid and invalid inputs    @run main MatcherWitupTest_P7a50cce19799 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_P7a50cce19799 {

    public static void main(String[] args) {
        testValidRegion();
        testInvalidRegionStartNegative();
        testInvalidRegionEndExceedsLength();
        testStartGreaterThanEnd();
    }

    static void testValidRegion() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("xxabcxx");
        int start = 2;
        int end = 5;
        Matcher resultMatcher = matcher.region(start, end);
        assert resultMatcher != null : "region method returned null";
        assert matcher.regionStart() == start : "Expected regionStart() to be " + start + ", but got " + matcher.regionStart();
        assert matcher.regionEnd() == end : "Expected regionEnd() to be " + end + ", but got " + matcher.regionEnd();
    }

    static void testInvalidRegionStartNegative() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("xxabcxx");
        try {
            matcher.region(-1, 3);
            assert false : "Expected IndexOutOfBoundsException for negative start";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testInvalidRegionEndExceedsLength() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("xxabcxx");
        int length = matcher.getTextLength();
        try {
            matcher.region(0, length + 1);
            assert false : "Expected IndexOutOfBoundsException for end > text length";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testStartGreaterThanEnd() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("xxabcxx");
        try {
            matcher.region(4, 2);
            assert false : "Expected IndexOutOfBoundsException for start > end";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
