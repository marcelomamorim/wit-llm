/*
 * @test
 * @summary Test StringUTF16.putCharSB method for correct behavior and bounds checking
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {
    public static void main(String[] args) {
        testPutCharSBValid();
        testPutCharSBIndexNegative();
        testPutCharSBIndexTooLarge();
    }

    private static void testPutCharSBValid() {
        byte[] arr = new byte[4]; // length 4 bytes = 2 chars
        int index = 1;
        int c = 0xABCD;
        StringUTF16.putCharSB(arr, index, c);
        // Validate that the char was stored correctly
        char result = StringUTF16.getChar(arr, index);
        if (result != (char)c) {
            throw new AssertionError("putCharSB failed to store char correctly: expected " +
                Integer.toHexString(c) + ", got " + Integer.toHexString(result));
        }
    }

    private static void testPutCharSBIndexNegative() {
        byte[] arr = new byte[4];
        try {
            StringUTF16.putCharSB(arr, -1, 0x1234);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testPutCharSBIndexTooLarge() {
        byte[] arr = new byte[4];
        try {
            StringUTF16.putCharSB(arr, 2, 0x1234); // index 2 means char at position 2, but length is 2 chars (0 and 1)
            throw new AssertionError("Expected IndexOutOfBoundsException for index too large");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }
}
