/* @test    @summary Test MessageDigest.update(ByteBuffer) method for null and normal input    @run main MessageDigestWitupTest_Pe32c35c8dcc9 */

import java.security.MessageDigest;
import java.nio.ByteBuffer;

public class MessageDigestWitupTest_Pe32c35c8dcc9 {

    public static void main(String[] args) {
        testUpdateWithNull();
        testUpdateWithValidBuffer();
    }

    static void testUpdateWithNull() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((ByteBuffer) null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static void testUpdateWithValidBuffer() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] data = {1, 2, 3, 4, 5};
            ByteBuffer buffer = ByteBuffer.wrap(data);
            md.update(buffer);
            byte[] digest = md.digest();
            if (digest == null || digest.length == 0) {
                throw new AssertionError("Digest should not be null or empty");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
