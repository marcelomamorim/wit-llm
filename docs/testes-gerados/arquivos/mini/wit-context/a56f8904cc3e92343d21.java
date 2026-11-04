/*
 * @test
 * @summary Test Base64.decode(byte[] src, byte[] dst) for correct decoding and exception on small dst
 * @run main Base64WitupTest
 */

import java.util.Base64;

public class Base64WitupTest {
    public static void main(String[] args) throws Exception {
        testDecodeValidInput();
        testDecodeThrowsOnSmallDst();
    }

    private static void testDecodeValidInput() {
        // Encoded "Hello" in Base64
        byte[] encoded = "SGVsbG8=".getBytes(java.nio.charset.StandardCharsets.ISO_8859_1);
        // Allocate output buffer with enough size
        byte[] decoded = new byte[5];
        int len = Base64.getDecoder().decode(encoded, decoded);
        if (len != 5) {
            throw new RuntimeException("Decoded length mismatch: expected 5, got " + len);
        }
        String result = new String(decoded, 0, len, java.nio.charset.StandardCharsets.ISO_8859_1);
        if (!"Hello".equals(result)) {
            throw new RuntimeException("Decoded content mismatch: expected 'Hello', got '" + result + "'");
        }
    }

    private static void testDecodeThrowsOnSmallDst() {
        byte[] encoded = "SGVsbG8=".getBytes(java.nio.charset.StandardCharsets.ISO_8859_1);
        // Intentionally too small output buffer
        byte[] smallDst = new byte[2];
        try {
            Base64.getDecoder().decode(encoded, smallDst);
            throw new RuntimeException("Expected IllegalArgumentException due to small dst array, but none thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
