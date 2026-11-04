/*
 * @test
 * @summary Verifies Signature.sign() behavior for uninitialized and verify-initialized objects, and a successful signing round trip
 * @run main SignatureWitupTest
 */

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.security.SignatureException;

public class SignatureWitupTest {

    public static void main(String[] args) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();

        // Case 1: freshly obtained object, never initialized -> sign() must throw
        Signature s1 = Signature.getInstance("SHA256withRSA");
        try {
            s1.sign();
            throw new AssertionError("expected SignatureException for uninitialized object");
        } catch (SignatureException e) {
            if (e.getMessage() == null || !e.getMessage().contains("not initialized")) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }

        // Case 2: object initialized for verification -> sign() must throw
        Signature s2 = Signature.getInstance("SHA256withRSA");
        s2.initVerify(kp.getPublic());
        try {
            s2.sign();
            throw new AssertionError("expected SignatureException for verify-initialized object");
        } catch (SignatureException e) {
            if (e.getMessage() == null || !e.getMessage().contains("not initialized")) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }

        // Case 3: object initialized for signing -> sign() returns a non-empty signature
        byte[] data = "witup-data".getBytes("UTF-8");
        Signature signer = Signature.getInstance("SHA256withRSA");
        signer.initSign(kp.getPrivate());
        signer.update(data);
        byte[] sig = signer.sign();
        if (sig == null || sig.length != 256) {
            throw new AssertionError("unexpected signature length: " +
                    (sig == null ? "null" : String.valueOf(sig.length)));
        }

        // Case 4: the produced signature verifies with the matching public key
        Signature verifier = Signature.getInstance("SHA256withRSA");
        verifier.initVerify(kp.getPublic());
        verifier.update(data);
        if (!verifier.verify(sig)) {
            throw new AssertionError("signature produced by sign() did not verify");
        }
    }
}
