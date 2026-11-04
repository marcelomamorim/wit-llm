/*
 * @test
 * @summary Verifies Matcher.start(int) returns group start indices and rejects invalid group numbers
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testGroupStartIndices();
        testUnmatchedGroupReturnsMinusOne();
        testInvalidGroupIndices();
    }

    static void testGroupStartIndices() {
        Matcher m = Pattern.compile("(a)(b)").matcher("zab");
        if (!m.find()) {
            throw new RuntimeException("find() should succeed on 'zab'");
        }
        check(m.start() == 1, "start() expected 1 but was " + m.start());
        check(m.start(0) == 1, "start(0) expected 1 but was " + m.start(0));
        check(m.start(1) == 1, "start(1) expected 1 but was " + m.start(1));
        check(m.start(2) == 2, "start(2) expected 2 but was " + m.start(2));
    }

    static void testUnmatchedGroupReturnsMinusOne() {
        Matcher m = Pattern.compile("(a)|(b)").matcher("b");
        if (!m.find()) {
            throw new RuntimeException("find() should succeed on 'b'");
        }
        check(m.start(1) == -1, "start(1) expected -1 but was " + m.start(1));
        check(m.start(2) == 0, "start(2) expected 0 but was " + m.start(2));
    }

    static void testInvalidGroupIndices() {
        Matcher m = Pattern.compile("(a)(b)").matcher("ab");
        if (!m.find()) {
            throw new RuntimeException("find() should succeed on 'ab'");
        }
        expectIndexOutOfBounds(m, -1, "No group -1");
        expectIndexOutOfBounds(m, m.groupCount() + 1, "No group " + (m.groupCount() + 1));
    }

    static void expectIndexOutOfBounds(Matcher m, int group, String expectedMessage) {
        try {
            m.start(group);
        } catch (IndexOutOfBoundsException e) {
            check(expectedMessage.equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
            return;
        }
        throw new RuntimeException("start(" + group + ") should throw IndexOutOfBoundsException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
