/*
 * @test
 * @summary Tests Matcher.usePattern for null rejection, pattern switching and returned instance
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testNullPatternThrows();
        testUsePatternSwitchesPattern();
        testUsePatternReturnsSameMatcher();
    }

    static void testNullPatternThrows() {
        Matcher m = Pattern.compile("a").matcher("abc");
        try {
            m.usePattern(null);
            throw new AssertionError("Expected IllegalArgumentException for null pattern");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testUsePatternSwitchesPattern() {
        Pattern p1 = Pattern.compile("a");
        Pattern p2 = Pattern.compile("b");
        Matcher m = p1.matcher("abc");
        if (!m.find() || !"a".equals(m.group())) {
            throw new AssertionError("Initial pattern should match 'a'");
        }
        m.usePattern(p2);
        m.reset();
        if (!m.find()) {
            throw new AssertionError("New pattern should find 'b'");
        }
        if (!"b".equals(m.group()) || m.start() != 1) {
            throw new AssertionError("Expected group 'b' at index 1, got '"
                    + m.group() + "' at " + m.start());
        }
    }

    static void testUsePatternReturnsSameMatcher() {
        Matcher m = Pattern.compile("a").matcher("abc");
        Matcher returned = m.usePattern(Pattern.compile("c"));
        if (returned != m) {
            throw new AssertionError("usePattern should return this matcher");
        }
    }
}
