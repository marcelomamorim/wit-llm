/*
 * @test
 * @summary Witup-derived tests for Matcher.appendReplacement
 * @run main MatcherWitupTest
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testThrowsBeforeFind();
        testThrowsAfterFailedFind();
        testBasicReplacementAndTail();
        testReturnsSameMatcher();
        testMultipleMatches();
        testGroupReferenceAndEscape();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testThrowsBeforeFind() {
        Matcher m = Pattern.compile("a").matcher("a");
        StringBuilder sb = new StringBuilder();
        try {
            m.appendReplacement(sb, "b");
            throw new RuntimeException("expected IllegalStateException");
        } catch (IllegalStateException expected) {
        }
        check(sb.length() == 0, "sb modified on failure");
    }

    static void testThrowsAfterFailedFind() {
        Matcher m = Pattern.compile("z").matcher("abc");
        check(!m.find(), "find should fail");
        StringBuilder sb = new StringBuilder();
        try {
            m.appendReplacement(sb, "b");
            throw new RuntimeException("expected IllegalStateException");
        } catch (IllegalStateException expected) {
        }
    }

    static void testBasicReplacementAndTail() {
        Matcher m = Pattern.compile("a(b)").matcher("xaby");
        check(m.find(), "find should succeed");
        StringBuilder sb = new StringBuilder();
        m.appendReplacement(sb, "[$1]");
        check(sb.toString().equals("x[b]"), "unexpected after replacement: " + sb);
        m.appendTail(sb);
        check(sb.toString().equals("x[b]y"), "unexpected after tail: " + sb);
    }

    static void testReturnsSameMatcher() {
        Matcher m = Pattern.compile("b").matcher("abc");
        check(m.find(), "find should succeed");
        StringBuilder sb = new StringBuilder();
        check(m.appendReplacement(sb, "Z") == m, "should return this");
    }

    static void testMultipleMatches() {
        Matcher m = Pattern.compile("\\d+").matcher("a1b22c");
        StringBuilder sb = new StringBuilder();
        while (m.find()) {
            m.appendReplacement(sb, "#");
        }
        m.appendTail(sb);
        check(sb.toString().equals("a#b#c"), "unexpected result: " + sb);
    }

    static void testGroupReferenceAndEscape() {
        Matcher m = Pattern.compile("(\\w)=").matcher("k=v");
        check(m.find(), "find should succeed");
        StringBuilder sb = new StringBuilder();
        m.appendReplacement(sb, "\\$$1");
        m.appendTail(sb);
        check(sb.toString().equals("$kv"), "unexpected result: " + sb);
    }
}
