/* @test    @summary Testa o método update(ByteBuffer) de MessageDigest para comportamento observável e exceções.    @run main MessageDigestWitupTest_Pe32c35c8dcc9 */

import java.security.MessageDigest;
import java.nio.ByteBuffer;

public class MessageDigestWitupTest_Pe32c35c8dcc9 {

    public static void main(String[] args) {
        testUpdateWithNullInput();
        testUpdateWithValidInput();
    }

    private static void testUpdateWithNullInput() {
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

    private static void testUpdateWithValidInput() {
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
            throw new AssertionError("Unexpected exception during valid update: " + e);
        }
    }
}
