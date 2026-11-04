/*
 * @test
 * @summary Test java.security.Signature.update(ByteBuffer) method behavior
 * @run main SignatureWitupTest
 */

import java.security.Signature;
import java.security.SignatureException;
import java.nio.ByteBuffer;

public class SignatureWitupTest {
    public static void main(String[] args) throws Exception {
        testUpdateNullByteBuffer();
        testUpdateWithoutInit();
        testUpdateWithValidInit();
    }

    private static void testUpdateNullByteBuffer() throws Exception {
        Signature sig = Signature.getInstance("SHA256withDSA");
        sig.initSign(java.security.KeyPairGenerator.getInstance("DSA").generateKeyPair().getPrivate());
        try {
            sig.update((ByteBuffer) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testUpdateWithoutInit() throws Exception {
        Signature sig = Signature.getInstance("SHA256withDSA");
        ByteBuffer data = ByteBuffer.wrap(new byte[] {1, 2, 3});
        try {
            sig.update(data);
            throw new RuntimeException("Expected SignatureException not thrown");
        } catch (SignatureException se) {
            String msg = se.getMessage();
            if (msg == null || !msg.contains("object not initialized")) {
                throw new RuntimeException("SignatureException message unexpected: " + msg);
            }
        }
    }

    private static void testUpdateWithValidInit() throws Exception {
        Signature sig = Signature.getInstance("SHA256withDSA");
        java.security.KeyPairGenerator kpg = java.security.KeyPairGenerator.getInstance("DSA");
        java.security.KeyPair kp = kpg.generateKeyPair();
        sig.initSign(kp.getPrivate());
        ByteBuffer data = ByteBuffer.wrap(new byte[] {10, 20, 30});
        // Should not throw any exception
        sig.update(data);
    }
}
