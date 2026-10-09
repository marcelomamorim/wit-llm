/* @test    @summary Test MessageDigest.digest(byte[], int, int) for correct behavior and exception handling    @run main MessageDigestWitupTest_P01478d0c8115 */

import java.security.*;

public class MessageDigestWitupTest_P01478d0c8115 {

    public static void main(String[] args) {
        testValidDigest();
        testNullBufferThrows();
        testBufferTooSmallThrows();
    }

    private static void testValidDigest() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] input = {1, 2, 3, 4, 5};
            md.update(input);
            byte[] outputBuffer = new byte[64];
            int resultBytes = md.digest(outputBuffer, 0, 32);
            if (resultBytes <= 0 || resultBytes > outputBuffer.length) {
                throw new AssertionError("Unexpected digest length: " + resultBytes);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testValidDigest: " + e);
        }
    }

    private static void testNullBufferThrows() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.digest(null, 0, 10);
            throw new AssertionError("Expected IllegalArgumentException for null buffer");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testNullBufferThrows: " + e);
        }
    }

    private static void testBufferTooSmallThrows() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[10];
            // offset + len exceeds buffer length
            md.digest(buffer, 8, 5);
            throw new AssertionError("Expected IllegalArgumentException for buffer too small");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testBufferTooSmallThrows: " + e);
        }
    }
}
