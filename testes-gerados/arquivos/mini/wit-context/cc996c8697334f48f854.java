/*
 * @test
 * @summary Test constructor PKCS10(PublicKey, PKCS10Attributes) basic assignment
 * @run main PKCS10WitupTest
 */

import java.security.*;
import sun.security.pkcs10.PKCS10;
sun.security.pkcs10.PKCS10Attributes;

public class PKCS10WitupTest {
    public static void main(String[] args) throws Exception {
        // Generate a dummy RSA key pair for testing
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(512);
        KeyPair kp = kpg.generateKeyPair();
        PublicKey pubKey = kp.getPublic();

        // Create empty PKCS10Attributes instance
        PKCS10Attributes attrs = new PKCS10Attributes();

        // Construct PKCS10 with public key and attributes
        PKCS10 pkcs10 = new PKCS10(pubKey, attrs);

        // Check that the subjectPublicKeyInfo field is set to the given public key
        if (pkcs10.subjectPublicKeyInfo != pubKey) {
            throw new RuntimeException("subjectPublicKeyInfo not set correctly");
        }

        // Check that the attributeSet field is set to the given attributes
        if (pkcs10.attributeSet != attrs) {
            throw new RuntimeException("attributeSet not set correctly");
        }

        // Additional sanity: attributeSet should not be null
        if (pkcs10.attributeSet == null) {
            throw new RuntimeException("attributeSet is null");
        }

        System.out.println("Test passed.");
    }
}
