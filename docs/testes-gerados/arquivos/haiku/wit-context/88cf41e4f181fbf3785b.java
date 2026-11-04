/*
 * @test
 * @summary Witup-derived checks for CertificateX509Key.set exception path on unrecognized attribute names
 * @modules java.base/sun.security.x509
 * @run main CertificateX509KeyWitupTest
 */
import java.io.IOException;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import sun.security.x509.CertificateX509Key;

public class CertificateX509KeyWitupTest {
    public static void main(String[] args) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        PublicKey k1 = kpg.generateKeyPair().getPublic();
        PublicKey k2 = kpg.generateKeyPair().getPublic();

        CertificateX509Key cert = new CertificateX509Key(k1);
        cert.set("value", k2);
        if (!k2.equals(cert.get("value"))) {
            throw new RuntimeException("set(value) did not update the key");
        }

        cert.set("VALUE", k1);
        if (!k1.equals(cert.get("value"))) {
            throw new RuntimeException("set is expected to be case-insensitive");
        }

        boolean thrown = false;
        try {
            cert.set("other", k2);
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for unrecognized attribute name");
        }

        if (!k1.equals(cert.get("value"))) {
            throw new RuntimeException("Key changed after rejected set");
        }
    }
}
