/*
 * @test
 * @summary Test java.util.regex.Matcher.start(int) method for expected exceptions and valid returns
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testStartNoMatch();
        testStartGroupOutOfBounds();
        testStartValidGroup();
    }

    private static void testStartNoMatch() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher("ab");
        // No match attempted yet, so first < 0, expect IllegalStateException
        try {
            m.start(0);
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException ise) {
            // expected
        }
    }

    private static void testStartGroupOutOfBounds() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher("ab");
        m.find(); // perform a match so first >= 0
        int invalidGroup1 = -1;
        int invalidGroup2 = m.groupCount() + 1;

        try {
            m.start(invalidGroup1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative group not thrown");
        } catch (IndexOutOfBoundsException iobe) {
            // expected
        }

        try {
            m.start(invalidGroup2);
            throw new RuntimeException("Expected IndexOutOfBoundsException for group > groupCount not thrown");
        } catch (IndexOutOfBoundsException iobe) {
            // expected
        }
    }

    private static void testStartValidGroup() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher("ab");
        boolean found = m.find();
        if (!found) {
            throw new RuntimeException("Setup failed: expected to find a match");
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
}
