/*
 * @test
 * @summary Test constructor PBEWithSHA1AndRC2_40 of PKCS12PBECipherCore for expected behavior
 * @run main com.sun.crypto.provider.PKCS12PBECipherCoreWitupTest
 */

import com.sun.crypto.provider.PKCS12PBECipherCore;
import java.security.NoSuchAlgorithmException;

public class PKCS12PBECipherCoreWitupTest {
    public static void main(String[] args) throws Exception {
        // Test that constructor PBEWithSHA1AndRC2_40 creates instance without throwing
        PKCS12PBECipherCore.PBEWithSHA1AndRC2_40 instance = new PKCS12PBECipherCore.PBEWithSHA1AndRC2_40();
        if (instance == null) {
            throw new RuntimeException("Constructor returned null instance");
        }
    }
}
