/*
 * @test
 * @summary Verifies through the public javax.crypto API that the PBEWithSHA1AndRC2_128 provider service can be instantiated and reports the expected algorithm and provider
 * @run main PKCS12PBECipherCoreWitupTest
 */

import javax.crypto.Cipher;

public class PKCS12PBECipherCoreWitupTest {

    public static void main(String[] args) throws Exception {
        Cipher c = Cipher.getInstance("PBEWithSHA1AndRC2_128");
        if (c == null) {
            throw new RuntimeException("Cipher.getInstance returned null");
        }
        if (!"PBEWithSHA1AndRC2_128".equals(c.getAlgorithm())) {
            throw new RuntimeException("Unexpected algorithm: " + c.getAlgorithm());
        }
        if (!"SunJCE".equals(c.getProvider().getName())) {
            throw new RuntimeException("Unexpected provider: " + c.getProvider().getName());
        }
    }
}
