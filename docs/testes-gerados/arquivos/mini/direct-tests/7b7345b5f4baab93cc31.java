/*
 * @test
 * @summary Test java.util.regex.Pattern.compile(String) basic behavior
 * @run main PatternWitupTest
 */

import java.util.regex.Pattern;

public class PatternWitupTest {
    public static void main(String[] args) {
        testCompileSimplePattern();
        testCompileEmptyPattern();
        testCompileSpecialCharsPattern();
    }

    private static void testCompileSimplePattern() {
        Pattern p = Pattern.compile("abc");
        if (p == null) {
            throw new AssertionError("Pattern.compile returned null for simple pattern");
        }
        if (!p.pattern().equals("abc")) {
            throw new AssertionError("Pattern pattern() mismatch: expected 'abc' but got '" + p.pattern() + "'");
        }
    }

    private static void testCompileEmptyPattern() {
        Pattern p = Pattern.compile("");
        if (p == null) {
            throw new AssertionError("Pattern.compile returned null for empty pattern");
        }
        if (!p.pattern().equals("")) {
            throw new AssertionError("Pattern pattern() mismatch: expected empty string but got '" + p.pattern() + "'");
        }
    }

    private static void testCompileSpecialCharsPattern() {
        String regex = "\\d+\\s*";
        Pattern p = Pattern.compile(regex);
        if (p == null) {
            throw new AssertionError("Pattern.compile returned null for special chars pattern");
        }
        if (!p.pattern().equals(regex)) {
            throw new AssertionError("Pattern pattern() mismatch: expected '" + regex + "' but got '" + p.pattern() + "'");
        }
    }
}
