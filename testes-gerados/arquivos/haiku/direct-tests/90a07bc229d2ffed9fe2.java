/*
 * @test
 * @summary X509CertImpl.getEncodedInternal throws CertificateEncodingException when no certificate data is set
 * @modules java.base/sun.security.x509
 * @run main X509CertImplWitupTest
 */

import java.security.cert.CertificateEncodingException;
import sun.security.x509.X509CertImpl;

public class X509CertImplWitupTest {

    public static void main(String[] args) throws Exception {
        X509CertImpl cert = new X509CertImpl();

        try {
            cert.getEncodedInternal();
            throw new RuntimeException("Expected CertificateEncodingException from getEncodedInternal");
        } catch (CertificateEncodingException e) {
        }

        try {
            cert.getEncoded();
            throw new RuntimeException("Expected CertificateEncodingException from getEncoded");
        } catch (CertificateEncodingException e) {
        }
    }
}
