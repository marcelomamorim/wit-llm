/*
 * @test
 * @summary Test X509CertImpl constructor with InputStream for valid and invalid inputs
 * @run main X509CertImplWitupTest
 */

import sun.security.x509.X509CertImpl;
import sun.security.util.DerValue;
import java.io.*;
import java.security.cert.CertificateException;

public class X509CertImplWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerEncodedCertificate();
        testInvalidInputStreamThrowsCertificateException();
    }

    private static void testValidDerEncodedCertificate() throws Exception {
        // Create a minimal valid DER encoding for a certificate
        // Using DerValue to create a dummy DER sequence
        byte[] derEncoded = new byte[] {
            0x30, 0x03, 0x02, 0x01, 0x01 // SEQUENCE { INTEGER 1 }
        };
        ByteArrayInputStream bais = new ByteArrayInputStream(derEncoded);
        X509CertImpl cert = new X509CertImpl(bais);
        if (cert == null) {
            throw new RuntimeException("X509CertImpl constructor returned null for valid DER input");
        }
    }

    private static void testInvalidInputStreamThrowsCertificateException() throws Exception {
        // Provide invalid input that is neither RFC1421 hex nor DER
        byte[] invalidData = new byte[] {0x00, 0x01, 0x02};
        ByteArrayInputStream bais = new ByteArrayInputStream(invalidData);
        try {
            new X509CertImpl(bais);
            throw new RuntimeException("Expected CertificateException not thrown for invalid input");
        } catch (CertificateException e) {
            // Expected exception
            String msg = e.getMessage();
            if (!msg.contains("Input stream must be") && !msg.contains("Unable to parse DER value")) {
                throw new RuntimeException("Unexpected CertificateException message: " + msg);
            }
        }
    }
}
