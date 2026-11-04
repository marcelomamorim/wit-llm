/*
 * @test
 * @summary Test java.security.MessageDigest update(byte[], int, int) method
 * @run main MessageDigestWitupTest
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestWitupTest {
    public static void main(String[] args) throws Exception {
        testUpdateWithNullInput();
        testUpdateWithShortInput();
        testUpdateWithValidInput();
    }

    private static void testUpdateWithNullInput() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(null, 0, 1);
            throw new RuntimeException("Expected IllegalArgumentException for null input");
        } catch (IllegalArgumentException iae) {
            if (!"No input buffer given".equals(iae.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + iae.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    private static void testUpdateWithShortInput() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] input = new byte[5];
            md.update(input, 2, 4); // 5 - 2 = 3 < 4, should throw
            throw new RuntimeException("Expected IllegalArgumentException for short input buffer");
        } catch (IllegalArgumentException iae) {
            if (!"Input buffer too short".equals(iae.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + iae.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    private static void testUpdateWithValidInput() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] input = new byte[] {1, 2, 3, 4, 5};
        md.update(input, 1, 3); // should process bytes 2,3,4
        byte[] digest = md.digest();
        if (digest == null || digest.length == 0) {
            throw new RuntimeException("Digest result is null or empty");
        }
    }
}
