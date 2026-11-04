/*
 * @test
 * @summary Test java.util.regex.Matcher.end(int) method behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testEndAfterMatch();
        testEndBeforeMatch();
        testEndInvalidGroupNegative();
        testEndInvalidGroupTooLarge();
    }

    private static void testEndAfterMatch() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher("ab");
        if (!m.find()) {
            throw new RuntimeException("Setup failed: expected to find a match");
        }
        // groupCount() == 2, groups: group 0 entire match, group 1 'a', group 2 'b'
        // end(0) should be 2 (end index of entire match "ab")
        int end0 = m.end(0);
        if (end0 != 2) {
            throw new RuntimeException("Expected end(0) to be 2 but was " + end0);
        }
        // end(1) should be 1 (end index of group 1 'a')
        int end1 = m.end(1);
        if (end1 != 1) {
            throw new RuntimeException("Expected end(1) to be 1 but was " + end1);
        }
        // end(2) should be 2 (end index of group 2 'b')
        int end2 = m.end(2);
        if (end2 != 2) {
            throw new RuntimeException("Expected end(2) to be 2 but was " + end2);
        }
    }

    private static void testEndBeforeMatch() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("a");
        try {
            m.end(0);
            throw new RuntimeException("Expected IllegalStateException for end() before match");
        } catch (IllegalStateException ise) {
            // expected
        }
    }

    private static void testEndInvalidGroupNegative() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("a");
        if (!m.find()) {
            throw new RuntimeException("Setup failed: expected to find a match");
        }
        try {
            m.end(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative group");
        } catch (IndexOutOfBoundsException ioobe) {
            // expected
        }
    }

    private static void testEndInvalidGroupTooLarge() {
        Pattern p = Pattern.compile("(a)");
        Matcher m = p.matcher("a");
        if (!m.find()) {
            throw new RuntimeException("Setup failed: expected to find a match");
        }
        int groupCount = m.groupCount();
        try {
            m.end(groupCount + 1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for group > groupCount");
        } catch (IndexOutOfBoundsException ioobe) {
            // expected
        }
    }
}
