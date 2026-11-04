/*
 * @test
 * @summary Checks CertificateAlgorithmId decoding of a valid DER AlgorithmId and rejection of an empty stream
 * @modules java.base/sun.security.x509
 * @run main CertificateAlgorithmIdWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import sun.security.x509.CertificateAlgorithmId;

public class CertificateAlgorithmIdWitupTest {
    public static void main(String[] args) throws Exception {
        byte[] sha256 = {0x30, 0x0D, 0x06, 0x09, 0x60, (byte) 0x86, 0x48, 0x01, 0x65, 0x03, 0x04, 0x02, 0x01};
        CertificateAlgorithmId id = new CertificateAlgorithmId(new ByteArrayInputStream(sha256));
        String s = id.toString();
        if (s == null || !s.contains("OID = 2.16.840.1.101.3.4.2.1")) {
            throw new RuntimeException("unexpected toString: " + s);
        }

        boolean threw = false;
        try {
            new CertificateAlgorithmId(new ByteArrayInputStream(new byte[0]));
        } catch (IOException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("expected IOException for empty stream");
        }
    }
}
