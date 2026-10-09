/* @test
   @summary Tests the update(ByteBuffer) method of MessageDigest.
   @run main MessageDigestWitupTest_P39f223168fbc
 */
import java.security.*;
import java.nio.ByteBuffer;
public class MessageDigestWitupTest_P39f223168fbc {
    public static void main(String[] args) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        ByteBuffer buffer = ByteBuffer.wrap(new byte[] {1, 2, 3, 4});
        md.update(buffer);
        byte[] digest1 = md.digest();
        if (digest1 == null || digest1.length == 0) {
            throw new AssertionError("Digest should not be null or empty after update and digest");
        }
        // Test that calling update with null throws NullPointerException
        try {
            md.update((ByteBuffer) null);
            throw new AssertionError("Expected NullPointerException when input is null");
        } catch (NullPointerException e) {
            // expected
        }
        // Test that engineUpdate is called by invoking update
        ByteBuffer buf2 = ByteBuffer.wrap(new byte[] {5, 6});
        md.update(buf2);
        byte[] digest2 = md.digest();
        if (digest2 == null || digest2.length == 0) {
            throw new AssertionError("Digest should not be null or empty after second update and digest");
        }
    }
}
