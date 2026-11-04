/*
 * @test
 * @summary Verifies Character.toChars(int, char[], int) for BMP, supplementary, boundary and invalid code points
 * @run main CharacterWitupTest
 */

public class CharacterWitupTest {

    public static void main(String[] args) {
        testBmp();
        testBmpWithOffset();
        testSupplementary();
        testSupplementaryWithOffset();
        testBoundaries();
        testInvalidCodePoints();
        testBmpArrayTooSmall();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testBmp() {
        char[] dst = new char[1];
        int n = Character.toChars(0x0041, dst, 0);
        check(n == 1, "BMP code point must return 1");
        check(dst[0] == 'A', "BMP code point must be written as its char value");

        n = Character.toChars(0xFFFF, dst, 0);
        check(n == 1, "U+FFFF must return 1");
        check(dst[0] == '\uFFFF', "U+FFFF must be written unchanged");
    }

    static void testBmpWithOffset() {
        char[] dst = new char[3];
        int n = Character.toChars(0x0042, dst, 2);
        check(n == 1, "BMP code point with offset must return 1");
        check(dst[0] == 0 && dst[1] == 0, "Other positions must not be modified");
        check(dst[2] == 'B', "BMP char must be written at dstIndex");
    }

    static void testSupplementary() {
        char[] dst = new char[2];
        int n = Character.toChars(0x1F600, dst, 0);
        check(n == 2, "Supplementary code point must return 2");
        check(dst[0] == '\uD83D', "High surrogate mismatch for U+1F600");
        check(dst[1] == '\uDE00', "Low surrogate mismatch for U+1F600");
    }

    static void testSupplementaryWithOffset() {
        char[] dst = new char[3];
        int n = Character.toChars(0x10000, dst, 1);
        check(n == 2, "Supplementary code point with offset must return 2");
        check(dst[0] == 0, "Position before dstIndex must not be modified");
        check(dst[1] == '\uD800', "High surrogate mismatch for U+10000 at offset");
        check(dst[2] == '\uDC00', "Low surrogate mismatch for U+10000 at offset");
    }

    static void testBoundaries() {
        char[] dst = new char[2];
        int n = Character.toChars(0x10000, dst, 0);
        check(n == 2 && dst[0] == '\uD800' && dst[1] == '\uDC00', "U+10000 encoding mismatch");

        n = Character.toChars(0x10FFFF, dst, 0);
        check(n == 2 && dst[0] == '\uDBFF' && dst[1] == '\uDFFF', "U+10FFFF encoding mismatch");
    }

    static void testInvalidCodePoints() {
        expectIllegalArgument(0x110000, true);
        expectIllegalArgument(-1, false);
    }

    static void expectIllegalArgument(int codePoint, boolean checkMessage) {
        char[] dst = new char[2];
        try {
            Character.toChars(codePoint, dst, 0);
            throw new RuntimeException("Expected IllegalArgumentException for code point " + codePoint);
        } catch (IllegalArgumentException e) {
            if (checkMessage) {
                check("Not a valid Unicode code point: 0x110000".equals(e.getMessage()),
                      "Unexpected message: " + e.getMessage());
            }
        }
    }

    static void testBmpArrayTooSmall() {
        try {
            Character.toChars(0x0041, new char[0], 0);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
