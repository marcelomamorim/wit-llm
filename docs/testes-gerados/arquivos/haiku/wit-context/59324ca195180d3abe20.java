/*
 * @test
 * @summary Witup-generated tests for Matcher.region argument validation and region state
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        expectIndexOutOfBounds(-1, 2, "abc");
        expectIndexOutOfBounds(4, 4, "abc");
        expectIndexOutOfBounds(0, -1, "abc");
        expectIndexOutOfBounds(0, 4, "abc");
        expectIndexOutOfBounds(2, 1, "abc");
        testValidRegion(1, 2, "abc");
        testValidRegion(3, 3, "abc");
        testValidRegion(0, 3, "abc");
    }

    static void expectIndexOutOfBounds(int start, int end, String input) {
        Matcher m = Pattern.compile("a").matcher(input);
        try {
            m.region(start, end);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected IndexOutOfBoundsException for region(" + start + ", " + end + ")");
    }

    static void testValidRegion(int start, int end, String input) {
        Matcher m = Pattern.compile("a").matcher(input);
        Matcher result = m.region(start, end);
        if (result != m) {
            throw new RuntimeException("region() must return the same matcher");
        }
        if (m.regionStart() != start) {
            throw new RuntimeException("regionStart mismatch: expected " + start + " but was " + m.regionStart());
        }
        if (m.regionEnd() != end) {
            throw new RuntimeException("regionEnd mismatch: expected " + end + " but was " + m.regionEnd());
        }
    }
}
