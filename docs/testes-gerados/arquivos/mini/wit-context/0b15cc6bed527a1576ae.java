/*
 * @test
 * @summary Test java.util.Base64.encode(byte[], byte[]) for correct output size and exception on small dst
 * @run main Base64WitupTest
 */

import java.util.Base64;

public class Base64WitupTest {
    public static void main(String[] args) {
        testEncodeWithSufficientDst();
        testEncodeWithInsufficientDst();
    }

    private static void testEncodeWithSufficientDst() {
        byte[] src = {0, 1, 2, 3, 4, 5};
        int expectedLen = Base64.getEncoder().encode(src).length;
        byte[] dst = new byte[expectedLen];
        int ret = Base64.encode(src, dst);
        if (ret != expectedLen) {
            throw new RuntimeException("Returned length " + ret + " does not match expected " + expectedLen);
        }
        // Verify that dst contains valid Base64 encoding of src
        byte[] encoded = Base64.getEncoder().encode(src);
        for (int i = 0; i < ret; i++) {
            if (dst[i] != encoded[i]) {
                throw new RuntimeException("Encoded output mismatch at index " + i);
            }
        }
    }

    private static void testEncodeWithInsufficientDst() {
        byte[] src = {0, 1, 2, 3, 4, 5};
        int expectedLen = Base64.getEncoder().encode(src).length;
        byte[] dst = new byte[expectedLen - 1]; // deliberately too small
        try {
            Base64.encode(src, dst);
            throw new RuntimeException("Expected IllegalArgumentException for too small dst array");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
