/*
 * @test
 * @summary Behavioral tests for Matcher.appendTail(StringBuffer)
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testNoMatchAppendsWholeInput();
        testEmptyInputNoMatch();
        testAfterSingleReplacement();
        testAfterAllMatches();
        testReturnsSameBuffer();
        testNullBufferThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNoMatchAppendsWholeInput() {
        Matcher m = Pattern.compile("q").matcher("abc");
        StringBuffer sb = new StringBuffer("pre:");
        m.appendTail(sb);
        check("pre:abc".equals(sb.toString()), "expected pre:abc but was " + sb);
    }

    static void testEmptyInputNoMatch() {
        Matcher m = Pattern.compile("x").matcher("");
        StringBuffer sb = new StringBuffer();
        m.appendTail(sb);
        check(sb.length() == 0, "expected empty result but was " + sb);
    }

    static void testAfterSingleReplacement() {
        Matcher m = Pattern.compile("a").matcher("xaybz");
        StringBuffer sb = new StringBuffer();
        check(m.find(), "expected a match");
        m.appendReplacement(sb, "-");
        m.appendTail(sb);
        check("x-ybz".equals(sb.toString()), "expected x-ybz but was " + sb);
    }

    static void testAfterAllMatches() {
        Matcher m = Pattern.compile("[0-9]").matcher("a1b2c");
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "#");
        }
        m.appendTail(sb);
        check("a#b#c".equals(sb.toString()), "expected a#b#c but was " + sb);
    }

    static void testReturnsSameBuffer() {
        Matcher m = Pattern.compile("q").matcher("abc");
        StringBuffer sb = new StringBuffer();
        StringBuffer result = m.appendTail(sb);
        check(result == sb, "appendTail must return the same StringBuffer");
    }

    static void testNullBufferThrowsNPE() {
        Matcher m = Pattern.compile("q").matcher("abc");
        boolean thrown = false;
        try {
            m.appendTail(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null StringBuffer");
    }
}
