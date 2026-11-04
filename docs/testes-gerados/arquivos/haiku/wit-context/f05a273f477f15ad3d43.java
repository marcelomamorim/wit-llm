/*
 * @test
 * @summary Tests observable behavior of Matcher.group(int) for no-match state, invalid group indices and unmatched optional groups
 * @run main MatcherWitupTest
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testGroupBeforeMatchThrowsIllegalState();
        testNegativeGroupThrowsIndexOutOfBounds();
        testGroupAboveCountThrowsIndexOutOfBounds();
        testSuccessfulGroups();
        testUnmatchedOptionalGroupReturnsNull();
    }

    static void testGroupBeforeMatchThrowsIllegalState() {
        Matcher m = Pattern.compile("a").matcher("b");
        expectIllegalState(m, 0);
    }

    static void testNegativeGroupThrowsIndexOutOfBounds() {
        Matcher m = Pattern.compile("(a)").matcher("a");
        if (!m.find()) {
            throw new RuntimeException("expected match");
        }
        expectIndexOutOfBounds(m, -1);
    }

    static void testGroupAboveCountThrowsIndexOutOfBounds() {
        Matcher m = Pattern.compile("(a)").matcher("a");
        if (!m.find()) {
            throw new RuntimeException("expected match");
        }
        if (m.groupCount() != 1) {
            throw new RuntimeException("unexpected groupCount: " + m.groupCount());
        }
        expectIndexOutOfBounds(m, 2);
    }

    static void testSuccessfulGroups() {
        Matcher m = Pattern.compile("(a)").matcher("a");
        if (!m.find()) {
            throw new RuntimeException("expected match");
        }
        check("a", m.group(0));
        check("a", m.group(1));
    }

    static void testUnmatchedOptionalGroupReturnsNull() {
        Matcher m = Pattern.compile("(a)|(b)").matcher("a");
        if (!m.find()) {
            throw new RuntimeException("expected match");
        }
        check("a", m.group(1));
        if (m.group(2) != null) {
            throw new RuntimeException("expected null for unmatched group");
        }
    }

    static void expectIllegalState(Matcher m, int g) {
        try {
            m.group(g);
        } catch (IllegalStateException e) {
            return;
        }
        throw new RuntimeException("expected IllegalStateException");
    }

    static void expectIndexOutOfBounds(Matcher m, int g) {
        try {
            m.group(g);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for group " + g);
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }
}
