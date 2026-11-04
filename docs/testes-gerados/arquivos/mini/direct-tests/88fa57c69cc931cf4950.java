/*
 * @test
 * @summary Test java.lang.Character.toChars(int,char[],int) method
 * @run main CharacterWitupTest
 */

public class CharacterWitupTest {
    public static void main(String[] args) {
        testBmpCodePoint();
        testSupplementaryCodePoint();
        testInvalidCodePoint();
    }

    private static void testBmpCodePoint() {
        char[] dst = new char[2];
        int codePoint = 'A'; // BMP code point U+0041
        int ret = java.lang.Character.toChars(codePoint, dst, 0);
        if (ret != 1) {
            throw new RuntimeException("Expected return 1 for BMP code point");
        }
        if (dst[0] != 'A') {
            throw new RuntimeException("Expected dst[0] to be 'A'");
        }
    }

    private static void testSupplementaryCodePoint() {
        char[] dst = new char[2];
        int codePoint = 0x1D11E; // MUSICAL SYMBOL G CLEF, supplementary code point
        int ret = java.lang.Character.toChars(codePoint, dst, 0);
        if (ret != 2) {
            throw new RuntimeException("Expected return 2 for supplementary code point");
        }
        // Check that surrogate pair is valid
        if (!java.lang.Character.isHighSurrogate(dst[0])) {
            throw new RuntimeException("Expected high surrogate at dst[0]");
        }
        if (!java.lang.Character.isLowSurrogate(dst[1])) {
            throw new RuntimeException("Expected low surrogate at dst[1]");
        }
    }

    private static void testInvalidCodePoint() {
        char[] dst = new char[2];
        int invalidCodePoint = 0x110000; // Invalid code point (greater than U+10FFFF)
        try {
            java.lang.Character.toChars(invalidCodePoint, dst, 0);
            throw new RuntimeException("Expected IllegalArgumentException for invalid code point");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Not a valid Unicode code point")) {
                throw new RuntimeException("Exception message unexpected: " + msg);
            }
        }
    }
}
