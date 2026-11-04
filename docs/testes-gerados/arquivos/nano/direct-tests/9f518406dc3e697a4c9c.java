/* @test    @summary Test MessageDigest.update(ByteBuffer) with valid and null input    @run main MessageDigestWitupTest */

import java.security.MessageDigest;
import java.nio.ByteBuffer;

public class MessageDigestWitupTest {

    public static void main(String[] args) {
        testUpdateWithValidByteBuffer();
        testUpdateWithNullByteBuffer();
    }

    private static void testUpdateWithValidByteBuffer() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] data = {1, 2, 3, 4, 5};
            ByteBuffer buffer = ByteBuffer.wrap(data);
            md.update(buffer);
            byte[] digest = md.digest();
            if (digest == null || digest.length == 0) {
                throw new AssertionError("Digest should not be null or empty after update.");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid ByteBuffer test: " + e);
        }
    }

    private static void testUpdateWithNullByteBuffer() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            ByteBuffer nullBuffer = null;
            boolean exceptionThrown = false;
            try {
                md.update(nullBuffer);
            } catch (NullPointerException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("NullPointerException was expected when passing null ByteBuffer.");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in null ByteBuffer test: " + e);
        }
    }
}
