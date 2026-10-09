/* @test    @summary Test Signature.update(ByteBuffer) method for various ByteBuffer inputs    @run main SignatureWitupTest_P0acb3e3cc728 */

import java.security.Signature;
import java.security.SignatureException;
import java.nio.ByteBuffer;

public class SignatureWitupTest_P0acb3e3cc728 {

    public static void main(String[] args) {
        testUpdateWithNullByteBuffer();
        testUpdateWithEmptyByteBuffer();
        testUpdateWithNonEmptyByteBuffer();
    }

    private static void testUpdateWithNullByteBuffer() {
        Signature sig = null;
        try {
            sig = Signature.getInstance("SHA256withRSA");
        } catch (Exception e) {
            throw new RuntimeException("Failed to get Signature instance", e);
        }
        boolean exceptionThrown = false;
        try {
            sig.update((ByteBuffer) null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        } catch (SignatureException e) {
            throw new RuntimeException("Unexpected SignatureException", e);
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when updating with null ByteBuffer");
        }
    }

    private static void testUpdateWithEmptyByteBuffer() {
        Signature sig = null;
        try {
            sig = Signature.getInstance("SHA256withRSA");
        } catch (Exception e) {
            throw new RuntimeException("Failed to get Signature instance", e);
        }
        ByteBuffer emptyBuffer = ByteBuffer.allocate(0);
        try {
            sig.update(emptyBuffer);
        } catch (SignatureException e) {
            throw new RuntimeException("SignatureException thrown during update with empty ByteBuffer", e);
        }
    }

    private static void testUpdateWithNonEmptyByteBuffer() {
        Signature sig = null;
        try {
            sig = Signature.getInstance("SHA256withRSA");
        } catch (Exception e) {
            throw new RuntimeException("Failed to get Signature instance", e);
        }
        byte[] dataBytes = {1, 2, 3, 4, 5};
        ByteBuffer buffer = ByteBuffer.wrap(dataBytes);
        try {
            sig.update(buffer);
        } catch (SignatureException e) {
            throw new RuntimeException("SignatureException thrown during update with data ByteBuffer", e);
        }
    }
}
