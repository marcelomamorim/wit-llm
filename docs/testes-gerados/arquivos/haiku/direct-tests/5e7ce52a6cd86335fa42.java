/*
 * @test
 * @summary Verify Signature.update(ByteBuffer) state checks, null handling and observable signing/verification behavior
 * @run main SignatureWitupTest
 */

import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

public class SignatureWitupTest {

    public static void main(String[] args) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();

        testUninitializedThrows();
        testNullDataThrowsNPE(kp.getPrivate());
        testSignVerifyRoundTrip(kp);
    }

    static void testUninitializedThrows() throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        boolean thrown = false;
        try {
            sig.update(ByteBuffer.wrap(new byte[] {1, 2, 3}));
        } catch (SignatureException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected SignatureException for uninitialized Signature");
        }
    }

    static void testNullDataThrowsNPE(PrivateKey priv) throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initSign(priv);
        boolean thrown = false;
        try {
            sig.update((ByteBuffer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null ByteBuffer");
        }
    }

    static void testSignVerifyRoundTrip(KeyPair kp) throws Exception {
        byte[] data = "hello signature".getBytes("UTF-8");

        Signature signer = Signature.getInstance("SHA256withRSA");
        signer.initSign(kp.getPrivate());
        signer.update(ByteBuffer.wrap(data));
        byte[] sigBytes = signer.sign();

        PublicKey pub = kp.getPublic();
        Signature verifier = Signature.getInstance("SHA256withRSA");
        verifier.initVerify(pub);
        verifier.update(ByteBuffer.wrap(data));
        if (!verifier.verify(sigBytes)) {
            throw new RuntimeException("signature over ByteBuffer data did not verify");
        }

        Signature wrongData = Signature.getInstance("SHA256withRSA");
        wrongData.initVerify(pub);
        wrongData.update(ByteBuffer.wrap("tampered".getBytes("UTF-8")));
        if (wrongData.verify(sigBytes)) {
            throw new RuntimeException("signature verified for different data");
        }
    }
}
