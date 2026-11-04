/*
 * @test
 * @summary Checks that Signature.update(byte) rejects uninitialized objects and accepts bytes after initSign/initVerify
 * @run main SignatureWitupTest
 */

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

public class SignatureWitupTest {

    public static void main(String[] args) throws Exception {
        testUpdateUninitializedThrows();
        testUpdateAfterInitVerifyDoesNotThrow();
        testUpdateAfterInitSignDoesNotThrow();
        testSignVerifyRoundTrip();
    }

    static void testUpdateUninitializedThrows() throws Exception {
        Signature s = Signature.getInstance("SHA256withRSA");
        boolean thrown = false;
        try {
            s.update((byte) 1);
        } catch (SignatureException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("update(byte) on uninitialized Signature must throw SignatureException");
        }
    }

    static void testUpdateAfterInitVerifyDoesNotThrow() throws Exception {
        PublicKey pub = generateKeyPair().getPublic();
        Signature s = Signature.getInstance("SHA256withRSA");
        s.initVerify(pub);
        s.update((byte) 0x42);
    }

    static void testUpdateAfterInitSignDoesNotThrow() throws Exception {
        PrivateKey priv = generateKeyPair().getPrivate();
        Signature s = Signature.getInstance("SHA256withRSA");
        s.initSign(priv);
        s.update((byte) 0x42);
    }

    static void testSignVerifyRoundTrip() throws Exception {
        KeyPair kp = generateKeyPair();
        byte[] data = {1, 2, 3, 4, 5};

        Signature signer = Signature.getInstance("SHA256withRSA");
        signer.initSign(kp.getPrivate());
        for (byte b : data) {
            signer.update(b);
        }
        byte[] sig = signer.sign();

        Signature verifier = Signature.getInstance("SHA256withRSA");
        verifier.initVerify(kp.getPublic());
        for (byte b : data) {
            verifier.update(b);
        }
        if (!verifier.verify(sig)) {
            throw new RuntimeException("signature generated via update(byte) must verify");
        }

        Signature tampered = Signature.getInstance("SHA256withRSA");
        tampered.initVerify(kp.getPublic());
        byte[] bad = {1, 2, 3, 4, 6};
        for (byte b : bad) {
            tampered.update(b);
        }
        if (tampered.verify(sig)) {
            throw new RuntimeException("signature must not verify over modified data");
        }
    }

    static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        return kpg.generateKeyPair();
    }
}
