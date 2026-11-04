/*
 * @test
 * @summary Witup-style checks for Matcher.appendReplacement(StringBuffer, String)
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

    static void testNoMatchThrowsIllegalState() {
        Matcher m = Pattern.compile("b").matcher("abc");
        StringBuffer sb = new StringBuffer();
        boolean thrown = false;
        try {
            m.appendReplacement(sb, "X");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "appendReplacement without a prior successful find must throw IllegalStateException");
        check(sb.length() == 0, "StringBuffer must not be modified when no match is available");
    }

    static void testSingleReplacementWithTail() {
        Matcher m = Pattern.compile("b").matcher("abc");
        StringBuffer sb = new StringBuffer();
        check(m.find(), "find should locate 'b' in 'abc'");
        Matcher returned = m.appendReplacement(sb, "X");
        check(returned == m, "appendReplacement must return this matcher");
        m.appendTail(sb);
        check(sb.toString().equals("aXc"), "expected aXc but was " + sb);
    }

    static void testMultipleReplacements() {
        Matcher m = Pattern.compile("b").matcher("abcabc");
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "X");
        }
        m.appendTail(sb);
        check(sb.toString().equals("aXcaXc"), "expected aXcaXc but was " + sb);
    }

    static void testGroupZeroReferenceInReplacement() {
        Matcher m = Pattern.compile("b+").matcher("abbc");
        StringBuffer sb = new StringBuffer();
        check(m.find(), "find should locate 'bb' in 'abbc'");
        m.appendReplacement(sb, "<$0>");
        m.appendTail(sb);
        check(sb.toString().equals("a<bb>c"), "expected a<bb>c but was " + sb);
    }

    static void testAppendsToExistingBufferContent() {
        Matcher m = Pattern.compile("b").matcher("abc");
        StringBuffer sb = new StringBuffer("pre-");
        check(m.find(), "find should locate 'b' in 'abc'");
        m.appendReplacement(sb, "X");
        m.appendTail(sb);
        check(sb.toString().equals("pre-aXc"), "expected pre-aXc but was " + sb);
    }

    public static void main(String[] args) {
        testNoMatchThrowsIllegalState();
        testSingleReplacementWithTail();
        testMultipleReplacements();
        testGroupZeroReferenceInReplacement();
        testAppendsToExistingBufferContent();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
