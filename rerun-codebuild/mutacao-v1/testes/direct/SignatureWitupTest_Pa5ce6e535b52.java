/* @test    @summary Test the verify method of java.security.Signature for correct behavior and exception handling    @run main SignatureWitupTest_Pa5ce6e535b52 */

import java.security.*;

public class SignatureWitupTest_Pa5ce6e535b52 {

    public static void main(String[] args) throws Exception {
        testVerifyWithoutInit();
        testVerifyWithInvalidSignature();
        testVerifyWithValidSignature();
    }

    private static void testVerifyWithoutInit() throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        byte[] dummySignature = new byte[] {1, 2, 3};
        try {
            sig.verify(dummySignature);
            throw new AssertionError("Expected SignatureException not thrown when verify called without init");
        } catch (SignatureException e) {
            // Expected exception
        }
    }

    private static void testVerifyWithInvalidSignature() throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        sig.initVerify(kp.getPublic());
        byte[] data = "test data".getBytes("UTF-8");
        sig.update(data);
        byte[] invalidSignature = new byte[] {0, 0, 0, 0};
        boolean result = sig.verify(invalidSignature);
        if (result) {
            throw new AssertionError("Verification should fail with invalid signature");
        }
    }

    private static void testVerifyWithValidSignature() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initSign(kp.getPrivate());
        byte[] data = "valid data".getBytes("UTF-8");
        sig.update(data);
        byte[] signatureBytes = sig.sign();
        Signature verifier = Signature.getInstance("SHA256withRSA");
        verifier.initVerify(kp.getPublic());
        verifier.update(data);
        boolean verifyResult = verifier.verify(signatureBytes);
        if (!verifyResult) {
            throw new AssertionError("Verification failed with valid signature");
        }
    }
}
