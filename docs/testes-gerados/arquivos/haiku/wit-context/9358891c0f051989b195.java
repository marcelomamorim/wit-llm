/*
 * @test
 * @summary Verifies that the SunJCE PBEWithSHA1AndRC2_40 cipher, whose constructor builds a PKCS12PBECipherCore for RC2, is obtainable through the public Cipher API
 * @run main PKCS12PBECipherCoreWitupTest
 */

import javax.crypto.Cipher;

public class PKCS12PBECipherCoreWitupTest {

    public static void main(String[] args) throws Exception {
        Cipher c = Cipher.getInstance("PBEWithSHA1AndRC2_40", "SunJCE");
        if (c == null) {
            throw new RuntimeException("Cipher.getInstance returned null");
        }
        if (!"PBEWithSHA1AndRC2_40".equals(c.getAlgorithm())) {
            throw new RuntimeException("Unexpected algorithm: " + c.getAlgorithm());
        }
        if (!"SunJCE".equals(c.getProvider().getName())) {
            throw new RuntimeException("Unexpected provider: " + c.getProvider().getName());
        }

        boolean thrown = false;
        try {
            Cipher.getInstance("PBEWithSHA1AndRC2_40", "NoSuchProviderXyz");
        } catch (java.security.NoSuchProviderException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NoSuchProviderException for unknown provider");
        }
    }
}
