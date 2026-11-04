/*
 * @test
 * @summary Verify X509CertImpl.derEncode rejects a certificate with no signed data and leaves the output stream untouched
 * @modules java.base/sun.security.x509
 * @run main X509CertImplWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.X509CertImpl;

public class X509CertImplWitupTest {

    public static void main(String[] args) throws Exception {
        X509CertImpl cert = new X509CertImpl();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            cert.derEncode(bos);
            throw new RuntimeException("Expected IOException for null signedCert");
        } catch (IOException expected) {
            if (!"Null certificate to encode".equals(expected.getMessage())) {
                throw new RuntimeException("Unexpected message: " + expected.getMessage());
            }
        }
        if (bos.size() != 0) {
            throw new RuntimeException("Output stream must not be written on failure");
        }

        try {
            cert.derEncode(null);
            throw new RuntimeException("Expected IOException for null signedCert with null stream");
        } catch (IOException expected) {
            if (!"Null certificate to encode".equals(expected.getMessage())) {
                throw new RuntimeException("Unexpected message: " + expected.getMessage());
            }
        }
    }
}
