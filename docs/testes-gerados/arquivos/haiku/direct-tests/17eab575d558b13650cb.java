/*
 * @test
 * @summary Witup-style checks for Matcher.appendReplacement(StringBuffer, String)
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testNoMatchThrows();
        testBasicReplacementAccumulates();
        testGroupReferences();
        testEscapedDollar();
        testReturnsSameMatcher();
        testAppendsToExistingBuffer();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNoMatchThrows() {
        Matcher m = Pattern.compile("z").matcher("abc");
        StringBuffer sb = new StringBuffer();
        boolean thrown = false;
        try {
            m.appendReplacement(sb, "X");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalStateException when no match is available");
        check(sb.length() == 0, "buffer must not change when no match is available");
    }

    static void testBasicReplacementAccumulates() {
        Matcher m = Pattern.compile("b").matcher("abcabc");
        StringBuffer sb = new StringBuffer();
        check(m.find(), "first find should succeed");
        m.appendReplacement(sb, "X");
        check(sb.toString().equals("aX"), "unexpected after first replacement: " + sb);
        check(m.find(), "second find should succeed");
        m.appendReplacement(sb, "X");
        check(sb.toString().equals("aXcaX"), "unexpected after second replacement: " + sb);
    }

    static void testGroupReferences() {
        Matcher m = Pattern.compile("(\\w)(\\d)").matcher("a1");
        StringBuffer sb = new StringBuffer();
        check(m.find(), "find should succeed");
        m.appendReplacement(sb, "$2$1");
        check(sb.toString().equals("1a"), "group references not expanded: " + sb);
    }

    static void testEscapedDollar() {
        Matcher m = Pattern.compile("a").matcher("a");
        StringBuffer sb = new StringBuffer();
        check(m.find(), "find should succeed");
        m.appendReplacement(sb, "\\$x");
        check(sb.toString().equals("$x"), "escaped dollar not handled: " + sb);
    }

    static void testReturnsSameMatcher() {
        Matcher m = Pattern.compile("a").matcher("a");
        StringBuffer sb = new StringBuffer();
        check(m.find(), "find should succeed");
        Matcher returned = m.appendReplacement(sb, "Q");
        check(returned == m, "appendReplacement must return this matcher");
    }

    static void testAppendsToExistingBuffer() {
        Matcher m = Pattern.compile("y").matcher("xyz");
        StringBuffer sb = new StringBuffer("pre-");
        check(m.find(), "find should succeed");
        m.appendReplacement(sb, "Q");
        check(sb.toString().equals("pre-xQ"), "existing content or intervening text wrong: " + sb);
    }
}
