/*
 * @test
 * @summary Test MessageDigest.digest(byte[], int, int) for argument validation and normal operation
 * @run main MessageDigestWitupTest
 */

import java.security.MessageDigest;
import java.security.DigestException;

public class MessageDigestWitupTest {
    public static void main(String[] args) throws Exception {
        testNullBufferThrows();
        testBufferTooSmallThrows();
        testDigestNormalOperation();
    }

    private static void testNullBufferThrows() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.digest(null, 0, 1);
            throw new RuntimeException("Expected IllegalArgumentException for null buffer");
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for null buffer", e);
        }
    }

    private static void testBufferTooSmallThrows() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] buf = new byte[5];
            // offset=3, len=3 -> 3+3=6 > buf.length=5
            md.digest(buf, 3, 3);
            throw new RuntimeException("Expected IllegalArgumentException for buffer too small");
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for buffer too small", e);
        }
    }

    private static void testDigestNormalOperation() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] input = "test input".getBytes("UTF-8");
        md.update(input);
        byte[] output = new byte[32]; // SHA-256 output length
        int len = md.digest(output, 0, output.length);
        if (len != output.length) {
            throw new RuntimeException("Digest returned unexpected length: " + len);
        }
        // Call digest again to verify reset behavior
        md.update(input);
        byte[] output2 = new byte[32];
        int len2 = md.digest(output2, 0, output2.length);
        if (len2 != output2.length) {
            throw new RuntimeException("Digest returned unexpected length on second call: " + len2);
        }
        // Outputs should be equal for same input
        for (int i = 0; i < len; i++) {
            if (output[i] != output2[i]) {
                throw new RuntimeException("Digest outputs differ on repeated digest calls");
            }
        }
    }
}
