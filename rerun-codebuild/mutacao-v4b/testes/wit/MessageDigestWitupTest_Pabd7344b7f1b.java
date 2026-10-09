/* @test
   @summary Tests the digest(byte[] buf, int offset, int len) method for exception paths.
   @run main MessageDigestWitupTest_Pabd7344b7f1b
 */
import java.security.*;
public class MessageDigestWitupTest_Pabd7344b7f1b {
    public static void main(String[] args) throws Exception {
        testNullBuffer();
        testSmallBuffer();
    }
    private static void testNullBuffer() {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] buffer = null;
        try {
            md.digest(buffer, 0, 10);
            throw new AssertionError("Expected IllegalArgumentException for null buffer");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    private static void testSmallBuffer() {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] buffer = new byte[10];
        int offset = 0;
        int len = 20; // intentionally larger than buffer length
        try {
            md.digest(buffer, offset, len);
            throw new AssertionError("Expected IllegalArgumentException for small buffer");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
