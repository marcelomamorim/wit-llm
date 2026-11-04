/*
 * @test
 * @summary Test java.util.regex.Matcher.usePattern(Pattern) method behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testUsePatternWithValidPattern();
        testUsePatternWithNullPattern();
    }

    private static void testUsePatternWithValidPattern() {
        Pattern p1 = Pattern.compile("a*b");
        Matcher m = p1.matcher("aaab");

        Pattern p2 = Pattern.compile("a+");
        Matcher returned = m.usePattern(p2);

        if (returned != m) {
            throw new AssertionError("usePattern should return the same Matcher instance");
        }

        // Test that the new pattern is used
        boolean matches = m.matches();
        if (!matches) {
            throw new AssertionError("Matcher should match input with new pattern");
        }
    }

    private static void testUsePatternWithNullPattern() {
        Pattern p = Pattern.compile("abc");
        Matcher m = p.matcher("abc");

        try {
            m.usePattern(null);
            throw new AssertionError("usePattern(null) should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
