/*
 * @test
 * @summary Verifies observable behavior of Matcher.end(int) for matched, unmatched, and invalid groups
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEndOfWholeMatchAndGroups() {
        Matcher m = Pattern.compile("(a)(b)?").matcher("xa");
        check(m.find(), "find should succeed on 'xa'");
        check(m.end() == 2, "end() expected 2 but was " + m.end());
        check(m.end(0) == 2, "end(0) expected 2 but was " + m.end(0));
        check(m.end(1) == 2, "end(1) expected 2 but was " + m.end(1));
        check(m.end(2) == -1, "end(2) for unmatched group expected -1 but was " + m.end(2));
    }

    static void testEndAfterSuccessiveFinds() {
        Matcher m = Pattern.compile("a").matcher("aa");
        check(m.find(), "first find should succeed");
        check(m.end(0) == 1, "first end(0) expected 1 but was " + m.end(0));
        check(m.find(), "second find should succeed");
        check(m.end(0) == 2, "second end(0) expected 2 but was " + m.end(0));
    }

    static void testEndWithoutMatchThrows() {
        Matcher m = Pattern.compile("a").matcher("b");
        try {
            m.end(0);
            throw new RuntimeException("expected IllegalStateException before a match");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void testEndWithNegativeGroupThrows() {
        Matcher m = Pattern.compile("(a)").matcher("a");
        check(m.find(), "find should succeed on 'a'");
        try {
            m.end(-1);
            throw new RuntimeException("expected IndexOutOfBoundsException for group -1");
        } catch (IndexOutOfBoundsException expected) {
            check("No group -1".equals(expected.getMessage()),
                  "unexpected message: " + expected.getMessage());
        }
    }

    static void testEndWithGroupAboveCountThrows() {
        Matcher m = Pattern.compile("(a)").matcher("a");
        check(m.find(), "find should succeed on 'a'");
        int invalid = m.groupCount() + 1;
        try {
            m.end(invalid);
            throw new RuntimeException("expected IndexOutOfBoundsException for group " + invalid);
        } catch (IndexOutOfBoundsException expected) {
            check(expected.getMessage().equals("No group " + invalid),
                  "unexpected message: " + expected.getMessage());
        }
    }

    public static void main(String[] args) {
        testEndOfWholeMatchAndGroups();
        testEndAfterSuccessiveFinds();
        testEndWithoutMatchThrows();
        testEndWithNegativeGroupThrows();
        testEndWithGroupAboveCountThrows();
    }
}
