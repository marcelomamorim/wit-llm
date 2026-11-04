/*
 * @test
 * @summary Verifies Matcher.group(int) returns captured text, null for unmatched groups, and throws IndexOutOfBoundsException for invalid group indexes
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testWholeAndCapturedGroups();
        testUnmatchedOptionalGroupReturnsNull();
        testParticipatingOptionalGroupReturnsText();
        testInvalidIndexesThrow();
    }

    static void testWholeAndCapturedGroups() {
        Matcher m = Pattern.compile("(a)(b)?c").matcher("xacx");
        if (!m.find()) {
            throw new RuntimeException("expected a match in xacx");
        }
        check("ac", m.group(0));
        check("a", m.group(1));
    }

    static void testUnmatchedOptionalGroupReturnsNull() {
        Matcher m = Pattern.compile("(a)(b)?c").matcher("xacx");
        if (!m.find()) {
            throw new RuntimeException("expected a match in xacx");
        }
        if (m.group(2) != null) {
            throw new RuntimeException("expected null for unmatched group 2");
        }
    }

    static void testParticipatingOptionalGroupReturnsText() {
        Matcher m = Pattern.compile("(a)(b)?c").matcher("abc");
        if (!m.matches()) {
            throw new RuntimeException("expected full match of abc");
        }
        check("abc", m.group());
        check("a", m.group(1));
        check("b", m.group(2));
    }

    static void testInvalidIndexesThrow() {
        Matcher m = Pattern.compile("(a)(b)?c").matcher("abc");
        if (!m.matches()) {
            throw new RuntimeException("expected full match of abc");
        }
        expectIndexOutOfBounds(m, 3);
        expectIndexOutOfBounds(m, -1);
        expectIndexOutOfBounds(m, 100);
    }

    static void expectIndexOutOfBounds(Matcher m, int group) {
        try {
            m.group(group);
        } catch (IndexOutOfBoundsException e) {
            check("No group " + group, e.getMessage());
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for group " + group);
    }

    static void check(String expected, String actual) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new RuntimeException("expected [" + expected + "] but was [" + actual + "]");
        }
    }
}
