/*
 * @test
 * @summary Test java.lang.Character.codePointBefore(char[], int, int) boundary and normal cases
 * @run main CharacterWitupTest
 */

public class CharacterWitupTest {
    public static void main(String[] args) {
        testValidCodePointBefore();
        testIndexEqualsStartThrows();
        testStartLessThanZeroThrows();
        testStartGreaterOrEqualLengthThrows();
    }

    private static void testValidCodePointBefore() {
        char[] chars = {'a', '\uD800', '\uDC00', 'b'}; // 'a', surrogate pair for U+10000, 'b'
        // index = 3, start = 1, should return code point before index 3 (which is low surrogate at 2)
        int cp = java.lang.Character.codePointBefore(chars, 3, 1);
        if (cp != 0x10000) {
            throw new Error("Expected code point 0x10000 but got " + Integer.toHexString(cp));
        }
        // index = 1, start = 0, should return code point before index 1 (which is 'a')
        cp = java.lang.Character.codePointBefore(chars, 1, 0);
        if (cp != 'a') {
            throw new Error("Expected code point 'a' but got " + Integer.toHexString(cp));
        }
    }

    private static void testIndexEqualsStartThrows() {
        char[] chars = {'a', 'b', 'c'};
        try {
            java.lang.Character.codePointBefore(chars, 1, 1);
            throw new Error("Expected IndexOutOfBoundsException for index == start");
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    private static void testStartLessThanZeroThrows() {
        char[] chars = {'a', 'b', 'c'};
        try {
            java.lang.Character.codePointBefore(chars, 1, -1);
            throw new Error("Expected IndexOutOfBoundsException for start < 0");
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    private static void testStartGreaterOrEqualLengthThrows() {
        char[] chars = {'a', 'b', 'c'};
        try {
            java.lang.Character.codePointBefore(chars, 1, chars.length);
            throw new Error("Expected IndexOutOfBoundsException for start >= a.length");
        } catch (IndexOutOfBoundsException expected) {
        }
    }
}
