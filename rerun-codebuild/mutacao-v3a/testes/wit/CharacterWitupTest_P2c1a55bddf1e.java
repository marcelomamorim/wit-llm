/*
 * @test
 * @summary Tests for java.lang.Character.toChars(int, char[], int)
 * @run main CharacterWitupTest_P2c1a55bddf1e
 */

import java.lang.Character;
import java.lang.IllegalArgumentException;

public class CharacterWitupTest_P2c1a55bddf1e {

    public static void main(String[] args) {
        testValidBasicMultilingualPlane();
        testValidSupplementaryCodePoint();
        testInvalidCodePointBelowRange();
        testInvalidCodePointAboveRange();
        testNullDstArray();
        testDstArrayTooSmall();
        System.out.println("All tests passed.");
    }

    static void testValidBasicMultilingualPlane() {
        int codePoint = 0x0041; // 'A'
        char[] dst = new char[2];
        int result = Character.toChars(codePoint, dst, 0);
        assert result == 1 : "Expected result 1 for BMP code point";
        assert dst[0] == (char) codePoint : "Dst[0] should be 'A'";
    }

    static void testValidSupplementaryCodePoint() {
        int codePoint = 0x1F600; // 😀
        char[] dst = new char[2];
        int result = Character.toChars(codePoint, dst, 0);
        assert result == 2 : "Expected result 2 for supplementary code point";
        assert Character.isSurrogate(dst[0]) || Character.isSurrogate(dst[1]) : "Dst should contain surrogate pair";
    }

    static void testInvalidCodePointBelowRange() {
        int codePoint = -1;
        char[] dst = new char[2];
        try {
            Character.toChars(codePoint, dst, 0);
            assert false : "Expected IllegalArgumentException for code point below range";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInvalidCodePointAboveRange() {
        int codePoint = 0x110000; // beyond Unicode range
        char[] dst = new char[2];
        try {
            Character.toChars(codePoint, dst, 0);
            assert false : "Expected IllegalArgumentException for code point above range";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testNullDstArray() {
        int codePoint = 0x0041;
        try {
            Character.toChars(codePoint, null, 0);
            assert false : "Expected NullPointerException for null dst array";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testDstArrayTooSmall() {
        int codePoint = 0x1F600; // supplementary
        char[] dst = new char[1]; // too small
        try {
            Character.toChars(codePoint, dst, 0);
            assert false : "Expected ArrayIndexOutOfBoundsException for small dst array";
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
