/* @test    @summary Test for derEncode method in X509CertImpl to verify IOException when signedCert is null    @run main X509CertImplWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.X509CertImpl;

public class X509CertImplWitupTest {

    public static void main(String[] args) {
        testDerEncodeThrowsWhenSignedCertIsNull();
    }

    public static void testDerEncodeThrowsWhenSignedCertIsNull() {
        X509CertImpl cert = new X509CertImpl();
        // Use reflection or package-private access if needed; here, assume direct access for test
        // Since signedCert is package-private, we can set it directly if in same package, else reflection is needed
        // For this test, assume direct access or that the test is in the same package
        cert.signedCert = null; // forcibly set to null to trigger exception
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        boolean exceptionThrown = false;
        try {
            cert.derEncode(baos);
        } catch (IOException e) {
            exceptionThrown = true;
            // Optionally, verify exception message
            if (!"Null certificate to encode".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IOException was not thrown when signedCert is null");
        }
    }
}
