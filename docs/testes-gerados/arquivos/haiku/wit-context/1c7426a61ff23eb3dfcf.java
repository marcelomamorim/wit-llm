/*
 * @test
 * @summary Black-box checks for Matcher.replaceAll(Function) null handling and basic replacement behavior
 * @run main MatcherWitupTest
 */

import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullReplacerThrowsNPE() {
        Matcher m = Pattern.compile("\\d").matcher("a1b2");
        Function<MatchResult, String> nullReplacer = null;
        boolean thrown = false;
        try {
            m.replaceAll(nullReplacer);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null replacer");
    }

    static void testReplacementApplied() {
        Matcher m = Pattern.compile("\\d+").matcher("a1b22");
        String result = m.replaceAll(r -> "[" + r.group() + "]");
        check(result.equals("a[1]b[22]"),
              "unexpected result: " + result);
    }

    static void testNoMatchReturnsOriginalText() {
        Matcher m = Pattern.compile("\\d").matcher("abc");
        String result = m.replaceAll(r -> "X");
        check(result.equals("abc"),
              "unexpected result for no match: " + result);
    }

    public static void main(String[] args) {
        testNullReplacerThrowsNPE();
        testReplacementApplied();
        testNoMatchReturnsOriginalText();
    }
}
