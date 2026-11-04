/*
 * @test
 * @summary Test java.util.regex.Matcher.region(int,int) method for valid and invalid inputs
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testValidRegion();
        testRegionStartNegative();
        testRegionStartGreaterThanTextLength();
        testRegionEndNegative();
        testRegionEndGreaterThanTextLength();
        testRegionStartGreaterThanEnd();
    }

    private static void testValidRegion() {
        Pattern p = Pattern.compile("a*b");
        Matcher m = p.matcher("aaab");
        m.region(1, 4);
        if (m.regionStart() != 1 || m.regionEnd() != 4) {
            throw new RuntimeException("region() did not set regionStart or regionEnd correctly");
        }
    }

    private static void testRegionStartNegative() {
        Pattern p = Pattern.compile("a*b");
        Matcher m = p.matcher("aaab");
        try {
            m.region(-1, 3);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative start");
        } catch (IndexOutOfBoundsException e) {
            if (!"start".equals(e.getMessage())) {
                throw new RuntimeException("Exception message mismatch for negative start");
            }
        }
    }

    private static void testRegionStartGreaterThanTextLength() {
        Pattern p = Pattern.compile("a*b");
        Matcher m = p.matcher("aaab");
        int len = m.regionEnd();
        try {
            m.region(len + 1, len + 2);
            throw new RuntimeException("Expected IndexOutOfBoundsException for start > text length");
        } catch (IndexOutOfBoundsException e) {
            if (!"start".equals(e.getMessage())) {
                throw new RuntimeException("Exception message mismatch for start > text length");
            }
        }
    }

    private static void testRegionEndNegative() {
        Pattern p = Pattern.compile("a*b");
        Matcher m = p.matcher("aaab");
        try {
            m.region(0, -1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative end");
        } catch (IndexOutOfBoundsException e) {
            if (!"end".equals(e.getMessage())) {
                throw new RuntimeException("Exception message mismatch for negative end");
            }
        }
    }

    private static void testRegionEndGreaterThanTextLength() {
        Pattern p = Pattern.compile("a*b");
        Matcher m = p.matcher("aaab");
        int len = m.regionEnd();
        try {
            m.region(0, len + 1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for end > text length");
        } catch (IndexOutOfBoundsException e) {
            if (!"end".equals(e.getMessage())) {
                throw new RuntimeException("Exception message mismatch for end > text length");
            }
        }
    }

    private static void testRegionStartGreaterThanEnd() {
        Pattern p = Pattern.compile("a*b");
        Matcher m = p.matcher("aaab");
        try {
            m.region(3, 2);
            throw new RuntimeException("Expected IndexOutOfBoundsException for start > end");
        } catch (IndexOutOfBoundsException e) {
            if (!"start > end".equals(e.getMessage())) {
                throw new RuntimeException("Exception message mismatch for start > end");
            }
        }
    }
}
