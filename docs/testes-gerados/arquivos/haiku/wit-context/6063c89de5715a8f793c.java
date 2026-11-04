/*
 * @test
 * @summary Signature.sign() must throw SignatureException when the object is not initialized for signing
 * @run main SignatureWitupTest
 */

import java.security.Signature;
import java.security.SignatureException;

public class SignatureWitupTest {
    public static void main(String[] args) throws Exception {
        String[] algorithms = { "SHA256withRSA", "SHA1withDSA" };
        for (String algorithm : algorithms) {
            Signature sig = Signature.getInstance(algorithm);
            boolean thrown = false;
            try {
                sig.sign();
            } catch (SignatureException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("SignatureException expected for uninitialized " + algorithm);
            }
        }
    }
}
