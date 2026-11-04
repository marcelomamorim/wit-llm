/*
 * @test
 * @summary Test StringUTF16.compress method behavior
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testCompressAllCharsBelow0xFF();
        testCompressCharAbove0xFFStopsCompression();
        testCompressEmptyLength();
        testCompressZeroLengthArray();
        testCompressThrowsOnInvalidBounds();
    }

    private static void testCompressAllCharsBelow0xFF() {
        byte[] src = new byte[] {0, 1, 0, 2, 0, 3}; // chars: 1, 2, 3
        byte[] dst = new byte[3];
        int result = java.lang.StringUTF16.compress(src, 0, dst, 0, 3);
        if (result != 3) {
            throw new Error("Expected length 3 but got " + result);
        }
        if (dst[0] != 1 || dst[1] != 2 || dst[2] != 3) {
            throw new Error("Destination array content mismatch");
        }
    }

    private static void testCompressCharAbove0xFFStopsCompression() {
        // char with value > 0xFF: 0x0100 (256)
        byte[] src = new byte[] {1, 0, 0, 0}; // chars: 256, 0
        byte[] dst = new byte[2];
        int result = java.lang.StringUTF16.compress(src, 0, dst, 0, 2);
        if (result != 0) {
            throw new Error("Expected length 0 due to char > 0xFF but got " + result);
        }
    }

    private static void testCompressEmptyLength() {
        byte[] src = new byte[] {0, 1};
        byte[] dst = new byte[1];
        int result = java.lang.StringUTF16.compress(src, 0, dst, 0, 0);
        if (result != 0) {
            throw new Error("Expected length 0 for zero length input but got " + result);
        }
    }

    private static void testCompressZeroLengthArray() {
        byte[] src = new byte[0];
        byte[] dst = new byte[0];
        try {
            java.lang.StringUTF16.compress(src, 0, dst, 0, 0);
        } catch (Exception e) {
            throw new Error("compress should not throw for zero length arrays", e);
        }
    }

    private static void testCompressThrowsOnInvalidBounds() {
        byte[] src = new byte[] {0, 1, 0, 2};
        byte[] dst = new byte[2];
        boolean thrown = false;
        try {
            // srcOff + len > src.length should throw
            java.lang.StringUTF16.compress(src, 2, dst, 0, 3);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new Error("Expected IndexOutOfBoundsException for invalid srcOff+len");
        }
    }
}
