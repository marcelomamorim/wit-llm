/*
 * @test
 * @summary Test java.lang.StringUTF16.charAt(byte[], int) method
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {
    public static void main(String[] args) {
        testCharAtValidIndex();
        testCharAtInvalidIndex();
    }

    private static void testCharAtValidIndex() {
        // Prepare a byte array representing UTF-16 chars: 'A' (0x0041), 'B' (0x0042)
        byte[] val = new byte[4];
        // 'A' = 0x0041 -> bytes: 0x00, 0x41
        val[0] = 0x00;
        val[1] = 0x41;
        // 'B' = 0x0042 -> bytes: 0x00, 0x42
        val[2] = 0x00;
        val[3] = 0x42;

        char c0 = java.lang.StringUTF16.charAt(val, 0);
        if (c0 != 'A') {
            throw new Error("Expected 'A' at index 0, got: " + c0);
        }

        char c1 = java.lang.StringUTF16.charAt(val, 1);
        if (c1 != 'B') {
            throw new Error("Expected 'B' at index 1, got: " + c1);
        }
    }

    private static void testCharAtInvalidIndex() {
        byte[] val = new byte[2];
        val[0] = 0x00;
        val[1] = 0x41; // 'A'

        boolean threw = false;
        try {
            // index -1 should throw IndexOutOfBoundsException
            java.lang.StringUTF16.charAt(val, -1);
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new Error("Expected IndexOutOfBoundsException for index -1");
        }

        threw = false;
        try {
            // index 2 is out of bounds (length is 1 char)
            java.lang.StringUTF16.charAt(val, 2);
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new Error("Expected IndexOutOfBoundsException for index 2");
        }
    }
}
