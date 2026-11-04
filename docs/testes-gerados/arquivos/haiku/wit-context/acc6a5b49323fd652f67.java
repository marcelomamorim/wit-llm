/*
 * @test
 * @summary Verifies that X509CertImpl.getEncoded() throws CertificateEncodingException when no certificate data is present
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
                cert.getEncoded();
                throw new RuntimeException("expected CertificateEncodingException");
            } catch (CertificateEncodingException expected) {
            }
        }
    }
}
