/*
 * @test
 * @summary Signature.verify(byte[]) must throw SignatureException when not in VERIFY state and must verify correctly after initVerify
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
        byte[] data = "witup-data".getBytes("UTF-8");

        // Case 1: uninitialized object -> verify must throw SignatureException
        Signature uninit = Signature.getInstance("SHA256withRSA");
        try {
            uninit.verify(new byte[256]);
            throw new AssertionError("expected SignatureException for uninitialized object");
        } catch (SignatureException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not initialized")) {
                throw new AssertionError("unexpected message: " + msg);
            }
        }

        // Case 2: object in SIGN state -> verify must throw SignatureException
        Signature signer = Signature.getInstance("SHA256withRSA");
        signer.initSign(kp.getPrivate());
        signer.update(data);
        byte[] sig = signer.sign();
        try {
            signer.verify(sig);
            throw new AssertionError("expected SignatureException in SIGN state");
        } catch (SignatureException e) {
            // expected
        }

        // Case 3: valid signature in VERIFY state -> true
        Signature verifier = Signature.getInstance("SHA256withRSA");
        verifier.initVerify(kp.getPublic());
        verifier.update(data);
        if (!verifier.verify(sig)) {
            throw new AssertionError("valid signature was rejected");
        }

        // Case 4: tampered signature of same length in VERIFY state -> false
        byte[] tampered = sig.clone();
        tampered[tampered.length - 1] ^= 0x01;
        Signature verifier2 = Signature.getInstance("SHA256withRSA");
        verifier2.initVerify(kp.getPublic());
        verifier2.update(data);
        if (verifier2.verify(tampered)) {
            throw new AssertionError("tampered signature was accepted");
        }
    }
}
