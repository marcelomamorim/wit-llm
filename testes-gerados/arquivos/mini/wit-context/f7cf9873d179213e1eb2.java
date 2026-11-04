/*
 * @test
 * @summary Test java.security.MessageDigest.update(byte[], int, int) for input validation and normal operation
 * @run main MessageDigestWitupTest
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestWitupTest {
    public static void main(String[] args) throws Exception {
        testUpdateNullInput();
        testUpdateInputTooShort();
        testUpdateValidInput();
    }

    private static void testUpdateNullInput() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        try {
            md.update(null, 0, 1);
            throw new RuntimeException("Expected IllegalArgumentException for null input");
        } catch (IllegalArgumentException iae) {
            // Expected exception
        }
    }

    private static void testUpdateInputTooShort() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] input = new byte[5];
        try {
            md.update(input, 3, 3); // 3 + 3 = 6 > input.length (5)
            throw new RuntimeException("Expected IllegalArgumentException for input buffer too short");
        } catch (IllegalArgumentException iae) {
            // Expected exception
        }
    }

    private static void testUpdateValidInput() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] input = new byte[10];
        for (int i = 0; i < input.length; i++) {
            input[i] = (byte) i;
        }
        // Should not throw
        md.update(input, 2, 5);
        byte[] digest = md.digest();
        if (digest == null || digest.length == 0) {
            throw new RuntimeException("Digest result is invalid");
        }
    }
}
