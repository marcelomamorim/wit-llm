/*
 * @test
 * @summary Test constructor PBEWithSHA1AndRC4_40 of PKCS12PBECipherCore for expected behavior
 * @run main com.sun.crypto.provider.PKCS12PBECipherCoreWitupTest
 */

import com.sun.crypto.provider.PKCS12PBECipherCore;
import java.security.NoSuchAlgorithmException;

public class PKCS12PBECipherCoreWitupTest {
    public static void main(String[] args) throws Exception {
        // Test that the constructor PBEWithSHA1AndRC4_40 creates an instance without throwing
        PKCS12PBECipherCore.PBEWithSHA1AndRC4_40 instance = null;
        try {
            instance = new PKCS12PBECipherCore.PBEWithSHA1AndRC4_40();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Constructor threw NoSuchAlgorithmException unexpectedly", e);
        }
        if (instance == null) {
            throw new RuntimeException("Constructor returned null instance");
        }
    }
}
