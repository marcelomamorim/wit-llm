/* @test
   @summary Testa o método region de java.util.regex.Matcher para cobrir exceções de IndexOutOfBoundsException.
   @run main MatcherWitupTest_P7f9354365968
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_P7f9354365968 {
    public static void main(String[] args) throws Exception {
        testRegionStartNegative();
        testRegionStartGreaterThanTextLength();
        testRegionEndNegative();
        testRegionEndGreaterThanTextLength();
        testRegionStartGreaterThanEnd();
    }

    private static void testRegionStartNegative() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcde");
        try {
            matcher.region(-1, 3);
            throw new AssertionError("Expected IndexOutOfBoundsException for start = -1");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testRegionStartGreaterThanTextLength() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcde");
        try {
            matcher.region(10, 12);
            throw new AssertionError("Expected IndexOutOfBoundsException for start > text length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testRegionEndNegative() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcde");
        try {
            matcher.region(0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for end = -1");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testRegionEndGreaterThanTextLength() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcde");
        try {
            matcher.region(0, 10);
            throw new AssertionError("Expected IndexOutOfBoundsException for end > text length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testRegionStartGreaterThanEnd() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcde");
        try {
            matcher.region(4, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for start > end");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
