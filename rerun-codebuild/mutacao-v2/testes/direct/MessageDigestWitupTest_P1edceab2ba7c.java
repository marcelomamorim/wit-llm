/*
 * @test
 * @summary Test java.security.MessageDigest update(ByteBuffer) method
 * @run main MessageDigestWitupTest_P1edceab2ba7c
 */

import java.security.MessageDigest;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;

public class MessageDigestWitupTest_P1edceab2ba7c {
    public static void main(String[] args) throws Exception {
        testUpdateWithNull();
        testUpdateWithEmptyBuffer();
        testUpdateWithNonEmptyBuffer();
    }

    static void testUpdateWithNull() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((ByteBuffer) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    static void testUpdateWithEmptyBuffer() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        ByteBuffer empty = ByteBuffer.allocate(0);
        md.update(empty);
        byte[] digest = md.digest();
        if (digest == null || digest.length == 0) {
            throw new RuntimeException("Digest should not be null or empty");
        }
    }

    static void testUpdateWithNonEmptyBuffer() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] data = {1, 2, 3, 4, 5};
        ByteBuffer buffer = ByteBuffer.wrap(data);
        md.update(buffer);
        byte[] digest = md.digest();
        if (digest == null || digest.length == 0) {
            throw new RuntimeException("Digest should not be null or empty");
        }
    }
}
