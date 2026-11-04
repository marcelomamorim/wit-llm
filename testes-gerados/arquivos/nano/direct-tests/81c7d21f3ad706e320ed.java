/* @test    @summary Test Character.toChars(int) with BMP code point    @run main CharacterWitupTest */

import java.lang.Character;

public class CharacterWitupTest {

    public static void main(String[] args) {
        testBmpCodePoint();
        testSupplementaryCodePoint();
        testInvalidCodePointNegative();
        testInvalidCodePointTooLarge();
    }

    static void testBmpCodePoint() {
        int codePoint = 0x0041; // 'A'
        char[] result = Character.toChars(codePoint);
        assert result.length == 1 : "BMP code point should return array of length 1";
        assert result[0] == (char) codePoint : "Result char should match input code point";
    }

    static void testSupplementaryCodePoint() {
        int codePoint = 0x1F600; // 😀
        char[] result = Character.toChars(codePoint);
        assert result.length == 2 : "Supplementary code point should return array of length 2";
        int highSurrogate = result[0];
        int lowSurrogate = result[1];
        assert highSurrogate >= 0xD800 && highSurrogate <= 0xDBFF : "High surrogate out of range";
        assert lowSurrogate >= 0xDC00 && lowSurrogate <= 0xDFFF : "Low surrogate out of range";
        int reconstructedCodePoint = Character.toCodePoint(result[0], result[1]);
        assert reconstructedCodePoint == codePoint : "Reconstructed code point should match original";
    }

    static void testInvalidCodePointNegative() {
        int invalidCodePoint = -1;
        try {
            Character.toChars(invalidCodePoint);
            assert false : "Expected IllegalArgumentException for negative code point";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInvalidCodePointTooLarge() {
        int invalidCodePoint = 0x110000; // one more than max valid
        try {
            Character.toChars(invalidCodePoint);
            assert false : "Expected IllegalArgumentException for code point too large";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
