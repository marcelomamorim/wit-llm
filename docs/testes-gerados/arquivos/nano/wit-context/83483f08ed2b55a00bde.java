/*
 * @test
 * @summary Tests for java.util.regex.Matcher.find(int start)
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testFindWithValidStart();
        testFindWithNegativeStart();
        testFindWithStartBeyondLength();
    }

    private static void testFindWithValidStart() {
        Pattern pattern = Pattern.compile("\d+");
        Matcher matcher = pattern.matcher("abc123xyz");
        boolean result = matcher.find(3);
        if (!result) {
            throw new AssertionError("Expected find(3) to return true");
        }
    }

    private static void testFindWithNegativeStart() {
        Pattern pattern = Pattern.compile("\d+");
        Matcher matcher = pattern.matcher("abc123xyz");
        try {
            matcher.find(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for find(-1)");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testFindWithStartBeyondLength() {
        Pattern pattern = Pattern.compile("\d+");
        Matcher matcher = pattern.matcher("abc123xyz");
        int length = matcher.regionEnd();
        try {
            matcher.find(length + 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for find(" + (length + 1) + ")");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
