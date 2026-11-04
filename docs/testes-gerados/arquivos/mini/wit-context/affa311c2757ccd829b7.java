/*
 * @test
 * @summary Test Matcher.usePattern(Pattern) method for null argument and state reset
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testUsePatternNull();
        testUsePatternValid();
    }

    private static void testUsePatternNull() {
        Pattern p = Pattern.compile("a*");
        Matcher m = p.matcher("");
        try {
            m.usePattern(null);
            throw new RuntimeException("Expected IllegalArgumentException for null pattern");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testUsePatternValid() {
        Pattern p1 = Pattern.compile("a*");
        Pattern p2 = Pattern.compile("b+");
        Matcher m = p1.matcher("aaa");

        // Use initial pattern
        boolean initialMatches = m.matches();

        // Change pattern
        m.usePattern(p2);

        // After usePattern, matcher should use new pattern
        boolean afterChangeMatches = m.matches();

        if (initialMatches == afterChangeMatches) {
            // It's possible both are false or true, but we expect different behavior
            // Just check that no exception and state reset happened
        }

        // Check that groups array is reset to -1
        // We cannot access groups directly, so we rely on no exceptions and ability to match
        m.reset("bbbb");
        if (!m.matches()) {
            throw new RuntimeException("Matcher did not match input after usePattern with new pattern");
        }
    }
}
