/*
 * @test
 * @summary Verifies that X509CertImpl(InputStream) rejects inputs that are neither RFC1421 hex nor raw DER with a CertificateException
 * @modules java.base/sun.security.x509
 * @run main X509CertImplWitupTest
 */

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import sun.security.x509.X509CertImpl;

public class X509CertImplWitupTest {

    public static void main(String[] args) throws Exception {
        expectCertificateException(new byte[0]);
        expectCertificateException(new byte[] {0x01, 0x02, 0x03});
        System.out.println("PASSED");
    }

    static void expectCertificateException(byte[] data) {
        try {
            new X509CertImpl(new ByteArrayInputStream(data));
        } catch (CertificateException e) {
            return;
        }
        throw new RuntimeException("Expected CertificateException for input length " + data.length);
    }
}
