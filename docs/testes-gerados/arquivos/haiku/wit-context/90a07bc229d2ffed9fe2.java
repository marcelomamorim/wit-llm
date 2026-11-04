/*
 * @test
 * @summary Verify that getEncodedInternal throws CertificateEncodingException when no certificate data is present
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
            throw new RuntimeException("Expected CertificateEncodingException for null signedCert");
        } catch (CertificateEncodingException e) {
            if (e.getMessage() == null) {
                throw new RuntimeException("Exception message must not be null");
            }
        }
    }
}
