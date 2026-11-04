/*
 * @test
 * @summary Tests the static method Pattern.matches for various inputs.
 * @run main PatternWitupTest
 */

import java.util.regex.Pattern;

public class PatternWitupTest {

    public static void main(String[] args) {
        testSimpleMatch();
        testNoMatch();
        testEmptyPattern();
        testEmptyInput();
        testSpecialCharacters();
        testLiteralMatch();
        testComplexPattern();
        testNullInput();
    }

    static void testSimpleMatch() {
        boolean result = Pattern.matches("a*b", "aaab");
        if (!result) {
            throw new AssertionError("Expected true for pattern 'a*b' and input 'aaab'");
        }
    }

    static void testNoMatch() {
        boolean result = Pattern.matches("a*b", "aabx");
        if (result) {
            throw new AssertionError("Expected false for pattern 'a*b' and input 'aabx'");
        }
    }

    static void testEmptyPattern() {
        boolean result = Pattern.matches("", "");
        if (!result) {
            throw new AssertionError("Expected true for empty pattern and empty input");
        }
    }

    static void testEmptyInput() {
        boolean result = Pattern.matches("a*", "");
        if (!result) {
            throw new AssertionError("Expected true for pattern 'a*' and empty input");
        }
    }

    static void testSpecialCharacters() {
        boolean result = Pattern.matches("\\d+", "12345");
        if (!result) {
            throw new AssertionError("Expected true for pattern '\\d+' and input '12345'");
        }
    }

    static void testLiteralMatch() {
        boolean result = Pattern.matches("hello", "hello");
        if (!result) {
            throw new AssertionError("Expected true for pattern 'hello' and input 'hello'");
        }
    }

    static void testComplexPattern() {
        boolean result = Pattern.matches("[a-z]+\d{2}", "abc12");
        if (!result) {
            throw new AssertionError("Expected true for pattern '[a-z]+\\d{2}' and input 'abc12'");
        }
    }

    static void testNullInput() {
        try {
            Pattern.matches("a*b", null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
