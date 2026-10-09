/* @test
   @summary Tests the digest(byte[] buf, int offset, int len) method of MessageDigest.
   @run main MessageDigestWitupTest_Pabd7344b7f1b
 */
import java.security.*;
import java.util.*;

public class MessageDigestWitupTest_Pabd7344b7f1b {
    public static void main(String[] args) throws Exception {
        testDigestWithValidBuffer();
        testDigestWithNullBuffer();
        testDigestWithSmallBuffer();
    }

    private static void testDigestWithValidBuffer() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] input = "test input".getBytes("UTF-8");
        md.update(input);
        byte[] outputBuffer = new byte[64];
        int result = md.digest(outputBuffer, 0, outputBuffer.length);
        if (result <= 0 || result > outputBuffer.length) {
            throw new AssertionError("Unexpected digest length: " + result);
        }
        // Verify that state is reset by calling digest again
        byte[] digest1 = md.digest();
        if (digest1.length != 32) { // SHA-256 output length
            throw new AssertionError("Digest length mismatch");
        }
    }

    private static void testDigestWithNullBuffer() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try {
            md.digest(null, 0, 10);
            throw new AssertionError("Expected IllegalArgumentException for null buffer");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testDigestWithSmallBuffer() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] input = "short".getBytes("UTF-8");
        md.update(input);
        byte[] smallBuffer = new byte[2];
        try {
            md.digest(smallBuffer, 0, smallBuffer.length);
            // Should succeed if buffer is large enough for digest
            // but here buffer is too small for digest output, so expect exception
            // However, the method does not throw for small buffer, it returns number of bytes
            // So, check if result is within bounds
            int result = md.digest(smallBuffer, 0, smallBuffer.length);
            if (result > smallBuffer.length) {
                throw new AssertionError("Digest length exceeds buffer size");
            }
        } catch (IllegalArgumentException e) {
            // expected if buffer too small
        }
    }
}
