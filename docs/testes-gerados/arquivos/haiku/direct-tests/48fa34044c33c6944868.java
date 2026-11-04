/*
 * @test
 * @summary Verifies public behavior of Matcher.start(int) for match, unmatched group, and error cases
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

    static void testNoMatchThrowsIllegalState() {
        Matcher m = Pattern.compile("a").matcher("xyz");
        boolean threw = false;
        try {
            m.start(0);
        } catch (IllegalStateException e) {
            threw = true;
        }
        check(threw, "start(0) before any match must throw IllegalStateException");

        check(!m.find(), "find should fail on 'xyz' with pattern 'a'");
        threw = false;
        try {
            m.start(0);
        } catch (IllegalStateException e) {
            threw = true;
        }
        check(threw, "start(0) after failed find must throw IllegalStateException");
    }

    static void testGroupZeroAndCapturedGroups() {
        Matcher m = Pattern.compile("(a)(b)").matcher("xxab");
        check(m.find(), "find should match 'ab'");
        check(m.start(0) == 2, "start(0) expected 2 but was " + m.start(0));
        check(m.start(1) == 2, "start(1) expected 2 but was " + m.start(1));
        check(m.start(2) == 3, "start(2) expected 3 but was " + m.start(2));
    }

    static void testUnmatchedOptionalGroupReturnsMinusOne() {
        Matcher m = Pattern.compile("(a)(b)?").matcher("a");
        check(m.find(), "find should match 'a'");
        check(m.start(0) == 0, "start(0) expected 0 but was " + m.start(0));
        check(m.start(1) == 0, "start(1) expected 0 but was " + m.start(1));
        check(m.start(2) == -1, "unmatched group start expected -1 but was " + m.start(2));
    }

    static void testOutOfRangeGroupThrows() {
        Matcher m = Pattern.compile("(a)").matcher("a");
        check(m.find(), "find should match 'a'");

        boolean threw = false;
        try {
            m.start(-1);
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }
        check(threw, "start(-1) must throw IndexOutOfBoundsException");

        threw = false;
        try {
            m.start(m.groupCount() + 1);
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }
        check(threw, "start(groupCount()+1) must throw IndexOutOfBoundsException");
    }

    public static void main(String[] args) {
        testNoMatchThrowsIllegalState();
        testGroupZeroAndCapturedGroups();
        testUnmatchedOptionalGroupReturnsMinusOne();
        testOutOfRangeGroupThrows();
    }
}
