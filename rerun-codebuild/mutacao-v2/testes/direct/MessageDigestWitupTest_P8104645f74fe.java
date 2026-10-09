/*
 * @test
 * @summary Test java.security.MessageDigest.digest(byte[], int, int) method
 * @run main MessageDigestWitupTest_P8104645f74fe
 */

import java.security.MessageDigest;
import java.security.DigestException;

public class MessageDigestWitupTest_P8104645f74fe {
    public static void main(String[] args) throws Exception {
        testDigestWithNullBuffer();
        testDigestWithInsufficientBufferLength();
        testDigestWithValidBuffer();
    }

    private static void testDigestWithNullBuffer() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.digest(null, 0, 10);
            throw new RuntimeException("Expected IllegalArgumentException for null buffer");
        } catch (IllegalArgumentException iae) {
            if (!"No output buffer given".equals(iae.getMessage())) {
                throw new RuntimeException("Unexpected message for null buffer: " + iae.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for null buffer", e);
        }
    }

    private static void testDigestWithInsufficientBufferLength() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] buf = new byte[10];
        try {
            // offset=5, len=6 -> buf.length - offset = 5 < 6, should throw
            md.digest(buf, 5, 6);
            throw new RuntimeException("Expected IllegalArgumentException for insufficient buffer length");
        } catch (IllegalArgumentException iae) {
            if (!"Output buffer too small for specified offset and length".equals(iae.getMessage())) {
                throw new RuntimeException("Unexpected message for insufficient buffer length: " + iae.getMessage());
            }
        }
    }

    private static void testDigestWithValidBuffer() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] input = "test input".getBytes("UTF-8");
        md.update(input);
        byte[] buf = new byte[32]; // SHA-256 digest length is 32 bytes
        int len = md.digest(buf, 0, buf.length);
        if (len != 32) {
            throw new RuntimeException("Digest length mismatch: expected 32, got " + len);
        }
        // Call digest again to verify state reset (should produce same digest for same input)
        md.update(input);
        byte[] buf2 = new byte[32];
        int len2 = md.digest(buf2, 0, buf2.length);
        if (len2 != 32) {
            throw new RuntimeException("Digest length mismatch on second digest: expected 32, got " + len2);
        }
        for (int i = 0; i < len; i++) {
            if (buf[i] != buf2[i]) {
                throw new RuntimeException("Digest output differs between calls");
            }
        }
    }
}
