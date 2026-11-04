/*
 * @test
 * @summary Test StringUTF16.inflate method for correctness and bounds checking
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testInflateBasic();
        testInflateEmpty();
        testInflateBoundsCheck();
    }

    private static void testInflateBasic() {
        byte[] src = {0x01, 0x7F, (byte)0x80, (byte)0xFF};
        byte[] dst = new byte[8]; // len=4, so dst length must be at least 8
        int srcOff = 0;
        int dstOff = 0;
        int len = 4;

        java.lang.StringUTF16.inflate(src, srcOff, dst, dstOff, len);

        // Each src byte is converted to a char stored in dst as two bytes
        // So dst[0],dst[1] = char for src[0], etc.
        for (int i = 0; i < len; i++) {
            int expectedChar = src[i] & 0xFF;
            int hi = dst[i * 2] & 0xFF;
            int lo = dst[i * 2 + 1] & 0xFF;
            int actualChar = (hi << 8) | lo;
            if (actualChar != expectedChar) {
                throw new AssertionError("inflate failed at index " + i + ": expected " + expectedChar + ", got " + actualChar);
            }
        }
    }

    private static void testInflateEmpty() {
        byte[] src = new byte[0];
        byte[] dst = new byte[0];
        java.lang.StringUTF16.inflate(src, 0, dst, 0, 0);
        // No exception means pass
    }

    private static void testInflateBoundsCheck() {
        byte[] src = {0x01, 0x02};
        byte[] dst = new byte[2];

        // dstOff + len must be <= dst.length, otherwise checkBoundsOffCount throws
        // We test that an invalid call throws an exception

        boolean thrown = false;
        try {
            java.lang.StringUTF16.inflate(src, 0, dst, 1, 2); // dstOff=1 + len=2 = 3 > dst.length=2
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IndexOutOfBoundsException for invalid dst bounds");
        }

        // Also test that srcOff + len exceeding src length causes ArrayIndexOutOfBoundsException
        thrown = false;
        try {
            java.lang.StringUTF16.inflate(src, 1, dst, 0, 2); // srcOff=1 + len=2 = 3 > src.length=2
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for invalid src bounds");
        }
    }
}
