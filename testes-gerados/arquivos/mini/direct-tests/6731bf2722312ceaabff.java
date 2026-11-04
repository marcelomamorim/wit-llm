/*
 * @test
 * @summary Test constructor PBEWithSHA1AndRC4_128 of PKCS12PBECipherCore
 * @run main PKCS12PBECipherCoreWitupTest
 */

import java.security.NoSuchAlgorithmException;

public class PKCS12PBECipherCoreWitupTest {
    public static void main(String[] args) throws Exception {
        // Test that constructor PBEWithSHA1AndRC4_128() creates an instance without exception
        PKCS12PBECipherCore instance = null;
        try {
            instance = new PKCS12PBECipherCore.PBEWithSHA1AndRC4_128();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Constructor threw NoSuchAlgorithmException", e);
        }
        if (instance == null) {
            throw new RuntimeException("Constructor returned null instance");
        }
        // No further observable behavior to test from constructor
        System.out.println("PASS");
    }
}
