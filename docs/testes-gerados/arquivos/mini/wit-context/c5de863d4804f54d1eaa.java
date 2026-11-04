/*
 * @test
 * @summary Test StringUTF16.charAt(byte[], int) for valid and invalid indices
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testCharAtValid();
        testCharAtInvalidNegativeIndex();
        testCharAtInvalidIndexEqualLength();
    }

    private static void testCharAtValid() {
        // Prepare a byte array representing UTF16 chars: two chars 'A' and 'B'
        // 'A' = 0x0041, 'B' = 0x0042
        byte[] val = new byte[4];
        // Manually encode 'A' at index 0
        val[0] = 0x00; // high byte
        val[1] = 0x41; // low byte
        // Manually encode 'B' at index 1
        val[2] = 0x00; // high byte
        val[3] = 0x42; // low byte

        char c0 = java.lang.StringUTF16.charAt(val, 0);
        if (c0 != 'A') {
            throw new Error("Expected 'A' at index 0 but got: " + c0);
        }

        char c1 = java.lang.StringUTF16.charAt(val, 1);
        if (c1 != 'B') {
            throw new Error("Expected 'B' at index 1 but got: " + c1);
        }
    }

    private static void testCharAtInvalidNegativeIndex() {
        byte[] val = new byte[2];
        val[0] = 0x00;
        val[1] = 0x41; // 'A'
        try {
            java.lang.StringUTF16.charAt(val, -1);
            throw new Error("Expected StringIndexOutOfBoundsException for negative index");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCharAtInvalidIndexEqualLength() {
        byte[] val = new byte[2];
        val[0] = 0x00;
        val[1] = 0x41; // 'A'
        int length = java.lang.StringUTF16.length(val); // length should be 1
        try {
            java.lang.StringUTF16.charAt(val, length); // index == length is invalid
            throw new Error("Expected StringIndexOutOfBoundsException for index == length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
