/*
 * @test
 * @summary Verifies Matcher.end(int) return values and its exceptional behavior for invalid state and group indices
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void checkEquals(int expected, int actual, String message) {
        if (expected != actual) {
            failures++;
            System.err.println("FAILED: " + message + " expected=" + expected + " actual=" + actual);
        }
    }

    public static void main(String[] args) {
        testEndOfWholeMatch();
        testEndOfOptionalUnmatchedGroup();
        testEndBeforeFindThrows();
        testEndWithNegativeGroupThrows();
        testEndWithGroupAboveCountThrows();
        testEndAfterFailedFindThrows();

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    static void testEndOfWholeMatch() {
        Matcher m = Pattern.compile("b+").matcher("abbc");
        check(m.find(), "find should locate 'bb'");
        checkEquals(3, m.end(0), "end(0) of 'b+' in 'abbc'");
        checkEquals(1, m.start(0), "start(0) of 'b+' in 'abbc'");
    }

    static void testEndOfOptionalUnmatchedGroup() {
        Matcher m = Pattern.compile("(a)(b)?").matcher("a");
        check(m.find(), "find should match 'a'");
        checkEquals(1, m.end(0), "end(0) of '(a)(b)?'");
        checkEquals(1, m.end(1), "end(1) of '(a)(b)?'");
        checkEquals(-1, m.end(2), "end(2) of unmatched optional group");
    }

    static void testEndBeforeFindThrows() {
        Matcher m = Pattern.compile("a").matcher("a");
        try {
            m.end(0);
            check(false, "end(0) before find() should throw IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void testEndWithNegativeGroupThrows() {
        Matcher m = Pattern.compile("a").matcher("a");
        check(m.find(), "find should match 'a'");
        try {
            m.end(-1);
            check(false, "end(-1) should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    static void testEndWithGroupAboveCountThrows() {
        Matcher m = Pattern.compile("(a)").matcher("a");
        check(m.find(), "find should match 'a'");
        checkEquals(1, m.groupCount(), "groupCount of '(a)'");
        try {
            m.end(2);
            check(false, "end(2) should throw IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    static void testEndAfterFailedFindThrows() {
        Matcher m = Pattern.compile("z").matcher("abc");
        check(!m.find(), "find should fail for 'z' in 'abc'");
        try {
            m.end(0);
            check(false, "end(0) after failed find should throw IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
