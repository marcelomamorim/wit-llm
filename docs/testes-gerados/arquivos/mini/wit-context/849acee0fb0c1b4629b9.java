/*
 * @test
 * @summary Test constructor PBEWithSHA1AndDESede of PKCS12PBECipherCore
 * @run main PKCS12PBECipherCoreWitupTest
 */

import com.sun.crypto.provider.PKCS12PBECipherCore;
import java.security.NoSuchAlgorithmException;

public class PKCS12PBECipherCoreWitupTest {
    public static void main(String[] args) throws Exception {
        // Test that PBEWithSHA1AndDESede constructor creates instance without exception
        PKCS12PBECipherCore.PBEWithSHA1AndDESede instance = new PKCS12PBECipherCore.PBEWithSHA1AndDESede();
        if (instance == null) {
            throw new RuntimeException("Constructor returned null instance");
        }
    }
}
