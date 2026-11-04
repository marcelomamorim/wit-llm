/*
 * @test
 * @summary Verify that X509CertImpl(InputStream) rejects empty and malformed input with CertificateException
 * @modules java.base/sun.security.x509
 * @run main X509CertImplWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CertificateException;
import sun.security.x509.X509CertImpl;

public class X509CertImplWitupTest {

    public static void main(String[] args) throws Exception {
        expectCertificateException(new ByteArrayInputStream(new byte[0]));
        expectCertificateException(new ByteArrayInputStream(new byte[] {0x01, 0x02, 0x03}));
        expectCertificateException(new ByteArrayInputStream(new byte[] {0x30, 0x03, 0x02}));
    }

    private static void expectCertificateException(InputStream in) {
        try {
            new X509CertImpl(in);
            throw new RuntimeException("CertificateException expected");
        } catch (CertificateException expected) {
        }
    }
}
