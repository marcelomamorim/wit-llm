/*
 * @test
 * @summary Test java.util.regex.Matcher.find(int) for valid and invalid start indices
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testFindWithValidStart();
        testFindWithInvalidStartNegative();
        testFindWithInvalidStartTooLarge();
    }

    private static void testFindWithValidStart() {
        Pattern p = Pattern.compile("a*b");
        Matcher m = p.matcher("aaaaab");
        // start at 0, should find
        boolean found = m.find(0);
        if (!found) {
            throw new AssertionError("Expected to find match starting at 0");
        }
        // start at 3, should find (match "aab")
        found = m.find(3);
        if (!found) {
            throw new AssertionError("Expected to find match starting at 3");
        }
        // start at length of input, should not find
        found = m.find("aaaaab".length());
        if (found) {
            throw new AssertionError("Expected not to find match starting at input length");
        }
    }

    private static void testFindWithInvalidStartNegative() {
        Pattern p = Pattern.compile("a*b");
        Matcher m = p.matcher("aaaaab");
        try {
            m.find(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative start");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testFindWithInvalidStartTooLarge() {
        Pattern p = Pattern.compile("a*b");
        Matcher m = p.matcher("aaaaab");
        try {
            m.find("aaaaab".length() + 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for start > input length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
