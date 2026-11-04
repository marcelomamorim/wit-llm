/*
 * @test
 * @summary Verify CertificateX509Key.delete clears the value attribute and rejects unknown attribute names
 * @modules java.base/sun.security.x509
 * @run main CertificateX509KeyWitupTest
 */
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import sun.security.x509.CertificateX509Key;

public class CertificateX509KeyWitupTest {

    public static void main(String[] args) throws Exception {
        PublicKey pk = generateKey();
        testDeleteValueClearsKey(pk);
        testDeleteIsCaseInsensitive(pk);
        testDeleteUnknownNameThrowsAndKeepsKey(pk);
        testDeleteEmptyNameThrowsAndKeepsKey(pk);
    }

    static PublicKey generateKey() throws Exception {
        KeyPairGenerator g = KeyPairGenerator.getInstance("RSA");
        g.initialize(2048);
        KeyPair kp = g.generateKeyPair();
        return kp.getPublic();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testDeleteValueClearsKey(PublicKey pk) throws Exception {
        CertificateX509Key c = new CertificateX509Key(pk);
        c.delete(CertificateX509Key.KEY);
        check(c.get(CertificateX509Key.KEY) == null, "key should be null after delete(\"value\")");
        check(c.toString().equals(""), "toString should be empty after key deletion");
    }

    static void testDeleteIsCaseInsensitive(PublicKey pk) throws Exception {
        CertificateX509Key c = new CertificateX509Key(pk);
        c.delete("Value");
        check(c.get(CertificateX509Key.KEY) == null, "delete should ignore case for \"value\"");
    }

    static void testDeleteUnknownNameThrowsAndKeepsKey(PublicKey pk) throws Exception {
        CertificateX509Key c = new CertificateX509Key(pk);
        boolean thrown = false;
        try {
            c.delete("other");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "delete of unknown name should throw IOException");
        check(c.get(CertificateX509Key.KEY) == pk, "key must remain unchanged after failed delete");
    }

    static void testDeleteEmptyNameThrowsAndKeepsKey(PublicKey pk) throws Exception {
        CertificateX509Key c = new CertificateX509Key(pk);
        boolean thrown = false;
        try {
            c.delete("");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "delete of empty name should throw IOException");
        check(c.get(CertificateX509Key.KEY) == pk, "key must remain unchanged after failed delete of empty name");
    }
}
