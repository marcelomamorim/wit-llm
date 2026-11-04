/*
 * @test
 * @summary Verify CertificateX509Key.get(String) returns the key for the "value" name (case-insensitive) and throws IOException for other names
 * @run main CertificateX509KeyWitupTest
 */

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;

public class CertificateX509KeyWitupTest {

    public static void main(String[] args) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        KeyPair kp = kpg.generateKeyPair();
        PublicKey pub = kp.getPublic();

        CertificateX509Key cert = new CertificateX509Key(pub);

        check(cert.get("value") == pub, "get(value) should return the key");
        check(cert.get("VALUE") == pub, "get(VALUE) should return the key");
        check(cert.get("Value") == pub, "get(Value) should return the key");

        expectIOException(cert, "key");
        expectIOException(cert, "bogus");
        expectIOException(cert, "");

        CertificateX509Key nullCert = new CertificateX509Key((PublicKey) null);
        check(nullCert.get("value") == null, "get(value) should return null when key is null");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void expectIOException(CertificateX509Key cert, String name) {
        try {
            cert.get(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("get(" + name + ") should throw IOException");
    }
}
