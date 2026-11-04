/*
 * @test
 * @summary Test java.util.regex.Matcher.end(int) method behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testEndWithValidGroup();
        testEndWithInvalidGroupNegative();
        testEndWithInvalidGroupTooLarge();
    }

    private static void testEndWithValidGroup() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher("ab");
        if (!m.matches()) {
            throw new RuntimeException("Setup failed: pattern did not match input");
        }
        // groupCount() should be 2
        int groupCount = m.groupCount();
        if (groupCount != 2) {
            throw new RuntimeException("Expected groupCount 2 but got " + groupCount);
        }
        // end(0) returns end of entire match
        int end0 = m.end(0);
        if (end0 != 2) {
            throw new RuntimeException("Expected end(0) == 2 but got " + end0);
        }
        // end(1) returns end of group 1
        int end1 = m.end(1);
        if (end1 != 1) {
            throw new RuntimeException("Expected end(1) == 1 but got " + end1);
        }
        // end(2) returns end of group 2
        int end2 = m.end(2);
        if (end2 != 2) {
            throw new RuntimeException("Expected end(2) == 2 but got " + end2);
        }
    }

    private static void testEndWithInvalidGroupNegative() {
        Pattern p = Pattern.compile("(a)");
        Matcher m = p.matcher("a");
        if (!m.matches()) {
            throw new RuntimeException("Setup failed: pattern did not match input");
        }
        try {
            m.end(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for group -1");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testEndWithInvalidGroupTooLarge() {
        Pattern p = Pattern.compile("(a)");
        Matcher m = p.matcher("a");
        if (!m.matches()) {
            throw new RuntimeException("Setup failed: pattern did not match input");
        }
        int groupCount = m.groupCount();
        try {
            m.end(groupCount + 1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for group " + (groupCount + 1));
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
