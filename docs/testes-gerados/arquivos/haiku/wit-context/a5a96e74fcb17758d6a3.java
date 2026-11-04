/*
 * @test
 * @summary Behavioral tests for Matcher.appendTail(StringBuilder)
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testAppendTailWithoutMatch();
        testAppendTailEmptyInput();
        testAppendTailAfterReplacement();
        testAppendTailReturnsSameBuilder();
        testAppendTailNullBuilder();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAppendTailWithoutMatch() {
        Matcher m = Pattern.compile("a").matcher("xaybz");
        StringBuilder sb = m.appendTail(new StringBuilder("pre:"));
        check("pre:xaybz".equals(sb.toString()),
              "appendTail without prior match: got " + sb);
    }

    static void testAppendTailEmptyInput() {
        Matcher m = Pattern.compile("a").matcher("");
        StringBuilder sb = m.appendTail(new StringBuilder("q"));
        check("q".equals(sb.toString()),
              "appendTail with empty input: got " + sb);
    }

    static void testAppendTailAfterReplacement() {
        Matcher m = Pattern.compile("a").matcher("a-b");
        StringBuilder sb = new StringBuilder();
        check(m.find(), "expected a match for 'a'");
        m.appendReplacement(sb, "A");
        m.appendTail(sb);
        check("A-b".equals(sb.toString()),
              "appendTail after replacement: got " + sb);
    }

    static void testAppendTailReturnsSameBuilder() {
        Matcher m = Pattern.compile("a").matcher("xa");
        StringBuilder sb = new StringBuilder("x");
        StringBuilder returned = m.appendTail(sb);
        check(returned == sb, "appendTail should return the same StringBuilder");
        check("xx".equals(sb.toString()),
              "appendTail content mismatch: got " + sb);
    }

    static void testAppendTailNullBuilder() {
        Matcher m = Pattern.compile("a").matcher("abc");
        try {
            m.appendTail(null);
            throw new RuntimeException("expected NullPointerException for null StringBuilder");
        } catch (NullPointerException expected) {
        }
    }
}
