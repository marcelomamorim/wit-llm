/*
 * @test
 * @summary Test PKCS10 constructor with PublicKey and PKCS10Attributes
 * @run main PKCS10WitupTest
 */

import java.security.*;
import sun.security.pkcs10.PKCS10;
import sun.security.pkcs10.PKCS10Attributes;

public class PKCS10WitupTest {
    public static void main(String[] args) throws Exception {
        // Generate a simple RSA key pair for testing
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(512);
        KeyPair kp = kpg.generateKeyPair();
        PublicKey pubKey = kp.getPublic();

        // Create empty PKCS10Attributes
        PKCS10Attributes attrs = new PKCS10Attributes();

        // Construct PKCS10 with public key and attributes
        PKCS10 pkcs10 = new PKCS10(pubKey, attrs);

        // Validate that the object is not null
        if (pkcs10 == null) {
            throw new RuntimeException("PKCS10 constructor returned null");
        }

        // Construct PKCS10 with null attributes (allowed by source code)
        PKCS10 pkcs10NullAttrs = new PKCS10(pubKey, null);
        if (pkcs10NullAttrs == null) {
            throw new RuntimeException("PKCS10 constructor with null attributes returned null");
        }

        // Construct PKCS10 with null public key (allowed by source code)
        PKCS10 pkcs10NullKey = new PKCS10(null, attrs);
        if (pkcs10NullKey == null) {
            throw new RuntimeException("PKCS10 constructor with null public key returned null");
        }

        // If reached here, test passed
        System.out.println("PASS");
    }
}
