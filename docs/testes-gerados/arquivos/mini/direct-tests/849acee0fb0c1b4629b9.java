/*
 * @test
 * @summary Test constructor PBEWithSHA1AndDESede of PKCS12PBECipherCore
 * @run main PKCS12PBECipherCoreWitupTest
 */

import java.security.NoSuchAlgorithmException;
import com.sun.crypto.provider.PKCS12PBECipherCore;

public class PKCS12PBECipherCoreWitupTest {
    public static void main(String[] args) throws Exception {
        // Test that constructor PBEWithSHA1AndDESede creates an instance without exception
        PKCS12PBECipherCore.PBEWithSHA1AndDESede instance = new PKCS12PBECipherCore.PBEWithSHA1AndDESede();
        if (instance == null) {
            throw new RuntimeException("Constructor returned null");
        }
    }
}
