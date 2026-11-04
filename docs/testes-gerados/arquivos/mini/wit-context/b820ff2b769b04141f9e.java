/*
 * @test
 * @summary Test StringUTF16.checkOffset(int, byte[]) for boundary and exception cases
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {
    public static void main(String[] args) {
        testValidOffsets();
        testNegativeOffsetThrows();
        testOffsetGreaterThanLengthThrows();
        System.out.println("All tests passed.");
    }

    private static void testValidOffsets() {
        byte[] val = new byte[4]; // length=4 bytes, length(val)=2 chars
        // Valid offsets: 0,1,2
        // 0 <= off <= length(val) == 2
        // So off=0,1,2 are valid
        java.lang.StringUTF16.checkOffset(0, val);
        java.lang.StringUTF16.checkOffset(1, val);
        java.lang.StringUTF16.checkOffset(2, val);
    }

    private static void testNegativeOffsetThrows() {
        byte[] val = new byte[2];
        try {
            java.lang.StringUTF16.checkOffset(-1, val);
            throw new Error("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testOffsetGreaterThanLengthThrows() {
        byte[] val = new byte[2]; // length(val) = 1 char
        int length = java.lang.StringUTF16.length(val); // should be 1
        try {
            java.lang.StringUTF16.checkOffset(length + 1, val); // off > length(val)
            throw new Error("Expected StringIndexOutOfBoundsException for offset > length(val)");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
    }
}
