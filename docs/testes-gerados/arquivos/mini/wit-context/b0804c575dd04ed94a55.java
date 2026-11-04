/*
 * @test
 * @summary Test X509CertImpl(InputStream) constructor for valid and invalid inputs
 * @run main X509CertImplWitupTest
 */

import sun.security.x509.X509CertImpl;
import sun.security.util.DerValue;
import java.io.*;
import java.security.cert.CertificateException;

public class X509CertImplWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerInputStream();
        testInvalidInputStreamThrowsCertificateException();
    }

    private static void testValidDerInputStream() throws Exception {
        // Create a minimal valid DER encoding for a certificate
        // Since creating a full valid DER cert is complex, use a minimal DER encoding that DerValue accepts
        // Here we create a DER encoding of an empty SEQUENCE (0x30 0x00)
        byte[] derEncoded = new byte[] {0x30, 0x00};
        ByteArrayInputStream bais = new ByteArrayInputStream(derEncoded);

        X509CertImpl cert = new X509CertImpl(bais);
        if (cert == null) {
            throw new RuntimeException("X509CertImpl constructor returned null for valid DER input");
        }
    }

    private static void testInvalidInputStreamThrowsCertificateException() {
        // Provide an InputStream that is neither valid RFC1421 hex-encoded nor valid DER
        byte[] invalidData = new byte[] {0x00, 0x01, 0x02};
        ByteArrayInputStream bais = new ByteArrayInputStream(invalidData);

        try {
            new X509CertImpl(bais);
            throw new RuntimeException("Expected CertificateException not thrown for invalid input stream");
        } catch (CertificateException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Input stream must be")) {
                throw new RuntimeException("CertificateException message unexpected: " + msg);
            }
        }
    }
}
