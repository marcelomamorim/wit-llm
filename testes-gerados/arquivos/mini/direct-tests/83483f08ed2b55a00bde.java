/*
 * @test
 * @summary Test java.util.regex.Matcher.find(int) method behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testFindWithValidStart();
        testFindWithStartZero();
        testFindWithStartAtTextLength();
        testFindWithNegativeStart();
        testFindWithStartGreaterThanTextLength();
    }

    private static void testFindWithValidStart() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("abcabc");
        boolean found = m.find(1); // start at index 1, should find 'a' at index 3
        if (!found) {
            throw new AssertionError("Expected to find 'a' starting at index 1");
        }
        if (m.start() != 3) {
            throw new AssertionError("Expected match start at 3 but was " + m.start());
        }
    }

    private static void testFindWithStartZero() {
        Pattern p = Pattern.compile("b");
        Matcher m = p.matcher("abcabc");
        boolean found = m.find(0); // start at index 0, should find 'b' at index 1
        if (!found) {
            throw new AssertionError("Expected to find 'b' starting at index 0");
        }
        if (m.start() != 1) {
            throw new AssertionError("Expected match start at 1 but was " + m.start());
        }
    }

    private static void testFindWithStartAtTextLength() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("abc");
        boolean found = m.find(3); // start at index == text length, should not find
        if (found) {
            throw new AssertionError("Expected no match when start == text length");
        }
    }

    private static void testFindWithNegativeStart() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("abc");
        try {
            m.find(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative start");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testFindWithStartGreaterThanTextLength() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("abc");
        try {
            m.find(4); // 4 > text length 3
            throw new AssertionError("Expected IndexOutOfBoundsException for start > text length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
