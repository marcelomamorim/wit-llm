/*
 * @test
 * @summary Test sun.security.x509.X509CertImpl.getTBSCertificate() behavior including exception path
 * @run main X509CertImplWitupTest
 */

import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;
import java.security.cert.CertificateEncodingException;

public class X509CertImplWitupTest {
    public static void main(String[] args) throws Exception {
        testGetTBSCertificateReturnsEncodedInfo();
        testGetTBSCertificateThrowsWhenInfoNull();
    }

    private static void testGetTBSCertificateReturnsEncodedInfo() throws Exception {
        // Create X509CertInfo with minimal valid state
        X509CertInfo info = new X509CertInfo();
        // Construct X509CertImpl with info
        X509CertImpl cert = new X509CertImpl(info);

        // The encoded info should not be null and should be a byte array
        byte[] encoded = cert.getTBSCertificate();
        if (encoded == null) {
            throw new RuntimeException("getTBSCertificate() returned null encoded info");
        }
        if (encoded.length == 0) {
            throw new RuntimeException("getTBSCertificate() returned empty encoded info");
        }
    }

    private static void testGetTBSCertificateThrowsWhenInfoNull() {
        // Create X509CertImpl with default constructor, info is null
        X509CertImpl cert = new X509CertImpl();

        try {
            cert.getTBSCertificate();
            throw new RuntimeException("Expected CertificateEncodingException not thrown when info is null");
        } catch (CertificateEncodingException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Uninitialized certificate")) {
                throw new RuntimeException("CertificateEncodingException message unexpected: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type thrown: " + e);
        }
    }
}
