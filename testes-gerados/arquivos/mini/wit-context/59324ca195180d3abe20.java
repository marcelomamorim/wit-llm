/*
 * @test
 * @summary Test Matcher.region(int, int) for boundary checks and correct region setting
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testRegionValidBounds();
        testRegionStartLessThanZero();
        testRegionStartGreaterThanTextLength();
        testRegionEndLessThanZero();
        testRegionEndGreaterThanTextLength();
        testRegionStartGreaterThanEnd();
    }

    private static void testRegionValidBounds() {
        Pattern p = Pattern.compile("abc");
        Matcher m = p.matcher("abc");
        m.region(0, 3);
        if (m.regionStart() != 0 || m.regionEnd() != 3) {
            throw new AssertionError("region() did not set regionStart or regionEnd correctly");
        }
    }

    private static void testRegionStartLessThanZero() {
        Pattern p = Pattern.compile("abc");
        Matcher m = p.matcher("abc");
        try {
            m.region(-1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for start < 0");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testRegionStartGreaterThanTextLength() {
        Pattern p = Pattern.compile("abc");
        Matcher m = p.matcher("abc");
        int textLength = m.inputLength();
        try {
            m.region(textLength + 1, textLength + 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for start > text length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testRegionEndLessThanZero() {
        Pattern p = Pattern.compile("abc");
        Matcher m = p.matcher("abc");
        try {
            m.region(0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for end < 0");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testRegionEndGreaterThanTextLength() {
        Pattern p = Pattern.compile("abc");
        Matcher m = p.matcher("abc");
        int textLength = m.inputLength();
        try {
            m.region(0, textLength + 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for end > text length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testRegionStartGreaterThanEnd() {
        Pattern p = Pattern.compile("abc");
        Matcher m = p.matcher("abc");
        try {
            m.region(2, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for start > end");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
