/* @test    @summary Test the region method of java.util.regex.Matcher    @run main MatcherWitupTest */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testValidRegion();
        testRegionStartNegative();
        testRegionEndNegative();
        testRegionStartGreaterThanEnd();
        testRegionStartExceedsTextLength();
        testRegionEndExceedsTextLength();
    }

    static void testValidRegion() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("xxabcxx");
        matcher.region(2, 5);
        assert matcher.regionStart() == 2 : "Expected region start to be 2";
        assert matcher.regionEnd() == 5 : "Expected region end to be 5";
    }

    static void testRegionStartNegative() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("xxabcxx");
        try {
            matcher.region(-1, 3);
            assert false : "Expected IndexOutOfBoundsException for negative start";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testRegionEndNegative() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("xxabcxx");
        try {
            matcher.region(0, -1);
            assert false : "Expected IndexOutOfBoundsException for negative end";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testRegionStartGreaterThanEnd() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("xxabcxx");
        try {
            matcher.region(4, 2);
            assert false : "Expected IndexOutOfBoundsException for start > end";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testRegionStartExceedsTextLength() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("xxabcxx");
        int textLength = matcher.region(0, 0).regionEnd();
        try {
            matcher.region(textLength + 1, textLength + 2);
            assert false : "Expected IndexOutOfBoundsException for start > text length";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testRegionEndExceedsTextLength() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("xxabcxx");
        int textLength = matcher.region(0, 0).regionEnd();
        try {
            matcher.region(0, textLength + 1);
            assert false : "Expected IndexOutOfBoundsException for end > text length";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
