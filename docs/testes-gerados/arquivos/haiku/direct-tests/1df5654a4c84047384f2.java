/*
 * @test
 * @summary Verifies that the PBEWithSHA1AndRC4_40 cipher, whose constructor is the target, is obtainable through the public Cipher API from SunJCE
 * @run main PKCS12PBECipherCoreWitupTest
 */

import javax.crypto.Cipher;

public class PKCS12PBECipherCoreWitupTest {

    public static void main(String[] args) throws Exception {
        Cipher c = Cipher.getInstance("PBEWithSHA1AndRC4_40", "SunJCE");
        if (c == null) {
            throw new RuntimeException("Cipher.getInstance returned null");
        }
        if (!"SunJCE".equals(c.getProvider().getName())) {
            throw new RuntimeException("Unexpected provider: " + c.getProvider().getName());
        }
        if (!"PBEWithSHA1AndRC4_40".equals(c.getAlgorithm())) {
            throw new RuntimeException("Unexpected algorithm: " + c.getAlgorithm());
        }

        Cipher d = Cipher.getInstance("PBEWithSHA1AndRC4_40", "SunJCE");
        if (c == d) {
            throw new RuntimeException("Expected a new cipher instance on each getInstance call");
        }
    }
}
