/*
 * @test
 * @summary Test constructor PBEWithSHA1AndRC2_128 of PKCS12PBECipherCore
 * @run main PKCS12PBECipherCoreWitupTest
 */

import java.security.NoSuchAlgorithmException;

public class PKCS12PBECipherCoreWitupTest {
    public static void main(String[] args) throws Exception {
        // Test that constructor does not throw and initializes core
        PKCS12PBECipherCore.PBEWithSHA1AndRC2_128 instance = new PKCS12PBECipherCore.PBEWithSHA1AndRC2_128();
        if (instance == null) {
            throw new RuntimeException("Constructor returned null");
        }
    }
}
