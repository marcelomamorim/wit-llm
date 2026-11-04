/*
 * @test
 * @summary Verifies that the SunJCE PBEWithSHA1AndRC4_40 cipher, implemented by PKCS12PBECipherCore, can be obtained through the public Cipher API and rejects unknown algorithm names
 * @run main PKCS12PBECipherCoreWitupTest
 */

import javax.crypto.Cipher;
import java.security.NoSuchAlgorithmException;

public class PKCS12PBECipherCoreWitupTest {

    public static void main(String[] args) throws Exception {
        Cipher c = Cipher.getInstance("PBEWithSHA1AndRC4_40", "SunJCE");
        check(c != null, "Cipher instance must not be null");
        check("SunJCE".equals(c.getProvider().getName()),
              "Provider must be SunJCE");
        check("PBEWithSHA1AndRC4_40".equals(c.getAlgorithm()),
              "Algorithm name must match the requested transformation");

        Cipher c2 = Cipher.getInstance("PBEWithSHA1AndRC4_40", "SunJCE");
        check(c2 != c, "Each getInstance call must return a new object");

        boolean threw = false;
        try {
            Cipher.getInstance("PBEWithSHA1AndRC4_NoSuchAlgo", "SunJCE");
        } catch (NoSuchAlgorithmException e) {
            threw = true;
        }
        check(threw, "Unknown algorithm must throw NoSuchAlgorithmException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
