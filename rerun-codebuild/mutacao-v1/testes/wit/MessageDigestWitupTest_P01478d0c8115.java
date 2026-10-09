/* @test    @summary Test digest(byte[] buf, int offset, int len) for null buffer and small buffer cases    @run main MessageDigestWitupTest_P01478d0c8115 */

import java.security.*;

public class MessageDigestWitupTest_P01478d0c8115 {

    public static void main(String[] args) {
        testDigestNullBuffer();
        testDigestBufferTooSmall();
    }

    private static void testDigestNullBuffer() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] buffer = null;
            md.digest(buffer, 0, 10);
            throw new AssertionError("Expected IllegalArgumentException for null buffer");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testDigestBufferTooSmall() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[5];
            // buffer length is 5, offset is 0, len is 10, so buffer is too small
            md.digest(buffer, 0, 10);
            throw new AssertionError("Expected IllegalArgumentException for small buffer");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
