/*
 * @test
 * @summary Checks public Matcher.start(int) exception and group boundary behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testIllegalStateBeforeFind();
        testValidGroupsAfterFind();
        testUnmatchedGroupReturnsMinusOne();
        testOutOfRangeGroupsThrow();
        testIllegalStateAfterFailedFind();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectThrows(Class<? extends Throwable> type, Runnable action, String message) {
        try {
            action.run();
        } catch (Throwable t) {
            if (type.isInstance(t)) {
                return;
            }
            throw new RuntimeException(message + " (unexpected: " + t + ")", t);
        }
        throw new RuntimeException(message + " (no exception thrown)");
    }

    static void testIllegalStateBeforeFind() {
        final Matcher m = Pattern.compile("a").matcher("a");
        expectThrows(IllegalStateException.class, () -> m.start(0),
                "start(int) before any match must throw IllegalStateException");
    }

    static void testValidGroupsAfterFind() {
        Matcher m = Pattern.compile("(a)(b)").matcher("ab");
        check(m.find(), "find should succeed on \"ab\"");
        check(m.start(0) == 0, "start(0) should be 0");
        check(m.start(1) == 0, "start(1) should be 0");
        check(m.start(2) == 1, "start(2) should be 1");
    }

    static void testUnmatchedGroupReturnsMinusOne() {
        Matcher m = Pattern.compile("(a)|(b)").matcher("b");
        check(m.find(), "find should succeed on \"b\"");
        check(m.start(1) == -1, "unmatched group 1 should report -1");
        check(m.start(2) == 0, "matched group 2 should start at 0");
    }

    static void testOutOfRangeGroupsThrow() {
        final Matcher m = Pattern.compile("(a)(b)").matcher("ab");
        check(m.find(), "find should succeed on \"ab\"");
        expectThrows(IndexOutOfBoundsException.class, () -> m.start(3),
                "start(3) with groupCount 2 must throw IndexOutOfBoundsException");
        expectThrows(IndexOutOfBoundsException.class, () -> m.start(-1),
                "start(-1) must throw IndexOutOfBoundsException");
    }

    static void testIllegalStateAfterFailedFind() {
        final Matcher m = Pattern.compile("z").matcher("a");
        check(!m.find(), "find should fail on \"a\" for pattern z");
        expectThrows(IllegalStateException.class, () -> m.start(0),
                "start(int) after a failed match must throw IllegalStateException");
    }
}
