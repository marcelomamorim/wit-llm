/*
 * @test
 * @summary Test String constructor String(byte[], int) for basic correctness and bounds
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testBasicConstructor();
        testEmptyArray();
        testNullArray();
        testNegativeHibyte();
    }

    private static void testBasicConstructor() {
        byte[] ascii = {65, 66, 67}; // 'A', 'B', 'C'
        int hibyte = 0;
        String s = new String(ascii, hibyte);
        if (!s.equals("ABC")) {
            throw new Error("Basic constructor failed: expected 'ABC' but got '" + s + "'");
        }
    }

    private static void testEmptyArray() {
        byte[] ascii = new byte[0];
        int hibyte = 0;
        String s = new String(ascii, hibyte);
        if (!s.equals("")) {
            throw new Error("Empty array constructor failed: expected empty string but got '" + s + "'");
        }
    }

    private static void testNullArray() {
        try {
            new String((byte[])null, 0);
            throw new Error("Expected NullPointerException for null byte array");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNegativeHibyte() {
        byte[] ascii = {65, 66};
        int hibyte = -1;
        String s = new String(ascii, hibyte);
        // hibyte is used as high byte for each char, so chars should be (hibyte << 8) + ascii[i]
        // For hibyte = -1 (0xFFFF), chars are 0xFFFF + ascii[i], which is negative char values but valid chars
        // We test that string length matches and chars are as expected
        if (s.length() != ascii.length) {
            throw new Error("Length mismatch for negative hibyte");
        }
        for (int i = 0; i < s.length(); i++) {
            char expected = (char)((hibyte << 8) + (ascii[i] & 0xFF));
            if (s.charAt(i) != expected) {
                throw new Error("Char mismatch at index " + i + ": expected " + (int)expected + " but got " + (int)s.charAt(i));
            }
        }
    }
}
