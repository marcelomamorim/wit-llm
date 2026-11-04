/*
 * @test
 * @summary Test java.security.Signature update(byte) method behavior
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
        testUpdateWithoutInit();
        testUpdateAfterInitSign();
        testUpdateAfterInitVerify();
    }

    private static void testUpdateWithoutInit() {
        try {
            Signature sig = Signature.getInstance("SHA256withRSA");
            // Do not initialize sig with initSign or initVerify
            sig.update((byte)0x01);
            throw new RuntimeException("Expected SignatureException not thrown");
        } catch (SignatureException se) {
            String msg = se.getMessage();
            if (msg == null || !msg.contains("object not initialized")) {
                throw new RuntimeException("Unexpected SignatureException message: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception: " + e);
        }
    }

    private static void testUpdateAfterInitSign() throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(512);
        KeyPair kp = kpg.generateKeyPair();
        PrivateKey priv = kp.getPrivate();

        sig.initSign(priv);
        // Should not throw
        sig.update((byte)0x01);
    }

    private static void testUpdateAfterInitVerify() throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(512);
        KeyPair kp = kpg.generateKeyPair();
        PublicKey pub = kp.getPublic();

        sig.initVerify(pub);
        // Should not throw
        sig.update((byte)0x01);
    }
}
