/*
 * @test
 * @summary Tests for java.util.regex.Matcher.usePattern(Pattern)
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testUsePatternWithNullPattern();
        testUsePatternWithValidPattern();
    }

    private static void testUsePatternWithNullPattern() {
        Matcher matcher = Pattern.compile("abc").matcher("abc");
        try {
            matcher.usePattern(null);
            throw new AssertionError("Expected IllegalArgumentException for null pattern");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testUsePatternWithValidPattern() {
        Pattern pattern1 = Pattern.compile("abc");
        Pattern pattern2 = Pattern.compile("def");
        Matcher matcher = pattern1.matcher("abc");
        Matcher resultMatcher = matcher.usePattern(pattern2);
        if (resultMatcher != matcher) {
            throw new AssertionError("usePattern should return the same Matcher instance");
        }
        // Verify that the pattern has been updated
        if (matcher.pattern() != pattern2) {
            throw new AssertionError("Pattern was not updated correctly");
        }
    }
}
