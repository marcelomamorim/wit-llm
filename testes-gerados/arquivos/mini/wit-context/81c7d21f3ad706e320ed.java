/*
 * @test
 * @summary Test Character.toChars(int) for valid BMP, valid supplementary, and invalid code points
 * @run main CharacterWitupTest
 */

public class CharacterWitupTest {
    public static void main(String[] args) {
        testBmpCodePoint();
        testSupplementaryCodePoint();
        testInvalidCodePoint();
    }

    private static void testBmpCodePoint() {
        // BMP code point: 'A' (U+0041)
        int codePoint = 0x0041;
        char[] chars = java.lang.Character.toChars(codePoint);
        if (chars.length != 1 || chars[0] != 'A') {
            throw new Error("Failed BMP code point test: expected single char 'A'");
        }
    }

    private static void testSupplementaryCodePoint() {
        // Supplementary code point: U+1D11E (Musical Symbol G Clef)
        int codePoint = 0x1D11E;
        char[] chars = java.lang.Character.toChars(codePoint);
        if (chars.length != 2) {
            throw new Error("Failed supplementary code point test: expected surrogate pair length 2");
        }
        // Validate surrogate pair ranges
        char high = chars[0];
        char low = chars[1];
        if (high < 0xD800 || high > 0xDBFF) {
            throw new Error("High surrogate out of range: " + Integer.toHexString(high));
        }
        if (low < 0xDC00 || low > 0xDFFF) {
            throw new Error("Low surrogate out of range: " + Integer.toHexString(low));
        }
    }

    private static void testInvalidCodePoint() {
        // Invalid code point: negative value
        int codePoint = -1;
        try {
            java.lang.Character.toChars(codePoint);
            throw new Error("Expected IllegalArgumentException for negative code point");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Invalid code point: greater than Unicode max (0x110000)
        codePoint = 0x110000;
        try {
            java.lang.Character.toChars(codePoint);
            throw new Error("Expected IllegalArgumentException for code point > 0x10FFFF");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
