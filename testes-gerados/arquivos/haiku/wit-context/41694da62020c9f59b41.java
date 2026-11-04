/*
 * @test
 * @summary Tests Signature.update(byte[]) for uninitialized, null-data and valid signing states
 * @run main SignatureWitupTest
 */

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.security.SignatureException;

public class SignatureWitupTest {

    public static void main(String[] args) throws Exception {
        testUpdateUninitializedThrowsSignatureException();
        testUpdateNullDataInSigningStateThrowsNPE();
        testUpdateValidDataRoundTrip();
    }

    static void testUpdateUninitializedThrowsSignatureException() throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        try {
            sig.update(new byte[] {1, 2, 3});
            throw new RuntimeException("expected SignatureException for uninitialized Signature");
        } catch (SignatureException expected) {
            // expected
        }
    }

    static void testUpdateNullDataInSigningStateThrowsNPE() throws Exception {
        KeyPair kp = generateKeyPair();
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initSign(kp.getPrivate());
        try {
            sig.update((byte[]) null);
            throw new RuntimeException("expected NullPointerException for null data");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testUpdateValidDataRoundTrip() throws Exception {
        KeyPair kp = generateKeyPair();
        byte[] data = new byte[] {'a', 'b', 'c', 'd'};

        Signature signer = Signature.getInstance("SHA256withRSA");
        signer.initSign(kp.getPrivate());
        signer.update(data);
        signer.update(new byte[0]);
        byte[] sigBytes = signer.sign();
        if (sigBytes == null || sigBytes.length == 0) {
            throw new RuntimeException("signature must not be empty");
        }

        Signature verifier = Signature.getInstance("SHA256withRSA");
        verifier.initVerify(kp.getPublic());
        verifier.update(data);
        if (!verifier.verify(sigBytes)) {
            throw new RuntimeException("signature over updated data did not verify");
        }
    }

    static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        return kpg.generateKeyPair();
    }
}
