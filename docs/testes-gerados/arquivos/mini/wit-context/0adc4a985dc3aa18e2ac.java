/*
 * @test
 * @summary Test constructor PBEWithSHA1AndRC2_128 of PKCS12PBECipherCore for expected behavior
 * @run main PKCS12PBECipherCoreWitupTest
 */

import java.security.NoSuchAlgorithmException;
import com.sun.crypto.provider.PKCS12PBECipherCore;

public class PKCS12PBECipherCoreWitupTest {
    public static void main(String[] args) throws Exception {
        // Test that constructor PBEWithSHA1AndRC2_128 creates an instance without throwing
        PKCS12PBECipherCore.PBEWithSHA1AndRC2_128 instance = new PKCS12PBECipherCore.PBEWithSHA1AndRC2_128();
        if (instance == null) {
            throw new AssertionError("Constructor returned null instance");
        }
    }
}
