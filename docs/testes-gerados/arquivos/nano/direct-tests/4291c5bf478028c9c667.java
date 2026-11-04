/* @test    @summary Test MessageDigest.digest(byte[], int, int) for correct behavior and exception handling    @run main MessageDigestWitupTest */

import java.security.*;

public class MessageDigestWitupTest {

    public static void main(String[] args) {
        testDigestWithValidInput();
        testDigestWithNullBuffer();
        testDigestWithSmallBuffer();
    }

    private static void testDigestWithValidInput() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] input = {1, 2, 3, 4, 5};
            md.update(input);
            byte[] outputBuffer = new byte[64];
            int resultBytes = md.digest(outputBuffer, 0, outputBuffer.length);
            assert resultBytes > 0 : "digest should return positive number of bytes";
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testDigestWithValidInput", e);
        }
    }

    private static void testDigestWithNullBuffer() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.digest(null, 0, 10);
            throw new AssertionError("Expected IllegalArgumentException for null buffer");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testDigestWithNullBuffer", e);
        }
    }

    private static void testDigestWithSmallBuffer() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] input = {1, 2, 3, 4, 5};
            md.update(input);
            byte[] smallBuffer = new byte[2];
            md.digest(smallBuffer, 0, smallBuffer.length);
            throw new AssertionError("Expected IllegalArgumentException for small buffer");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testDigestWithSmallBuffer", e);
        }
    }
}
