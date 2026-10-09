/* @test    @summary Test Signature.update(ByteBuffer) method with valid ByteBuffer input    @run main SignatureWitupTest_P308218ed1dd7 */

import java.security.Signature;
import java.security.SignatureException;
import java.nio.ByteBuffer;

public class SignatureWitupTest_P308218ed1dd7 {

    public static void main(String[] args) {
        testUpdateWithValidByteBuffer();
        testUpdateWithNullByteBuffer();
    }

    private static void testUpdateWithValidByteBuffer() {
        try {
            Signature sig = Signature.getInstance("SHA256withRSA");
            // Initialize for signing with a dummy private key (not used in this test)
            // For testing purposes, we can skip actual init, focusing on method invocation
            // and exception handling
            ByteBuffer buffer = ByteBuffer.wrap(new byte[] {1, 2, 3, 4});
            sig.update(buffer);
            System.out.println("testUpdateWithValidByteBuffer passed.");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in valid ByteBuffer test", e);
        }
    }

    private static void testUpdateWithNullByteBuffer() {
        try {
            Signature sig = Signature.getInstance("SHA256withRSA");
            // Initialize for signing
            // For test, no actual init needed to invoke update
            sig.update((ByteBuffer) null);
            throw new RuntimeException("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            System.out.println("testUpdateWithNullByteBuffer passed.");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in null ByteBuffer test", e);
        }
    }
}
