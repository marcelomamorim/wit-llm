/*
 * @test
 * @summary Tests for Matcher.replaceAll(Function<MatchResult, String>) method.
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.MatchResult;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithNullReplacer();
        testReplaceAllWithEmptyPattern();
        testReplaceAllWithMatchingPattern();
        testReplaceAllWithNoMatches();
    }

    static void testReplaceAllWithNullReplacer() {
        Pattern pattern = Pattern.compile("a");
        Matcher matcher = pattern.matcher("abcabc");
        try {
            matcher.replaceAll(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Test passes
        }
    }

    static void testReplaceAllWithEmptyPattern() {
        Pattern pattern = Pattern.compile("");
        Matcher matcher = pattern.matcher("abc");
        String result = matcher.replaceAll(mr -> "X");
        if (!"Xabc".equals(result)) {
            throw new AssertionError("Unexpected result: " + result);
        }
    }

    static void testReplaceAllWithMatchingPattern() {
        Pattern pattern = Pattern.compile("a");
        Matcher matcher = pattern.matcher("abcabc");
        String result = matcher.replaceAll(mr -> "X");
        if (!"XbcXbc".equals(result)) {
            throw new AssertionError("Unexpected result: " + result);
        }
    }

    static void testReplaceAllWithNoMatches() {
        Pattern pattern = Pattern.compile("z");
        Matcher matcher = pattern.matcher("abc");
        String result = matcher.replaceAll(mr -> "X");
        if (!"abc".equals(result)) {
            throw new AssertionError("Unexpected result: " + result);
        }
    }
}
