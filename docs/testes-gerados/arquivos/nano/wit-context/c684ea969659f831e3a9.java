/*
 * @test
 * @summary Tests for java.util.regex.Pattern.matches method.
 * @run main PatternWitupTest
 */

import java.util.regex.Pattern;

public class PatternWitupTest {

    public static void main(String[] args) {
        testValidRegexAndInput();
        testNullRegex();
        testNullInput();
        testEmptyRegexAndInput();
        testInvalidRegex();
        testComplexRegexAndInput();
    }

    static void testValidRegexAndInput() {
        boolean result = Pattern.matches("a*b", "aaaaab");
        if (!result) {
            throw new AssertionError("Expected true for regex 'a*b' and input 'aaaaab'");
        }
    }

    static void testNullRegex() {
        try {
            Pattern.matches(null, "test");
            throw new AssertionError("Expected NullPointerException for null regex");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testNullInput() {
        boolean result = Pattern.matches("a*b", (CharSequence) null);
        if (result) {
            throw new AssertionError("Expected false for null input");
        }
    }

    static void testEmptyRegexAndInput() {
        boolean result = Pattern.matches("", "");
        if (!result) {
            throw new AssertionError("Expected true for empty regex and empty input");
        }
    }

    static void testInvalidRegex() {
        try {
            Pattern.matches("[", "test");
            throw new AssertionError("Expected PatternSyntaxException for invalid regex");
        } catch (java.util.regex.PatternSyntaxException e) {
            // expected
        }
    }

    static void testComplexRegexAndInput() {
        boolean result = Pattern.matches("\\d+\\.\\d+", "123.456");
        if (!result) {
            throw new AssertionError("Expected true for regex '\\d+\\.\\d+' and input '123.456'");
        }
    }
}
