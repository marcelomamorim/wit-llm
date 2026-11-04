/*
 * @test
 * @summary Verifies that the public PBEWithSHA1AndDESede cipher, which is backed by PKCS12PBECipherCore, can be obtained through the SunJCE provider and returns new instances
 * @run main PKCS12PBECipherCoreWitupTest
 */

import javax.crypto.Cipher;
import java.security.Provider;

public class PKCS12PBECipherCoreWitupTest {

    public static void main(String[] args) throws Exception {
        Cipher c1 = Cipher.getInstance("PBEWithSHA1AndDESede");
        Cipher c2 = Cipher.getInstance("PBEWithSHA1AndDESede");

        if (c1 == c2) {
            throw new RuntimeException("Expected a new Cipher instance on each getInstance call");
        }

        if (!"PBEWithSHA1AndDESede".equals(c1.getAlgorithm())) {
            throw new RuntimeException("Unexpected algorithm: " + c1.getAlgorithm());
        }

        Provider p = c1.getProvider();
        if (p == null || !"SunJCE".equals(p.getName())) {
            throw new RuntimeException("Unexpected provider: " + p);
        }
    }
}
