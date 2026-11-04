/* @test    @summary Test CertificateVersion constructor with valid and invalid inputs    @run main CertificateVersionWitupTest */

import java.io.IOException;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest {

    public static void main(String[] args) {
        testValidVersions();
        testInvalidVersion();
    }

    static void testValidVersions() {
        try {
            CertificateVersion v1 = new CertificateVersion(0);
            assert v1 != null : "Failed to create version 0";
            CertificateVersion v2 = new CertificateVersion(1);
            assert v2 != null : "Failed to create version 1";
            CertificateVersion v3 = new CertificateVersion(2);
            assert v3 != null : "Failed to create version 2";
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for valid versions", e);
        }
    }

    static void testInvalidVersion() {
        boolean thrown = false;
        try {
            new CertificateVersion(3);
        } catch (IOException e) {
            thrown = true;
            assert e.getMessage().contains("not supported") : "Unexpected message: " + e.getMessage();
        }
        assert thrown : "Expected IOException for invalid version";
    }
}
