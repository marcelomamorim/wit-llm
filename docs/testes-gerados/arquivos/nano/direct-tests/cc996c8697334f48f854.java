/* @test    @summary Test constructor PKCS10(PublicKey, PKCS10Attributes) for correct instantiation and null handling    @run main PKCS10WitupTest */

import java.security.*;
import sun.security.pkcs10.PKCS10;
import sun.security.pkcs10.PKCS10Attributes;

public class PKCS10WitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithValidKeyAndAttributes();
        testConstructorWithNullAttributes();
        testConstructorWithNullPublicKey();
    }

    private static void testConstructorWithValidKeyAndAttributes() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.generateKeyPair();
        PKCS10Attributes attrs = new PKCS10Attributes();
        PKCS10 pkcs10 = new PKCS10(kp.getPublic(), attrs);
        if (pkcs10 == null) {
            throw new AssertionError("PKCS10 instance should not be null");
        }
    }

    private static void testConstructorWithNullAttributes() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.generateKeyPair();
        PKCS10 pkcs10 = new PKCS10(kp.getPublic(), null);
        if (pkcs10 == null) {
            throw new AssertionError("PKCS10 instance should not be null when attributes are null");
        }
    }

    private static void testConstructorWithNullPublicKey() throws Exception {
        try {
            new PKCS10(null, new PKCS10Attributes());
            throw new AssertionError("Constructor should throw NullPointerException when publicKey is null");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
