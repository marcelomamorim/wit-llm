/*
 * @test
 * @summary Test java.lang.Character.toChars(int) method for BMP, supplementary and invalid code points
 * @run main CharacterWitupTest
 */

public class CharacterWitupTest {
    public static void main(String[] args) {
        testBmpCodePoint();
        testSupplementaryCodePoint();
        testInvalidCodePoint();
    }

    private static void testBmpCodePoint() {
        int codePoint = 'A'; // BMP code point
        char[] chars = java.lang.Character.toChars(codePoint);
        if (chars.length != 1) {
            throw new RuntimeException("Expected length 1 for BMP code point");
        }
        if (chars[0] != (char) codePoint) {
            throw new RuntimeException("Expected char to be 'A'");
        }
    }

    private static void testSupplementaryCodePoint() {
        int codePoint = 0x1D11E; // MUSICAL SYMBOL G CLEF, supplementary code point
        char[] chars = java.lang.Character.toChars(codePoint);
        if (chars.length != 2) {
            throw new RuntimeException("Expected length 2 for supplementary code point");
        }
        // Check that the chars form a valid surrogate pair
        if (!java.lang.Character.isHighSurrogate(chars[0])) {
            throw new RuntimeException("First char is not a high surrogate");
        }
        if (!java.lang.Character.isLowSurrogate(chars[1])) {
            throw new RuntimeException("Second char is not a low surrogate");
        }
        // Check that converting back to code point yields original
        int cp = java.lang.Character.toCodePoint(chars[0], chars[1]);
        if (cp != codePoint) {
            throw new RuntimeException("Surrogate pair does not convert back to original code point");
        }
    }

    private static void testInvalidCodePoint() {
        int invalidCodePoint = -1; // invalid code point (negative)
        try {
            java.lang.Character.toChars(invalidCodePoint);
            throw new RuntimeException("Expected IllegalArgumentException for negative code point");
        } catch (IllegalArgumentException e) {
            // expected
        }

        invalidCodePoint = 0x110000; // invalid code point (above Unicode max)
        try {
            java.lang.Character.toChars(invalidCodePoint);
            throw new RuntimeException("Expected IllegalArgumentException for code point above Unicode max");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
