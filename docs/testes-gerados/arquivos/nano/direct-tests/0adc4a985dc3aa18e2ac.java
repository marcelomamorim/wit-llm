/* @test    @summary Test constructor of PBEWithSHA1AndRC2_128 in PKCS12PBECipherCore    @run main PKCS12PBECipherCoreWitupTest */

import java.security.NoSuchAlgorithmException;

public class PKCS12PBECipherCoreWitupTest {

    public static void main(String[] args) {
        testConstructor();
    }

    public static void testConstructor() {
        try {
            com.sun.crypto.provider.PKCS12PBECipherCore instance = new com.sun.crypto.provider.PKCS12PBECipherCore.PBEWithSHA1AndRC2_128();
            if (instance == null) {
                throw new AssertionError("Constructor returned null");
            }
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError("Constructor threw NoSuchAlgorithmException", e);
        }
    }
}
