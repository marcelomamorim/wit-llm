/*
 * @test
 * @summary Verifies that the PKCS10(PublicKey, PKCS10Attributes) constructor accepts a public key with null attributes and yields a usable object
 * @modules java.base/sun.security.pkcs10
 * @run main PKCS10WitupTest
 */

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import sun.security.pkcs10.PKCS10;

public class PKCS10WitupTest {
    public static void main(String[] args) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.generateKeyPair();
        PublicKey pub = kp.getPublic();

        PKCS10 request = new PKCS10(pub, null);
        if (request == null) {
            throw new RuntimeException("PKCS10 constructor returned null");
        }
    }
}
