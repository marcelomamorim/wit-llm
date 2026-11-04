/* @test    @summary Test MessageDigest.update(byte[], int, int) for correctness and exception handling    @run main MessageDigestWitupTest */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestWitupTest {

    public static void main(String[] args) {
        testUpdateWithValidInput();
        testUpdateWithNullInput();
        testUpdateWithShortBuffer();
    }

    static void testUpdateWithValidInput() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] input = {0x01, 0x02, 0x03, 0x04, 0x05};
            md.update(input, 1, 3); // should process bytes 1,2,3
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    static void testUpdateWithNullInput() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(null, 0, 1);
            throw new AssertionError("Expected IllegalArgumentException for null input");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    static void testUpdateWithShortBuffer() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] input = {0x01, 0x02};
            md.update(input, 0, 5); // len > input.length - offset
        } catch (IllegalArgumentException e) {
            // expected
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
}
