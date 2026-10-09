/* @test
   @summary Verifies the behavior of Matcher.region method with valid and invalid inputs.
   @run main MatcherWitupTest_P7f9354365968
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Objects;

public class MatcherWitupTest_P7f9354365968 {
    public static void main(String[] args) {
        testRegionValid();
        testRegionInvalidStartNegative();
        testRegionInvalidStartExceedsTextLength();
        testRegionInvalidEndNegative();
        testRegionInvalidEndExceedsTextLength();
        testRegionStartGreaterThanEnd();
    }

    private static void testRegionValid() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcde");
        matcher.region(1, 3);
        if (matcher.regionStart() != 1 || matcher.regionEnd() != 3) {
            throw new AssertionError("Valid region not set correctly");
        }
        System.out.println("testRegionValid passed");
    }

    private static void testRegionInvalidStartNegative() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcde");
        try {
            matcher.region(-1, 3);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative start");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        System.out.println("testRegionInvalidStartNegative passed");
    }

    private static void testRegionInvalidStartExceedsTextLength() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcde");
        try {
            matcher.region(6, 8);
            throw new AssertionError("Expected IndexOutOfBoundsException for start exceeding text length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        System.out.println("testRegionInvalidStartExceedsTextLength passed");
    }

    private static void testRegionInvalidEndNegative() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcde");
        try {
            matcher.region(1, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative end");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        System.out.println("testRegionInvalidEndNegative passed");
    }

    private static void testRegionInvalidEndExceedsTextLength() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcde");
        try {
            matcher.region(1, 10);
            throw new AssertionError("Expected IndexOutOfBoundsException for end exceeding text length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        System.out.println("testRegionInvalidEndExceedsTextLength passed");
    }

    private static void testRegionStartGreaterThanEnd() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcde");
        matcher.region(3, 2);
        if (matcher.regionStart() != 3 || matcher.regionEnd() != 2) {
            throw new AssertionError("Region start and end not set correctly when start > end");
        }
        System.out.println("testRegionStartGreaterThanEnd passed");
    }
}
