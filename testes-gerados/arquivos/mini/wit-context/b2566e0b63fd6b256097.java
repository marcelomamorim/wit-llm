/*
 * @test
 * @summary Test java.util.regex.Matcher.end(int) method for expected exceptions and normal behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testEndNoMatchThrowsIllegalStateException();
        testEndGroupOutOfBoundsThrowsIndexOutOfBoundsException();
        testEndReturnsCorrectGroupEnd();
    }

    private static void testEndNoMatchThrowsIllegalStateException() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher("ab");
        // No match attempted yet, so first < 0 internally
        try {
            m.end(0);
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException ise) {
            // expected
        }
    }

    private static void testEndGroupOutOfBoundsThrowsIndexOutOfBoundsException() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher("ab");
        m.find(); // perform a match so first >= 0
        int invalidGroup1 = -1;
        int invalidGroup2 = m.groupCount() + 1;
        try {
            m.end(invalidGroup1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for group < 0 not thrown");
        } catch (IndexOutOfBoundsException ioobe) {
            // expected
        }
        try {
            m.end(invalidGroup2);
            throw new RuntimeException("Expected IndexOutOfBoundsException for group > groupCount not thrown");
        } catch (IndexOutOfBoundsException ioobe) {
            // expected
        }
    }

    private static void testEndReturnsCorrectGroupEnd() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher("ab");
        boolean found = m.find();
        if (!found) {
            throw new RuntimeException("Expected to find a match but did not");
        }
        // groupCount() == 2
        int end0 = m.end(0); // entire match
        int end1 = m.end(1); // group 1
        int end2 = m.end(2); // group 2
        if (end0 != 2) {
            throw new RuntimeException("Expected end(0) to be 2 but was " + end0);
        }
        if (end1 != 1) {
            throw new RuntimeException("Expected end(1) to be 1 but was " + end1);
        }
        if (end2 != 2) {
            throw new RuntimeException("Expected end(2) to be 2 but was " + end2);
        }
    }
}
