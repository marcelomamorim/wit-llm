/* @test    @summary Test Character.toChars with BMP code point and supplementary code point    @run main CharacterWitupTest */

import java.lang.Character;

public class CharacterWitupTest {

    public static void main(String[] args) {
        testBmpCodePoint();
        testSupplementaryCodePoint();
        testInvalidCodePoint();
    }

    static void testBmpCodePoint() {
        int codePoint = 0x0041; // 'A'
        char[] dst = new char[2];
        int length = Character.toChars(codePoint, dst, 0);
        assert length == 1 : "Expected length 1 for BMP code point";
        assert dst[0] == 'A' : "Expected 'A' in dst[0]";
    }

    static void testSupplementaryCodePoint() {
        int codePoint = 0x1F600; // 😀
        char[] dst = new char[2];
        int length = Character.toChars(codePoint, dst, 0);
        assert length == 2 : "Expected length 2 for supplementary code point";
        assert Character.isSurrogatePair(dst[0], dst[1]) : "dst should contain a surrogate pair";
        int reconstructed = Character.toCodePoint(dst[0], dst[1]);
        assert reconstructed == codePoint : "Reconstructed code point should match original";
    }

    static void testInvalidCodePoint() {
        int invalidCodePoint = 0x110000; // beyond Unicode range
        char[] dst = new char[2];
        try {
            Character.toChars(invalidCodePoint, dst, 0);
            assert false : "Expected IllegalArgumentException for invalid code point";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
