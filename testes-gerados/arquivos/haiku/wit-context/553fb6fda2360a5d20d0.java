/*
 * @test
 * @summary X509CertImpl.getTBSCertificate() throws CertificateEncodingException when no certificate info is set
 * @modules java.base/sun.security.x509
 * @run main X509CertImplWitupTest
 */

import java.security.cert.CertificateEncodingException;
import sun.security.x509.X509CertImpl;

public class X509CertImplWitupTest {

    public static void main(String[] args) throws Exception {
        X509CertImpl cert = new X509CertImpl();
        for (int i = 0; i < 2; i++) {
            try {
                cert.getTBSCertificate();
                throw new RuntimeException("Expected CertificateEncodingException on call " + i);
            } catch (CertificateEncodingException e) {
                if (!"Uninitialized certificate".equals(e.getMessage())) {
                    throw new RuntimeException("Unexpected message: " + e.getMessage(), e);
                }
            }
        }
    }
}
