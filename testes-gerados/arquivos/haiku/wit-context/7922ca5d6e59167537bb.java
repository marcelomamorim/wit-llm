/*
 * @test
 * @summary Verify that Signature.verify(byte[]) throws SignatureException when the object is not in the VERIFY state
 * @run main SignatureWitupTest
 */

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.security.SignatureException;

public class SignatureWitupTest {

    public static void main(String[] args) throws Exception {
        testVerifyWithoutInitThrows();
        testVerifyAfterInitSignThrows();
    }

    static void testVerifyWithoutInitThrows() throws Exception {
        Signature s = Signature.getInstance("SHA256withRSA");
        try {
            s.verify(new byte[] {1, 2, 3});
            throw new RuntimeException("expected SignatureException for uninitialized object");
        } catch (SignatureException e) {
            // expected
        }
    }

    static void testVerifyAfterInitSignThrows() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        Signature s = Signature.getInstance("SHA256withRSA");
        s.initSign(kp.getPrivate());
        try {
            s.verify(new byte[] {1, 2, 3});
            throw new RuntimeException("expected SignatureException for object in SIGN state");
        } catch (SignatureException e) {
            // expected
        }
    }
}
