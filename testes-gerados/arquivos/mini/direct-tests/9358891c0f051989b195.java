/*
 * @test
 * @summary Test constructor PBEWithSHA1AndRC2_40 of PKCS12PBECipherCore
 * @run main PKCS12PBECipherCoreWitupTest
 */

import java.security.NoSuchAlgorithmException;

public class PKCS12PBECipherCoreWitupTest {
    public static void main(String[] args) throws Exception {
        // Test that constructor PBEWithSHA1AndRC2_40 does not throw and creates non-null instance
        PKCS12PBECipherCore.PBEWithSHA1AndRC2_40 instance = new PKCS12PBECipherCore.PBEWithSHA1AndRC2_40();
        if (instance == null) {
            throw new RuntimeException("Constructor returned null instance");
        }
    }
}
