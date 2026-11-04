/*
 * @test
 * @summary Verify CertificateX509Key.delete clears the key for KEY name case-insensitively and rejects unknown names
 * @modules java.base/sun.security.x509
 * @run main CertificateX509KeyWitupTest
 */

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import sun.security.x509.CertificateX509Key;

public class CertificateX509KeyWitupTest {

    public static void main(String[] args) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();

        testDeleteValueClearsKey(kp);
        testDeleteIsCaseInsensitive(kp);
        testDeleteUnknownNameThrows(kp);
        testDeleteTwiceDoesNotThrow(kp);
    }

    static void testDeleteValueClearsKey(KeyPair kp) throws Exception {
        CertificateX509Key c = new CertificateX509Key(kp.getPublic());
        if (c.toString().isEmpty()) {
            throw new RuntimeException("Expected non-empty string before delete");
        }
        c.delete("value");
        if (!c.toString().isEmpty()) {
            throw new RuntimeException("Expected empty string after delete");
        }
        if (c.get("value") != null) {
            throw new RuntimeException("Expected null key after delete");
        }
    }

    static void testDeleteIsCaseInsensitive(KeyPair kp) throws Exception {
        CertificateX509Key c = new CertificateX509Key(kp.getPublic());
        c.delete("VALUE");
        if (!c.toString().isEmpty()) {
            throw new RuntimeException("Case-insensitive delete failed");
        }
    }

    static void testDeleteUnknownNameThrows(KeyPair kp) throws Exception {
        CertificateX509Key c = new CertificateX509Key(kp.getPublic());
        try {
            c.delete("bogus");
            throw new RuntimeException("Expected IOException for unknown attribute name");
        } catch (IOException expected) {
            // expected
        }
        if (c.toString().isEmpty()) {
            throw new RuntimeException("Key must remain after failed delete");
        }
    }

    static void testDeleteTwiceDoesNotThrow(KeyPair kp) throws Exception {
        CertificateX509Key c = new CertificateX509Key(kp.getPublic());
        c.delete("value");
        c.delete("value");
        if (!c.toString().isEmpty()) {
            throw new RuntimeException("Expected empty string after repeated delete");
        }
    }
}
