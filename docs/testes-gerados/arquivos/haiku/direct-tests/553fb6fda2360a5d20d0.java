/*
 * @test
 * @summary Verifies that getTBSCertificate throws CertificateEncodingException on an uninitialized X509CertImpl
 * @modules java.base/sun.security.x509
 * @run main X509CertImplWitupTest
 */

import java.security.cert.CertificateEncodingException;
import sun.security.x509.X509CertImpl;

public class X509CertImplWitupTest {

    public static void main(String[] args) throws Exception {
        checkUninitializedThrows();
        checkRepeatedCallsConsistent();
    }

    static void checkUninitializedThrows() {
        X509CertImpl cert = new X509CertImpl();
        try {
            cert.getTBSCertificate();
            throw new RuntimeException("expected CertificateEncodingException");
        } catch (CertificateEncodingException e) {
            if (!"Uninitialized certificate".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
    }

    static void checkRepeatedCallsConsistent() {
        X509CertImpl cert = new X509CertImpl();
        for (int i = 0; i < 2; i++) {
            try {
                cert.getTBSCertificate();
                throw new RuntimeException("expected CertificateEncodingException on call " + i);
            } catch (CertificateEncodingException e) {
                if (!"Uninitialized certificate".equals(e.getMessage())) {
                    throw new RuntimeException("unexpected message on call " + i + ": " + e.getMessage());
                }
            }
        }
    }
}
