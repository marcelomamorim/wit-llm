/*
 * @test
 * @summary Test java.util.regex.Matcher.start(int) method behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testStartAfterMatch();
        testStartBeforeMatch();
        testStartInvalidGroupNegative();
        testStartInvalidGroupTooLarge();
    }

    private static void testStartAfterMatch() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher("ab");
        if (!m.find()) {
            throw new RuntimeException("Expected to find a match");
        }
        // group 0 is entire match
        int start0 = m.start(0);
        if (start0 != 0) {
            throw new RuntimeException("Expected start(0) to be 0 but was " + start0);
        }
        // group 1 is 'a'
        int start1 = m.start(1);
        if (start1 != 0) {
            throw new RuntimeException("Expected start(1) to be 0 but was " + start1);
        }
        // group 2 is 'b'
        int start2 = m.start(2);
        if (start2 != 1) {
            throw new RuntimeException("Expected start(2) to be 1 but was " + start2);
        }
    }

    private static void testStartBeforeMatch() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("a");
        try {
            m.start(0);
            throw new RuntimeException("Expected IllegalStateException when no match available");
        } catch (IllegalStateException ise) {
            // expected
        }
    }

    private static void testStartInvalidGroupNegative() {
        Pattern p = Pattern.compile("(a)");
        Matcher m = p.matcher("a");
        m.find();
        try {
            m.start(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative group");
        } catch (IndexOutOfBoundsException ioobe) {
            // expected
        }
    }

    private static void testStartInvalidGroupTooLarge() {
        Pattern p = Pattern.compile("(a)");
        Matcher m = p.matcher("a");
        m.find();
        int groupCount = m.groupCount();
        try {
            m.start(groupCount + 1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for group > groupCount");
        } catch (IndexOutOfBoundsException ioobe) {
            // expected
        }
    }
}
