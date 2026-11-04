/* @test    @summary Test usePattern method of Matcher class    @run main MatcherWitupTest */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testUsePatternWithValidPattern();
        testUsePatternWithNullPattern();
    }

    static void testUsePatternWithValidPattern() {
        Pattern pattern1 = Pattern.compile("abc");
        Pattern pattern2 = Pattern.compile("def");
        Matcher matcher = pattern1.matcher("abcdef");
        matcher.usePattern(pattern2);
        // Verify that the pattern has been changed by matching with the new pattern
        boolean matches = matcher.usePattern(pattern2).matches();
        if (!matches) {
            throw new AssertionError("Expected matcher to match pattern 'def'");
        }
    }

    static void testUsePatternWithNullPattern() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abc");
        try {
            matcher.usePattern(null);
            throw new AssertionError("Expected IllegalArgumentException for null pattern");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
