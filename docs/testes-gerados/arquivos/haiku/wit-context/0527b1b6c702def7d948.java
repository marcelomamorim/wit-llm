/*
 * @test
 * @summary Witup-generated checks for CertificateX509Key.get(String)
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
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        PublicKey pk = kp.getPublic();

        CertificateX509Key cert = new CertificateX509Key(pk);

        check(cert.get("value") == pk, "get(\"value\") must return the stored key");
        check(cert.get("VALUE") == pk, "get is expected to be case-insensitive");
        check(cert.get("Value") == pk, "get is expected to be case-insensitive");

        expectIOException(cert, "key");
        expectIOException(cert, "name");
        expectIOException(cert, "");
        expectIOException(cert, "unknown");

        CertificateX509Key nullKeyCert = new CertificateX509Key((PublicKey) null);
        check(nullKeyCert.get("value") == null, "get(\"value\") must return null when key is null");
    }

    private static void expectIOException(CertificateX509Key cert, String name) {
        try {
            cert.get(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for attribute name: " + name);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
