/* @test    @summary Test Character.toChars method for BMP and supplementary code points    @run main CharacterWitupTest_P2c1a55bddf1e */

import java.lang.Character;

public class CharacterWitupTest_P2c1a55bddf1e {

    public static void main(String[] args) {
        testBmpCodePoint();
        testSupplementaryCodePoint();
        testInvalidCodePoint();
    }

    static void testBmpCodePoint() {
        int codePoint = 0x0041; // 'A'
        char[] dst = new char[2];
        int count = Character.toChars(codePoint, dst, 0);
        assert count == 1 : "Expected count 1 for BMP code point";
        assert dst[0] == 'A' : "Expected character 'A'";
    }

    static void testSupplementaryCodePoint() {
        int codePoint = 0x1F600; // 😀
        char[] dst = new char[2];
        int count = Character.toChars(codePoint, dst, 0);
        assert count == 2 : "Expected count 2 for supplementary code point";
        int highSurrogate = dst[0];
        int lowSurrogate = dst[1];
        assert Character.isHighSurrogate((char) highSurrogate) : "Expected high surrogate";
        assert Character.isLowSurrogate((char) lowSurrogate) : "Expected low surrogate";
    }

    static void testInvalidCodePoint() {
        int invalidCodePoint = 0x110000; // beyond Unicode max
        char[] dst = new char[2];
        try {
            Character.toChars(invalidCodePoint, dst, 0);
            assert false : "Expected IllegalArgumentException for invalid code point";
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            assert msg != null && msg.contains("0x110000") : "Exception message should contain the invalid code point";
        }
    }
}
