/*
 * @test
 * @summary Verifies Matcher.appendTail appends the input after the last append position and returns the same StringBuffer
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testTailAfterReplacement();
        testTailWithoutAnyMatch();
        testTailOnEmptyInput();
        testReturnsSameBuffer();
        testMatchAtEndLeavesEmptyTail();
        testNullBufferThrowsNpe();
    }

    private static void testTailAfterReplacement() {
        Matcher m = Pattern.compile("a").matcher("xaybz");
        StringBuffer sb = new StringBuffer();
        check(m.find(), "find should succeed");
        m.appendReplacement(sb, "-");
        m.appendTail(sb);
        check("x-ybz".equals(sb.toString()),
              "expected x-ybz but was " + sb);
    }

    private static void testTailWithoutAnyMatch() {
        Matcher m = Pattern.compile("q").matcher("hello");
        StringBuffer sb = new StringBuffer("pre:");
        m.appendTail(sb);
        check("pre:hello".equals(sb.toString()),
              "expected pre:hello but was " + sb);
    }

    private static void testTailOnEmptyInput() {
        Matcher m = Pattern.compile("a").matcher("");
        StringBuffer sb = new StringBuffer();
        m.appendTail(sb);
        check(sb.length() == 0, "expected empty result but was " + sb);
    }

    private static void testReturnsSameBuffer() {
        Matcher m = Pattern.compile("a").matcher("abc");
        StringBuffer sb = new StringBuffer();
        StringBuffer result = m.appendTail(sb);
        check(result == sb, "appendTail should return the same StringBuffer");
        check("abc".equals(result.toString()), "expected abc but was " + result);
    }

    private static void testMatchAtEndLeavesEmptyTail() {
        Matcher m = Pattern.compile("b").matcher("ab");
        StringBuffer sb = new StringBuffer();
        check(m.find(), "find should succeed");
        m.appendReplacement(sb, "X");
        m.appendTail(sb);
        check("aX".equals(sb.toString()),
              "expected aX but was " + sb);
    }

    private static void testNullBufferThrowsNpe() {
        Matcher m = Pattern.compile("a").matcher("abc");
        boolean thrown = false;
        try {
            m.appendTail(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null buffer");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
