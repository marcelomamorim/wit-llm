/*
 * @test
 * @summary Test java.security.Signature.update(byte) for correct exception and normal behavior
 * @run main SignatureWitupTest
 */

import java.security.Signature;
import java.security.SignatureException;

public class SignatureWitupTest {
    public static void main(String[] args) throws Exception {
        testUpdateThrowsIfNotInitialized();
        testUpdateAcceptsByteWhenInitialized();
    }

    private static void testUpdateThrowsIfNotInitialized() {
        Signature sig = null;
        try {
            sig = Signature.getInstance("SHA256withDSA");
        } catch (Exception e) {
            throw new RuntimeException("Failed to get Signature instance", e);
        }

        // Do NOT initialize sig with initSign or initVerify, so state != SIGN or VERIFY
        // Expect SignatureException when calling update(byte)
        try {
            sig.update((byte)0x01);
            throw new RuntimeException("Expected SignatureException not thrown");
        } catch (SignatureException se) {
            // Expected
            String msg = se.getMessage();
            if (msg == null || !msg.contains("not initialized")) {
                throw new RuntimeException("SignatureException message unexpected: " + msg);
            }
        }
    }

    private static void testUpdateAcceptsByteWhenInitialized() throws Exception {
        Signature sig = Signature.getInstance("SHA256withDSA");

        // Initialize for signing with a dummy private key to reach state SIGN
        // Since we cannot create a real PrivateKey easily, we test initVerify with a dummy public key
        // to reach state VERIFY, which also allows update(byte) without exception.
        // Use a minimal dummy PublicKey implementation.
        java.security.PublicKey dummyPubKey = new java.security.PublicKey() {
            public String getAlgorithm() { return "DSA"; }
            public String getFormat() { return null; }
            public byte[] getEncoded() { return null; }
        };

        sig.initVerify(dummyPubKey);

        // Now update(byte) should not throw
        sig.update((byte)0x01);
        sig.update((byte)0xFF);
    }
}
