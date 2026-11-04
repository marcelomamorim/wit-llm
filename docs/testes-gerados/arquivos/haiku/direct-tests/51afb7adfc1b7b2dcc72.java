/*
 * @test
 * @summary Verify CertificateX509Key(DerInputStream) decodes a DER-encoded public key and rejects invalid DER input
 * @modules java.base/sun.security.util java.base/sun.security.x509
 * @run main CertificateX509KeyWitupTest
 */

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.util.Arrays;

import sun.security.util.DerInputStream;
import sun.security.x509.CertificateX509Key;

public class CertificateX509KeyWitupTest {
    public static void main(String[] args) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        PublicKey pub = kp.getPublicKey();
        byte[] der = pub.getEncoded();

        CertificateX509Key cert = new CertificateX509Key(new DerInputStream(der));
        PublicKey decoded = cert.get(CertificateX509Key.KEY);

        if (decoded == null) {
            throw new RuntimeException("decoded key is null");
        }
        if (!Arrays.equals(der, decoded.getEncoded())) {
            throw new RuntimeException("encoded form mismatch");
        }
        if (!"RSA".equals(decoded.getAlgorithm())) {
            throw new RuntimeException("unexpected algorithm: " + decoded.getAlgorithm());
        }

        try {
            new CertificateX509Key(new DerInputStream(new byte[] {0x01, 0x02}));
            throw new RuntimeException("expected IOException for invalid DER");
        } catch (IOException expected) {
            // expected
        }
    }
}
