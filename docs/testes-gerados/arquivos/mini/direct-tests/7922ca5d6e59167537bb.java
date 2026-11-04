/*
 * @test
 * @summary Test java.security.Signature.verify(byte[]) behavior
 * @run main SignatureWitupTest
 */

import java.security.Signature;
import java.security.SignatureException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class SignatureWitupTest {
    public static void main(String[] args) throws Exception {
        testVerifyWithoutInit();
        testVerifyWithValidSignature();
        testVerifyWithInvalidSignature();
    }

    private static void testVerifyWithoutInit() {
        try {
            Signature sig = Signature.getInstance("SHA256withDSA");
            // Do not initialize for verify
            sig.verify(new byte[0]);
            throw new RuntimeException("Expected SignatureException not thrown");
        } catch (SignatureException e) {
            String msg = e.getMessage();
            if (!msg.contains("object not initialized for verification")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }

    private static void testVerifyWithValidSignature() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.generateKeyPair();
        PrivateKey priv = kp.getPrivate();
        PublicKey pub = kp.getPublic();

        Signature sig = Signature.getInstance("SHA256withDSA");
        sig.initSign(priv);
        byte[] data = "Test data for signature".getBytes("UTF-8");
        sig.update(data);
        byte[] signatureBytes = sig.sign();

        Signature verifier = Signature.getInstance("SHA256withDSA");
        verifier.initVerify(pub);
        verifier.update(data);
        boolean result = verifier.verify(signatureBytes);
        if (!result) {
            throw new RuntimeException("Valid signature verification failed");
        }
    }

    private static void testVerifyWithInvalidSignature() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.generateKeyPair();
        PublicKey pub = kp.getPublic();

        Signature verifier = Signature.getInstance("SHA256withDSA");
        verifier.initVerify(pub);
        byte[] data = "Test data for signature".getBytes("UTF-8");
        verifier.update(data);

        // Corrupt signature bytes (empty or random)
        byte[] badSignature = new byte[] {0, 1, 2, 3, 4};
        boolean result = verifier.verify(badSignature);
        if (result) {
            throw new RuntimeException("Invalid signature verification unexpectedly succeeded");
        }
    }
}
