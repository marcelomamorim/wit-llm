/* @test    @summary Test Character.toChars for valid and invalid code points    @run main CharacterWitupTest_P55fa7b35f5ca */

import java.lang.Character;

public class CharacterWitupTest_P55fa7b35f5ca {

    public static void main(String[] args) {
        testValidBmpCodePoint();
        testValidSupplementaryCodePoint();
        testInvalidNegativeCodePoint();
        testInvalidTooLargeCodePoint();
    }

    private static void testValidBmpCodePoint() {
        int codePoint = 0x0041; // 'A'
        char[] dst = new char[2];
        int result = Character.toChars(codePoint, dst, 0);
        if (result != 1) {
            throw new AssertionError("Expected result 1 for BMP code point, got " + result);
        }
        if (dst[0] != (char) codePoint) {
            throw new AssertionError("Expected dst[0] to be 'A', got " + dst[0]);
        }
    }

    private static void testValidSupplementaryCodePoint() {
        int codePoint = 0x1F600; // 😀
        char[] dst = new char[2];
        int result = Character.toChars(codePoint, dst, 0);
        if (result != 2) {
            throw new AssertionError("Expected result 2 for supplementary code point, got " + result);
        }
        if (!Character.isSurrogatePair(dst[0], dst[1])) {
            throw new AssertionError("Expected surrogate pair for code point 0x1F600");
        }
        int reconstructed = Character.toCodePoint(dst[0], dst[1]);
        if (reconstructed != codePoint) {
            throw new AssertionError("Reconstructed code point does not match original. Expected 0x" + Integer.toHexString(codePoint) + ", got 0x" + Integer.toHexString(reconstructed));
        }
    }

    private static void testInvalidNegativeCodePoint() {
        int codePoint = -1;
        char[] dst = new char[2];
        try {
            Character.toChars(codePoint, dst, 0);
            throw new AssertionError("Expected IllegalArgumentException for negative code point");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testInvalidTooLargeCodePoint() {
        int codePoint = 0x110000; // one beyond U+10FFFF
        char[] dst = new char[2];
        try {
            Character.toChars(codePoint, dst, 0);
            throw new AssertionError("Expected IllegalArgumentException for code point > U+10FFFF");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
