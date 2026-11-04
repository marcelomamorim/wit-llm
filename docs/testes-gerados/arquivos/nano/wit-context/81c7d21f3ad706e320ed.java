/* @test    @summary Test for Character.toChars(int) method to verify behavior with valid and invalid code points    @run main CharacterWitupTest */

import java.lang.Character;

public class CharacterWitupTest {

    public static void main(String[] args) {
        testValidBmpCodePoint();
        testValidSupplementaryCodePoint();
        testInvalidNegativeCodePoint();
        testInvalidTooLargeCodePoint();
    }

    static void testValidBmpCodePoint() {
        int codePoint = 0x0041; // 'A'
        char[] result = Character.toChars(codePoint);
        assert result.length == 1 : "Expected length 1 for BMP code point";
        assert result[0] == (char) codePoint : "Result char does not match input code point";
    }

    static void testValidSupplementaryCodePoint() {
        int codePoint = 0x1F600; // 😀
        char[] result = Character.toChars(codePoint);
        assert result.length == 2 : "Expected length 2 for supplementary code point";
        int reconstructed = Character.toCodePoint(result[0], result[1]);
        assert reconstructed == codePoint : "Reconstructed code point does not match input";
    }

    static void testInvalidNegativeCodePoint() {
        int codePoint = -1;
        try {
            Character.toChars(codePoint);
            assert false : "Expected IllegalArgumentException for negative code point";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInvalidTooLargeCodePoint() {
        int codePoint = 0x110000; // beyond Unicode max
        try {
            Character.toChars(codePoint);
            assert false : "Expected IllegalArgumentException for code point beyond Unicode max";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
