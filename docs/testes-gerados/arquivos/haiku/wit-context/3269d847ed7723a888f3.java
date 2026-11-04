/*
 * @test
 * @summary Verifies Matcher.group(String) returns matched text for named groups and throws documented exceptions for no match, unknown names, and non-participating groups
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testNamedGroupReturnsMatchedText();
        testNonParticipatingGroupReturnsNull();
        testUnknownNameAfterMatchThrowsIllegalArgumentException();
        testNoMatchThrowsIllegalStateException();
        testGroupBeforeFindThrowsIllegalStateException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNamedGroupReturnsMatchedText() {
        Matcher m = Pattern.compile("(?<year>[0-9]{4})-(?<month>[0-9]{2})")
                           .matcher("date 2018-09 end");
        check(m.find(), "find should succeed");
        check("2018".equals(m.group("year")), "group(year) should be 2018");
        check("09".equals(m.group("month")), "group(month) should be 09");
        check(m.group("year").equals(m.group(1)), "named group should match indexed group 1");
    }

    static void testNonParticipatingGroupReturnsNull() {
        Matcher m = Pattern.compile("(?<a>x)|(?<b>y)").matcher("x");
        check(m.find(), "find should succeed");
        check("x".equals(m.group("a")), "group(a) should be x");
        check(m.group("b") == null, "non-participating group(b) should be null");
    }

    static void testUnknownNameAfterMatchThrowsIllegalArgumentException() {
        Matcher m = Pattern.compile("(?<a>x)").matcher("x");
        check(m.find(), "find should succeed");
        boolean thrown = false;
        try {
            m.group("zz");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "unknown group name should throw IllegalArgumentException");
    }

    static void testNoMatchThrowsIllegalStateException() {
        Matcher m = Pattern.compile("(?<a>x)").matcher("y");
        check(!m.find(), "find should fail");
        boolean thrown = false;
        try {
            m.group("a");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "group after failed match should throw IllegalStateException");
    }

    static void testGroupBeforeFindThrowsIllegalStateException() {
        Matcher m = Pattern.compile("(?<a>x)").matcher("x");
        boolean thrown = false;
        try {
            m.group("a");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "group before any match should throw IllegalStateException");
    }
}
