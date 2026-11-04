/*
 * @test
 * @summary derEncode must reject a certificate without encoded data before touching the stream
 * @modules java.base/sun.security.x509
 * @run main X509CertImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.X509CertImpl;

public class X509CertImplWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCertificateRejected();
        testNullCertificateRejectedWithNullStream();
    }

    static void testNullCertificateRejected() throws Exception {
        X509CertImpl cert = new X509CertImpl();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            cert.derEncode(out);
            throw new RuntimeException("Expected IOException for null certificate");
        } catch (IOException e) {
            if (!"Null certificate to encode".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
        if (out.size() != 0) {
            throw new RuntimeException("No bytes must be written on failure");
        }
    }

    static void testNullCertificateRejectedWithNullStream() throws Exception {
        X509CertImpl cert = new X509CertImpl();
        try {
            cert.derEncode(null);
            throw new RuntimeException("Expected IOException for null certificate");
        } catch (IOException e) {
            if (!"Null certificate to encode".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
    }
}
