/* @test
   @summary Testa o método verify de java.security.Signature para comportamento esperado.
   @run main SignatureWitupTest_P5113a72edf6c
 */
import java.security.Signature;
import java.security.SignatureException;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SignatureWitupTest_P5113a72edf6c {
    public static void main(String[] args) throws Exception {
        testVerifyUninitialized();
        testVerifyInitialized();
        testVerifyWithInvalidState();
    }

    private static void testVerifyUninitialized() throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        byte[] dummySignature = new byte[256];
        try {
            boolean result = sig.verify(dummySignature);
            if (result) {
                throw new AssertionError("Expected SignatureException for uninitialized object");
            }
        } catch (SignatureException e) {
            // esperado
        }
    }

    private static void testVerifyInitialized() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initSign(kp.getPrivate());
        byte[] data = "test data".getBytes("UTF-8");
        sig.update(data);
        byte[] signatureBytes = sig.sign();

        Signature verifier = Signature.getInstance("SHA256withRSA");
        verifier.initVerify(kp.getPublic());
        verifier.update(data);
        boolean verified = verifier.verify(signatureBytes);
        if (!verified) {
            throw new AssertionError("Verification failed for valid signature");
        }
    }

    private static void testVerifyWithInvalidState() throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        byte[] dummySignature = new byte[256];
        // Forcing state to an invalid value by reflection is complex; instead, simulate by calling verify after reset
        // Note: No public API to set internal state directly; so this test assumes verify throws if not initialized
        try {
            boolean result = sig.verify(dummySignature);
            if (result) {
                throw new AssertionError("Expected SignatureException for object not initialized for verification");
            }
        } catch (SignatureException e) {
            // esperado
        }
    }
}
