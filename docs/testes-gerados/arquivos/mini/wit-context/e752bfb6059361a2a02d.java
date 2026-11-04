/*
 * @test
 * @summary Test StringUTF16.inflate method for correct behavior and bounds checking
 * @run main java.lang.StringUTF16WitupTest
 */

public class StringUTF16WitupTest {
    public static void main(String[] args) {
        testInflateValid();
        testInflateInvalidDstOffset();
        testInflateInvalidLen();
        testInflateDstTooSmall();
    }

    private static void testInflateValid() {
        byte[] src = new byte[] {0x01, 0x02, 0x03};
        byte[] dst = new byte[10];
        int srcOff = 0;
        int dstOff = 0;
        int len = 3;

        // Call inflate with valid parameters
        java.lang.StringUTF16.inflate(src, srcOff, dst, dstOff, len);

        // Check that dst contains expected values (each char is one byte from src zero-extended)
        for (int i = 0; i < len; i++) {
            int highByte = dst[(dstOff + i) << 1] & 0xff;
            int lowByte = dst[((dstOff + i) << 1) + 1] & 0xff;
            if (highByte != 0) {
                throw new RuntimeException("High byte should be zero");
            }
            if (lowByte != (src[srcOff + i] & 0xff)) {
                throw new RuntimeException("Low byte mismatch at index " + i);
            }
        }
    }

    private static void testInflateInvalidDstOffset() {
        byte[] src = new byte[] {0x01};
        byte[] dst = new byte[2];
        int srcOff = 0;
        int dstOff = -1; // invalid
        int len = 1;

        try {
            java.lang.StringUTF16.inflate(src, srcOff, dst, dstOff, len);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for negative dstOff");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInflateInvalidLen() {
        byte[] src = new byte[] {0x01};
        byte[] dst = new byte[2];
        int srcOff = 0;
        int dstOff = 0;
        int len = -1; // invalid

        try {
            java.lang.StringUTF16.inflate(src, srcOff, dst, dstOff, len);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for negative len");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInflateDstTooSmall() {
        byte[] src = new byte[] {0x01, 0x02};
        byte[] dst = new byte[2]; // too small for len=2 chars (needs 4 bytes)
        int srcOff = 0;
        int dstOff = 0;
        int len = 2;

        try {
            java.lang.StringUTF16.inflate(src, srcOff, dst, dstOff, len);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for dst too small");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
