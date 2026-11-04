/* @test    @summary Test delete method of CertificateValidity class    @run main CertificateValidityWitupTest */

import java.io.IOException;
import sun.security.x509.CertificateValidity;

public class CertificateValidityWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteNotBefore();
        testDeleteNotAfter();
        testDeleteInvalidName();
    }

    private static void testDeleteNotBefore() throws IOException {
        CertificateValidity cv = new CertificateValidity();
        cv.delete("notBefore");
        if (cv.getNotBefore() != null) {
            throw new AssertionError("Expected notBefore to be null after delete.");
        }
    }

    private static void testDeleteNotAfter() throws IOException {
        CertificateValidity cv = new CertificateValidity();
        cv.delete("notAfter");
        if (cv.getNotAfter() != null) {
            throw new AssertionError("Expected notAfter to be null after delete.");
        }
    }

    private static void testDeleteInvalidName() {
        CertificateValidity cv = new CertificateValidity();
        try {
            cv.delete("invalidName");
            throw new AssertionError("Expected IOException for invalid attribute name.");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
