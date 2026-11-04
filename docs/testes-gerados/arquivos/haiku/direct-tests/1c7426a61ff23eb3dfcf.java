/*
 * @test
 * @summary Behavioral tests for Matcher.replaceAll(Function<MatchResult,String>)
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testReplaceEachMatch();
        testNoMatchReturnsOriginalText();
        testEmptyPatternMatchesEveryPosition();
        testReplacementStringIsProcessedForEscapes();
        testRepeatedCallsGiveSameResult();
        testNullReplacerThrowsNpe();
    }

    static void testReplaceEachMatch() {
        Matcher m = Pattern.compile("\\d+").matcher("a1b22");
        String r = m.replaceAll(mr -> "[" + mr.group() + "]");
        check(r.equals("a[1]b[22]"), "unexpected result: " + r);
    }

    static void testNoMatchReturnsOriginalText() {
        Matcher m = Pattern.compile("x").matcher("abc");
        String r = m.replaceAll(mr -> "Y");
        check(r.equals("abc"), "unexpected result: " + r);
    }

    static void testEmptyPatternMatchesEveryPosition() {
        Matcher m = Pattern.compile("").matcher("ab");
        String r = m.replaceAll(mr -> "x");
        check(r.equals("xaxbx"), "unexpected result: " + r);
    }

    static void testReplacementStringIsProcessedForEscapes() {
        Matcher m = Pattern.compile("\\d").matcher("a1");
        String r = m.replaceAll(mr -> "\\$");
        check(r.equals("a$"), "unexpected result: " + r);
    }

    static void testRepeatedCallsGiveSameResult() {
        Matcher m = Pattern.compile("\\d+").matcher("x12y345");
        String first = m.replaceAll(mr -> "#");
        String second = m.replaceAll(mr -> "#");
        check(first.equals("x#y#"), "unexpected first result: " + first);
        check(first.equals(second), "results differ: " + first + " vs " + second);
    }

    static void testNullReplacerThrowsNpe() {
        boolean thrown = false;
        try {
            Pattern.compile("a").matcher("a").replaceAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null replacer");
    }

    static void check(boolean ok, String msg) {
        if (!ok) {
            throw new RuntimeException(msg);
        }
    }
}
