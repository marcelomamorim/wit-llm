/*
 * @test
 * @summary Test sun.security.x509.CertificateVersion.get(String) method
 * @run main CertificateVersionWitupTest
 */

import sun.security.x509.CertificateVersion;
import java.io.IOException;

public class CertificateVersionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetVersionReturnsCorrectValue();
        testGetWithInvalidNameThrowsIOException();
    }

    private static void testGetVersionReturnsCorrectValue() throws IOException {
        CertificateVersion cv1 = new CertificateVersion(CertificateVersion.V1);
        Integer v1 = cv1.get("number");
        if (v1 == null || v1.intValue() != CertificateVersion.V1) {
            throw new RuntimeException("Expected version " + CertificateVersion.V1 + " but got " + v1);
        }

        CertificateVersion cv2 = new CertificateVersion(CertificateVersion.V2);
        Integer v2 = cv2.get("number");
        if (v2 == null || v2.intValue() != CertificateVersion.V2) {
            throw new RuntimeException("Expected version " + CertificateVersion.V2 + " but got " + v2);
        }

        CertificateVersion cv3 = new CertificateVersion(CertificateVersion.V3);
        Integer v3 = cv3.get("number");
        if (v3 == null || v3.intValue() != CertificateVersion.V3) {
            throw new RuntimeException("Expected version " + CertificateVersion.V3 + " but got " + v3);
        }

        // Also test case-insensitivity
        Integer v1Upper = cv1.get("NUMBER");
        if (v1Upper == null || v1Upper.intValue() != CertificateVersion.V1) {
            throw new RuntimeException("Expected version " + CertificateVersion.V1 + " but got " + v1Upper);
        }
    }

    private static void testGetWithInvalidNameThrowsIOException() {
        CertificateVersion cv = new CertificateVersion();
        try {
            cv.get("invalidName");
            throw new RuntimeException("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
