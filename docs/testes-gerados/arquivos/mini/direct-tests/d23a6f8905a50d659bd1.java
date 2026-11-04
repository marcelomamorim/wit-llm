/*
 * @test
 * @summary Test StringUTF16.checkBoundsOffCount method behavior
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {
    public static void main(String[] args) {
        testValidBounds();
        testInvalidBounds();
    }

    private static void testValidBounds() {
        byte[] val = new byte[10 << 1]; // length = 10 chars
        // offset=0, count=10 is valid
        StringUTF16.checkBoundsOffCount(0, 10, val);
        // offset=5, count=5 is valid
        StringUTF16.checkBoundsOffCount(5, 5, val);
        // offset=10, count=0 is valid
        StringUTF16.checkBoundsOffCount(10, 0, val);
    }

    private static void testInvalidBounds() {
        byte[] val = new byte[10 << 1]; // length = 10 chars
        // offset < 0
        try {
            StringUTF16.checkBoundsOffCount(-1, 5, val);
            throw new Error("Expected IndexOutOfBoundsException for offset < 0");
        } catch (IndexOutOfBoundsException expected) {
        }
        // count < 0
        try {
            StringUTF16.checkBoundsOffCount(0, -1, val);
            throw new Error("Expected IndexOutOfBoundsException for count < 0");
        } catch (IndexOutOfBoundsException expected) {
        }
        // offset + count > length
        try {
            StringUTF16.checkBoundsOffCount(6, 5, val);
            throw new Error("Expected IndexOutOfBoundsException for offset + count > length");
        } catch (IndexOutOfBoundsException expected) {
        }
    }
}
