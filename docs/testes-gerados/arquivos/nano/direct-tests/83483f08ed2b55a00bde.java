/* @test    @summary Test java.util.regex.Matcher.find(int start) method for various scenarios    @run main MatcherWitupTest */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testFindWithValidStart();
        testFindWithNegativeStart();
        testFindWithStartBeyondTextLength();
        testFindWithStartAtTextEnd();
        testFindWithStartBeforeText();
    }

    static void testFindWithValidStart() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("xxabcxx");
        boolean result = matcher.find(2);
        if (result != true) {
            throw new AssertionError("Expected find(2) to return true");
        }
        if (matcher.start() != 2 || matcher.end() != 5) {
            throw new AssertionError("Unexpected match positions: start=" + matcher.start() + ", end=" + matcher.end());
        }
    }

    static void testFindWithNegativeStart() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("xxabcxx");
        try {
            matcher.find(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for start=-1");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testFindWithStartBeyondTextLength() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("xxabcxx");
        int textLength = matcher.regionStart();
        int limit = matcher.regionEnd();
        int textLen = matcher.regionEnd();
        int textSize = matcher.regionEnd();
        // getTextLength() is not public, so we simulate by using regionEnd()
        int textLength = 8; // length of "xxabcxx"
        try {
            matcher.find(9);
            throw new AssertionError("Expected IndexOutOfBoundsException for start=9");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testFindWithStartAtTextEnd() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("xxabcxx");
        boolean result = matcher.find(5); // start at position 5, which is 'x'
        if (result != false) {
            throw new AssertionError("Expected find(5) to return false");
        }
    }

    static void testFindWithStartBeforeText() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("xxabcxx");
        try {
            matcher.find(-2);
            throw new AssertionError("Expected IndexOutOfBoundsException for start=-2");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
