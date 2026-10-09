/*
 * @test
 * @summary Test MessageDigest.update(ByteBuffer) for typical and null input.
 * @run main MessageDigestWitupTest_P1cc9892931a9
 */

import java.security.MessageDigest;
import java.nio.ByteBuffer;

public class MessageDigestWitupTest_P1cc9892931a9 {

    public static void main(String[] args) {
        testUpdateWithValidByteBuffer();
        testUpdateWithNullByteBuffer();
    }

    public static void testUpdateWithValidByteBuffer() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] data = {0x01, 0x02, 0x03};
            ByteBuffer buffer = ByteBuffer.wrap(data);
            md.update(buffer);
            byte[] digest1 = md.digest();
            // Re-initialize for a second update
            md = MessageDigest.getInstance("SHA-256");
            ByteBuffer buffer2 = ByteBuffer.wrap(data);
            md.update(buffer2);
            byte[] digest2 = md.digest();
            // Check that digest is not null and has correct length
            assert digest1 != null : "Digest1 is null";
            assert digest2 != null : "Digest2 is null";
            assert digest1.length == 32 : "Digest1 length incorrect";
            assert digest2.length == 32 : "Digest2 length incorrect";
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in valid ByteBuffer test", e);
        }
    }

    public static void testUpdateWithNullByteBuffer() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((ByteBuffer) null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in null ByteBuffer test", e);
        }
    }
}
