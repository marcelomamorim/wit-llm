/*
 * @test
 * @summary Test CertificateVersion(int) constructor for valid and invalid versions
 * @run main CertificateVersionWitupTest
 */

import sun.security.x509.CertificateVersion;
import java.io.IOException;

public class CertificateVersionWitupTest {
    public static void main(String[] args) throws Exception {
        testValidVersions();
        testInvalidVersion();
    }

    private static void testValidVersions() throws IOException {
        CertificateVersion cv1 = new CertificateVersion(CertificateVersion.V1);
        CertificateVersion cv2 = new CertificateVersion(CertificateVersion.V2);
        CertificateVersion cv3 = new CertificateVersion(CertificateVersion.V3);
        if (cv1 == null || cv2 == null || cv3 == null) {
            throw new RuntimeException("Constructor returned null for valid versions");
        }
    }

    private static void testInvalidVersion() {
        try {
            new CertificateVersion(-1);
            throw new RuntimeException("Expected IOException for invalid version -1");
        } catch (IOException e) {
            // expected
        }
        try {
            new CertificateVersion(3);
            throw new RuntimeException("Expected IOException for invalid version 3");
        } catch (IOException e) {
            // expected
        }
        try {
            new CertificateVersion(100);
            throw new RuntimeException("Expected IOException for invalid version 100");
        } catch (IOException e) {
            // expected
        }
    }
}
