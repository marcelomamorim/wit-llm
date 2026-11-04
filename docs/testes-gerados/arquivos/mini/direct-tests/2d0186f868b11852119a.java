/*
 * @test
 * @summary Test String constructor String(byte[], int) for basic correctness and edge cases
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testBasicAscii();
        testEmptyArray();
        testNegativeHibyte();
        testHibyteBoundary();
    }

    private static void testBasicAscii() {
        byte[] ascii = {65, 66, 67}; // 'A', 'B', 'C'
        int hibyte = 0;
        String s = new String(ascii, hibyte);
        // Expect string "ABC"
        if (!s.equals("ABC")) {
            throw new Error("testBasicAscii failed: expected 'ABC' but got '" + s + "'");
        }
    }

    private static void testEmptyArray() {
        byte[] ascii = new byte[0];
        int hibyte = 0;
        String s = new String(ascii, hibyte);
        // Expect empty string
        if (!s.equals("")) {
            throw new Error("testEmptyArray failed: expected empty string but got '" + s + "'");
        }
    }

    private static void testNegativeHibyte() {
        byte[] ascii = {65, 66}; // 'A', 'B'
        int hibyte = -1; // negative hibyte
        String s = new String(ascii, hibyte);
        // Each char = (hibyte << 8) + (ascii[i] & 0xff)
        // hibyte = -1 = 0xFFFFFFFF, shifted left 8 bits = 0xFFFFFF00
        // char = 0xFF00 + ascii[i]
        // So chars are 0xFF41 and 0xFF42
        if (s.length() != 2) {
            throw new Error("testNegativeHibyte failed: expected length 2 but got " + s.length());
        }
        if (s.charAt(0) != (char)((hibyte << 8) + (ascii[0] & 0xff))) {
            throw new Error("testNegativeHibyte failed: charAt(0) mismatch");
        }
        if (s.charAt(1) != (char)((hibyte << 8) + (ascii[1] & 0xff))) {
            throw new Error("testNegativeHibyte failed: charAt(1) mismatch");
        }
    }

    private static void testHibyteBoundary() {
        byte[] ascii = {0, 127, -128, -1};
        int hibyte = 1; // hibyte = 1
        String s = new String(ascii, hibyte);
        if (s.length() != ascii.length) {
            throw new Error("testHibyteBoundary failed: length mismatch");
        }
        for (int i = 0; i < ascii.length; i++) {
            char expected = (char)((hibyte << 8) + (ascii[i] & 0xff));
            if (s.charAt(i) != expected) {
                throw new Error("testHibyteBoundary failed at index " + i + ": expected " +
                                Integer.toHexString(expected) + " but got " +
                                Integer.toHexString(s.charAt(i)));
            }
        }
    }
}
