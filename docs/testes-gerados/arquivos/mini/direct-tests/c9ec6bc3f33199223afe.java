/*
 * @test
 * @summary Test String constructor String(byte[], int, int, int) for various inputs
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testEmptyCount();
        testCompactStringsLatin1();
        testNonCompactStringsUTF16();
        testOffsetCountBounds();
    }

    private static void testEmptyCount() {
        byte[] ascii = {1, 2, 3};
        String s = new String(ascii, 0, 0, 0);
        if (!s.equals("")) {
            throw new AssertionError("Expected empty string for count=0");
        }
    }

    private static void testCompactStringsLatin1() {
        // hibyte == 0, COMPACT_STRINGS true => LATIN1
        byte[] ascii = {65, 66, 67, 68}; // 'A','B','C','D'
        String s = new String(ascii, 0, 0, ascii.length);
        if (!s.equals("ABCD")) {
            throw new AssertionError("Expected 'ABCD' string");
        }
    }

    private static void testNonCompactStringsUTF16() {
        // hibyte != 0, COMPACT_STRINGS true => UTF16
        byte[] ascii = {1, 2, 3, 4};
        int hibyte = 1; // non-zero hibyte
        String s = new String(ascii, hibyte, 0, ascii.length);
        // The string will contain chars with code units hibyte<<8 | ascii[i]
        // We check length and char values
        if (s.length() != ascii.length) {
            throw new AssertionError("Expected length " + ascii.length + ", got " + s.length());
        }
        for (int i = 0; i < ascii.length; i++) {
            char expected = (char)((hibyte << 8) | (ascii[i] & 0xff));
            if (s.charAt(i) != expected) {
                throw new AssertionError("Char at " + i + " expected: " + (int)expected + " got: " + (int)s.charAt(i));
            }
        }
    }

    private static void testOffsetCountBounds() {
        byte[] ascii = {10, 20, 30, 40, 50};
        // Test offset + count > ascii.length triggers exception
        try {
            new String(ascii, 0, 3, 3); // offset=3, count=3, length=5 -> 3+3=6 > 5
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset+count > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        // Test negative offset
        try {
            new String(ascii, 0, -1, 2);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        // Test negative count
        try {
            new String(ascii, 0, 1, -2);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative count");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
