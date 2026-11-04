/*
 * @test
 * @summary Test String constructor with int[] codePoints, offset, and count
 * @run main StringWitupTest
 */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testEmptyCodePoints();
        testLatin1Encoding();
        testUTF16Encoding();
        testNullCodePoints();
        testInvalidBounds();
    }

    static void testEmptyCodePoints() {
        int[] codePoints = {};
        String s = new String(codePoints, 0, 0);
        assert s.equals("") : "Empty code points should produce empty string";
    }

    static void testLatin1Encoding() {
        int[] codePoints = {65, 66, 67}; // 'A', 'B', 'C'
        String s = new String(codePoints, 0, 3);
        assert s.equals("ABC") : "Should encode to 'ABC'";
    }

    static void testUTF16Encoding() {
        int[] codePoints = {0x1F600}; // 😀
        String s = new String(codePoints, 0, 1);
        assert s.codePointAt(0) == 0x1F600 : "Should encode to 😀";
    }

    static void testNullCodePoints() {
        try {
            new String((int[]) null, 0, 0);
            assert false : "Null codePoints should throw NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testInvalidBounds() {
        int[] codePoints = {65, 66};
        try {
            new String(codePoints, -1, 1);
            assert false : "Negative offset should throw ArrayIndexOutOfBoundsException";
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
        try {
            new String(codePoints, 0, 3);
            assert false : "Count exceeding array length should throw ArrayIndexOutOfBoundsException";
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
