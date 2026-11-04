/* @test    @summary Test for Character.toChars(int, char[], int) method handling valid and invalid code points    @run main CharacterWitupTest */

import java.lang.Character;

public class CharacterWitupTest {

    public static void main(String[] args) {
        testValidBmpCodePoint();
        testValidSupplementaryCodePoint();
        testInvalidCodePointNegative();
        testInvalidCodePointTooLarge();
    }

    private static void testValidBmpCodePoint() {
        char[] dst = new char[2];
        int result = Character.toChars(0x0041, dst, 0); // 'A'
        assert result == 1 : "Expected result 1 for BMP code point";
        assert dst[0] == 'A' : "Expected 'A' in dst[0]";
    }

    private static void testValidSupplementaryCodePoint() {
        char[] dst = new char[2];
        int codePoint = 0x1F600; // 😀
        int result = Character.toChars(codePoint, dst, 0);
        assert result == 2 : "Expected result 2 for supplementary code point";
        assert Character.isSurrogatePair(dst[0], dst[1]) : "Dst should contain surrogate pair";
    }

    private static void testInvalidCodePointNegative() {
        char[] dst = new char[2];
        try {
            Character.toChars(-1, dst, 0);
            assert false : "Expected IllegalArgumentException for negative code point";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testInvalidCodePointTooLarge() {
        char[] dst = new char[2];
        try {
            Character.toChars(0x110000, dst, 0); // beyond Unicode range
            assert false : "Expected IllegalArgumentException for code point beyond U+10FFFF";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
