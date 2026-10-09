/*
 * @test
 * @summary Test java.security.Signature.sign() method behavior
 * @run main SignatureWitupTest_P1eb3f43369e0
 */

import java.security.Signature;
import java.security.SignatureException;

public class SignatureWitupTest_P1eb3f43369e0 {
    public static void main(String[] args) throws Exception {
        testSignWithoutInit();
        testSignAfterInitSign();
    }

    private static void testSignWithoutInit() {
        try {
            Signature sig = Signature.getInstance("SHA256withDSA");
            // Do not initialize for signing
            sig.sign();
            throw new RuntimeException("Expected SignatureException not thrown");
        } catch (SignatureException e) {
            String msg = e.getMessage();
            if (!msg.contains("object not initialized for signing")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type: " + e);
        }
    }

    private static void testSignAfterInitSign() throws Exception {
        Signature sig = Signature.getInstance("SHA256withDSA");
        // Initialize for signing with a dummy private key
        // Use a generated key pair for DSA
        java.security.KeyPairGenerator kpg = java.security.KeyPairGenerator.getInstance("DSA");
        kpg.initialize(1024);
        java.security.KeyPair kp = kpg.generateKeyPair();
        sig.initSign(kp.getPrivate());
        // Update with some data
        byte[] data = "test data".getBytes("UTF-8");
        sig.update(data);
        byte[] signature = sig.sign();
        if (signature == null || signature.length == 0) {
            throw new RuntimeException("Signature returned is null or empty");
        }
    }
}
