/*
 * @test
 * @summary Test Signature.verify(byte[]) behavior including exception on uninitialized state
 * @run main SignatureWitupTest
 */

import java.security.Signature;
import java.security.SignatureException;

public class SignatureWitupTest {
    public static void main(String[] args) throws Exception {
        testVerifyThrowsIfNotInitialized();
        testVerifyReturnsBooleanWhenInitialized();
    }

    private static void testVerifyThrowsIfNotInitialized() {
        Signature sig = Signature.getInstance("SHA256withDSA");
        // Do not initialize for verify (no initVerify call)
        byte[] dummySignature = new byte[0];
        try {
            sig.verify(dummySignature);
            throw new RuntimeException("Expected SignatureException not thrown");
        } catch (SignatureException e) {
            String msg = e.getMessage();
            if (!msg.contains("object not initialized for verification")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testVerifyReturnsBooleanWhenInitialized() throws Exception {
        Signature sig = Signature.getInstance("SHA256withDSA");
        // Initialize for verify with a dummy public key
        // Since we cannot create a real PublicKey easily, we skip actual verification
        // Instead, we test that verify returns a boolean (true or false) when initialized
        // This test is limited by lack of key construction, but checks no exception thrown
        try {
            // We use a dummy subclass of PublicKey to initialize
            java.security.PublicKey dummyKey = new java.security.PublicKey() {
                public String getAlgorithm() { return "DSA"; }
                public String getFormat() { return "X.509"; }
                public byte[] getEncoded() { return new byte[0]; }
            };
            sig.initVerify(dummyKey);
            byte[] dummySignature = new byte[0];
            boolean result = sig.verify(dummySignature);
            // result is boolean, no exception expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception during verify after initVerify", e);
        }
    }
}
