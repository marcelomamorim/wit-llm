/*
 * @test
 * @summary Test Character.toChars(int, char[], int) for valid BMP, supplementary, and invalid code points
 * @run main CharacterWitupTest_P18eaa3cdf700
 */

public class CharacterWitupTest_P18eaa3cdf700 {
    public static void main(String[] args) {
        testBmpCodePoint();
        testSupplementaryCodePoint();
        testInvalidCodePoint();
    }

    private static void testBmpCodePoint() {
        char[] dst = new char[2];
        int codePoint = 'A'; // BMP code point
        int ret = java.lang.Character.toChars(codePoint, dst, 0);
        if (ret != 1) {
            throw new RuntimeException("Expected length 1 for BMP code point");
        }
        if (dst[0] != (char) codePoint) {
            throw new RuntimeException("Expected dst[0] to be the codePoint char");
        }
    }

    private static void testSupplementaryCodePoint() {
        char[] dst = new char[2];
        int codePoint = 0x1D11E; // MUSICAL SYMBOL G CLEF, supplementary code point
        int ret = java.lang.Character.toChars(codePoint, dst, 0);
        if (ret != 2) {
            throw new RuntimeException("Expected length 2 for supplementary code point");
        }
        // Validate surrogate pair range
        if (!java.lang.Character.isHighSurrogate(dst[0])) {
            throw new RuntimeException("Expected high surrogate at dst[0]");
        }
        if (!java.lang.Character.isLowSurrogate(dst[1])) {
            throw new RuntimeException("Expected low surrogate at dst[1]");
        }
    }

    private static void testInvalidCodePoint() {
        char[] dst = new char[2];
        int invalidCodePoint = 0x110000; // Above Unicode max U+10FFFF
        try {
            java.lang.Character.toChars(invalidCodePoint, dst, 0);
            throw new RuntimeException("Expected IllegalArgumentException for invalid code point");
        } catch (IllegalArgumentException e) {
            // Expected
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Not a valid Unicode code point")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
