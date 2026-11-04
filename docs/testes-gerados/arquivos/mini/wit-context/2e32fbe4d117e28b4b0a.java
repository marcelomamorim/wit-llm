/*
 * @test
 * @summary Test StringUTF16.putCharSB method for bounds checking and correct char insertion
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {
    public static void main(String[] args) {
        testPutCharSBValidIndex();
        testPutCharSBIndexOutOfBounds();
    }

    private static void testPutCharSBValidIndex() {
        byte[] val = new byte[4]; // length 4 means length(val) = 2 chars
        int index = 1;
        int c = 0xABCD;
        // Should not throw and should put char correctly
        StringUTF16.putCharSB(val, index, c);
        // Validate that the char was put correctly
        char result = StringUTF16.getChar(val, index);
        if (result != (char) c) {
            throw new AssertionError("putCharSB failed to put correct char: expected " +
                                     Integer.toHexString(c) + ", got " + Integer.toHexString(result));
        }
    }

    private static void testPutCharSBIndexOutOfBounds() {
        byte[] val = new byte[4]; // length(val) = 2 chars
        int c = 0x1234;

        // Test index < 0
        try {
            StringUTF16.putCharSB(val, -1, c);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index < 0");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }

        // Test index >= length(val)
        try {
            StringUTF16.putCharSB(val, 2, c); // length(val) = 2, so index 2 is out of bounds
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index >= length(val)");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
    }
}
