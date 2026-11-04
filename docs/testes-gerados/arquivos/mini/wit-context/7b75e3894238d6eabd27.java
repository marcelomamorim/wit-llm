/*
 * @test
 * @summary Test StringUTF16.checkIndex(int, byte[]) for bounds checking
 * @run main java.lang.StringUTF16WitupTest
 */

public class StringUTF16WitupTest {
    public static void main(String[] args) {
        testCheckIndexValid();
        testCheckIndexNegative();
        testCheckIndexTooLarge();
    }

    private static void testCheckIndexValid() {
        byte[] val = new byte[4]; // length=4, length(val)=4, so length(val)>>1=2
        // Valid indices: 0 and 1 (since length(val)>>1 == 2)
        // Should not throw
        StringUTF16.checkIndex(0, val);
        StringUTF16.checkIndex(1, val);
    }

    private static void testCheckIndexNegative() {
        byte[] val = new byte[4];
        try {
            StringUTF16.checkIndex(-1, val);
            throw new Error("Expected StringIndexOutOfBoundsException for negative index");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCheckIndexTooLarge() {
        byte[] val = new byte[4];
        int invalidIndex = 2; // length(val)>>1 == 2, so index 2 is out of bounds
        try {
            StringUTF16.checkIndex(invalidIndex, val);
            throw new Error("Expected StringIndexOutOfBoundsException for index >= length(val)>>1");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
