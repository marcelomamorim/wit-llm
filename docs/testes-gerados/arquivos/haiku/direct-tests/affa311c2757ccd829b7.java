/*
 * @test
 * @summary Verifies Matcher.usePattern rejects null and switches the matcher to the new pattern
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testNullPatternThrows();
        testReturnsSameMatcher();
        testUsesNewPatternForFind();
        testMatchesWithNewPattern();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullPatternThrows() {
        Matcher m = Pattern.compile("a").matcher("a");
        try {
            m.usePattern(null);
            throw new RuntimeException("Expected IllegalArgumentException for null pattern");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testReturnsSameMatcher() {
        Matcher m = Pattern.compile("a").matcher("abc");
        Matcher result = m.usePattern(Pattern.compile("b"));
        check(result == m, "usePattern must return this matcher");
    }

    static void testUsesNewPatternForFind() {
        Matcher m = Pattern.compile("a").matcher("abc");
        m.usePattern(Pattern.compile("b"));
        check(m.find(), "find should match the new pattern");
        check("b".equals(m.group()), "group should be 'b' but was '" + m.group() + "'");
        check(m.start() == 1, "start should be 1 but was " + m.start());
    }

    static void testMatchesWithNewPattern() {
        Matcher m = Pattern.compile("a+").matcher("aaa");
        m.usePattern(Pattern.compile("a{3}"));
        check(m.matches(), "matches should succeed with the new pattern a{3}");
    }
}
