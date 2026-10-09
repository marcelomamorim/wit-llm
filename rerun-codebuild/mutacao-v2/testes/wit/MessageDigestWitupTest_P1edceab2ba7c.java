/*
 * @test
 * @summary Test java.security.MessageDigest.update(ByteBuffer) for null input and normal usage
 * @run main MessageDigestWitupTest_P1edceab2ba7c
 */

import java.security.MessageDigest;
import java.nio.ByteBuffer;

public class MessageDigestWitupTest_P1edceab2ba7c {
    public static void main(String[] args) throws Exception {
        testUpdateNullInput();
        testUpdateWithValidInput();
    }

    private static void testUpdateNullInput() {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        try {
            md.update((ByteBuffer) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testUpdateWithValidInput() {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] data = {1, 2, 3, 4, 5};
        ByteBuffer buffer = ByteBuffer.wrap(data);
        md.update(buffer);
        byte[] digest = md.digest();
        if (digest == null || digest.length == 0) {
            throw new RuntimeException("Digest result is null or empty");
        }
    }
}
