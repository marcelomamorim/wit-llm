/*
 * @test
 * @summary Test StringUTF16.checkBoundsOffCount for bounds checking and exception throwing
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testValidBounds();
        testNegativeOffset();
        testNegativeCount();
        testOffsetCountExceedLength();
    }

    private static void testValidBounds() {
        byte[] val = new byte[10]; // length = 10
        // length(val) = 10 >> 1 = 5
        // offset=0, count=5 is valid
        StringUTF16.checkBoundsOffCount(0, 5, val);
        // offset=2, count=3 is valid
        StringUTF16.checkBoundsOffCount(2, 3, val);
    }

    private static void testNegativeOffset() {
        byte[] val = new byte[10];
        try {
            StringUTF16.checkBoundsOffCount(-1, 1, val);
            throw new Error("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testNegativeCount() {
        byte[] val = new byte[10];
        try {
            StringUTF16.checkBoundsOffCount(0, -1, val);
            throw new Error("Expected StringIndexOutOfBoundsException for negative count");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testOffsetCountExceedLength() {
        byte[] val = new byte[10];
        // length(val) = 5
        try {
            StringUTF16.checkBoundsOffCount(3, 3, val); // 3 + 3 = 6 > 5
            throw new Error("Expected StringIndexOutOfBoundsException for offset + count > length(val)");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
