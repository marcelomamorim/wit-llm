/*
 * @test
 * @summary Witup-generated checks for Matcher.start(int) exception paths and observable results
 * @run main MatcherWitupTest
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testIllegalStateBeforeMatch();
        testIndexOutOfBounds();
        testValidGroups();
        testStateResetAfterFailedFind();
        testUnmatchedGroupReturnsMinusOne();
    }

    static void testIllegalStateBeforeMatch() {
        Matcher m = Pattern.compile("(a)(b)?").matcher("ab");
        expectIllegalState(m, 0);
        expectIllegalState(m, 1);
    }

    static void testIndexOutOfBounds() {
        Matcher m = Pattern.compile("(a)(b)?").matcher("ab");
        check(m.find(), "find should succeed");
        expectIndexOutOfBounds(m, -1);
        expectIndexOutOfBounds(m, 3);
        expectIndexOutOfBounds(m, Integer.MAX_VALUE);
    }

    static void testValidGroups() {
        Matcher m = Pattern.compile("(a)(b)?").matcher("ab");
        check(m.find(), "find should succeed");
        check(m.start(0) == 0, "group 0 start");
        check(m.start(1) == 0, "group 1 start");
        check(m.start(2) == 1, "group 2 start");
    }

    static void testStateResetAfterFailedFind() {
        Matcher m = Pattern.compile("a").matcher("a");
        check(m.find(), "first find should succeed");
        check(m.start() == 0, "start after match");
        check(!m.find(), "second find should fail");
        expectIllegalState(m, 0);
    }

    static void testUnmatchedGroupReturnsMinusOne() {
        Matcher m = Pattern.compile("(a)|(b)").matcher("a");
        check(m.find(), "find should succeed");
        check(m.start(1) == 0, "group 1 start");
        check(m.start(2) == -1, "unmatched group should return -1");
    }

    static void expectIllegalState(Matcher m, int group) {
        try {
            m.start(group);
        } catch (IllegalStateException e) {
            return;
        }
        throw new RuntimeException("expected IllegalStateException for group " + group);
    }

    static void expectIndexOutOfBounds(Matcher m, int group) {
        try {
            m.start(group);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for group " + group);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
