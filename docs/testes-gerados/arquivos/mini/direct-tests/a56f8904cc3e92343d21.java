/*
 * @test
 * @summary Test java.util.Base64 decode(byte[] src, byte[] dst) method
 * @run main Base64WitupTest
 */

import java.util.Base64;

public class Base64WitupTest {
    public static void main(String[] args) throws Exception {
        testDecodeValid();
        testDecodeDstTooSmall();
    }

    private static void testDecodeValid() {
        // Base64 encoding of "Hello" is "SGVsbG8="
        byte[] encoded = "SGVsbG8=".getBytes(java.nio.charset.StandardCharsets.US_ASCII);
        // Allocate dst array with enough size
        byte[] dst = new byte[10];
        Base64.Decoder decoder = Base64.getDecoder();
        int decodedLen = decoder.decode(encoded, dst);
        // The decoded length should be 5 ("Hello")
        if (decodedLen != 5) {
            throw new RuntimeException("Expected decoded length 5 but got " + decodedLen);
        }
        // Check decoded bytes equal to "Hello"
        String decodedString = new String(dst, 0, decodedLen, java.nio.charset.StandardCharsets.US_ASCII);
        if (!"Hello".equals(decodedString)) {
            throw new RuntimeException("Decoded string mismatch: " + decodedString);
        }
    }

    private static void testDecodeDstTooSmall() {
        byte[] encoded = "SGVsbG8=".getBytes(java.nio.charset.StandardCharsets.US_ASCII);
        // Allocate dst array too small
        byte[] dst = new byte[2];
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            decoder.decode(encoded, dst);
            throw new RuntimeException("Expected IllegalArgumentException for too small dst array");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
