/* @test    @summary Test Character.toChars method with valid BMP code point    @run main CharacterWitupTest_P55fa7b35f5ca */

import java.lang.Character;

public class CharacterWitupTest_P55fa7b35f5ca {

    public static void main(String[] args) {
        testBmpCodePoint();
        testSupplementaryCodePoint();
        testInvalidCodePoint();
    }

    static void testBmpCodePoint() {
        int codePoint = 0x0041; // 'A'
        char[] dst = new char[2];
        int length = Character.toChars(codePoint, dst, 0);
        if (length != 1) {
            throw new AssertionError("Expected length 1 for BMP code point, got " + length);
        }
        if (dst[0] != 'A') {
            throw new AssertionError("Expected 'A', got " + dst[0]);
        }
    }

    static void testSupplementaryCodePoint() {
        int codePoint = 0x1F600; // 😀
        char[] dst = new char[2];
        int length = Character.toChars(codePoint, dst, 0);
        if (length != 2) {
            throw new AssertionError("Expected length 2 for supplementary code point, got " + length);
        }
        int reconstructedCodePoint = Character.toCodePoint(dst[0], dst[1]);
        if (reconstructedCodePoint != codePoint) {
            throw new AssertionError("Reconstructed code point " + reconstructedCodePoint + " does not match original " + codePoint);
        }
    }

    static void testInvalidCodePoint() {
        int invalidCodePoint = -1;
        char[] dst = new char[2];
        boolean exceptionThrown = false;
        try {
            Character.toChars(invalidCodePoint, dst, 0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for invalid code point")
        }
    }
}
