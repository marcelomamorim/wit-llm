/*
 * @test
 * @summary Test StringUTF16.compress method for valid and invalid inputs
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {
    public static void main(String[] args) {
        testCompressValid();
        testCompressInvalidNegativeLen();
        testCompressInvalidNegativeSrcOff();
        testCompressInvalidSrcOffLenOutOfBounds();
    }

    private static void testCompressValid() {
        byte[] src = new byte[10 << 1]; // length 20 bytes (10 chars)
        byte[] dst = new byte[10];
        // Fill src with chars <= 0xFF (e.g. 0x00 0x01, 0x00 0x02, ...)
        for (int i = 0; i < 10; i++) {
            src[i << 1] = 0;
            src[(i << 1) + 1] = (byte)(i + 1);
        }
        int result = java.lang.StringUTF16.compress(src, 0, dst, 0, 10);
        if (result != 10) {
            throw new RuntimeException("Expected compress to return 10 but got " + result);
        }
        for (int i = 0; i < 10; i++) {
            int expected = i + 1;
            int actual = dst[i] & 0xFF;
            if (actual != expected) {
                throw new RuntimeException("Mismatch at index " + i + ": expected " + expected + ", got " + actual);
            }
        }
    }

    private static void testCompressInvalidNegativeLen() {
        byte[] src = new byte[4];
        byte[] dst = new byte[4];
        try {
            java.lang.StringUTF16.compress(src, 0, dst, 0, -1);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for negative len");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCompressInvalidNegativeSrcOff() {
        byte[] src = new byte[4];
        byte[] dst = new byte[4];
        try {
            java.lang.StringUTF16.compress(src, -1, dst, 0, 1);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for negative srcOff");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCompressInvalidSrcOffLenOutOfBounds() {
        byte[] src = new byte[4];
        byte[] dst = new byte[4];
        try {
            // srcOff + len > src.length (4)
            java.lang.StringUTF16.compress(src, 3, dst, 0, 2);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for srcOff + len > src.length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
