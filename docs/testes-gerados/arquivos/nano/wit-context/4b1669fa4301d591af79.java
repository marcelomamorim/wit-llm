/* @test    @summary Test for CertificateVersion constructor with valid and invalid inputs    @run main CertificateVersionWitupTest */

import sun.security.x509.CertificateVersion;
import java.io.IOException;

public class CertificateVersionWitupTest {

    public static void main(String[] args) {
        testValidVersionV1();
        testValidVersionV2();
        testValidVersionV3();
        testInvalidVersion();
    }

    static void testValidVersionV1() {
        try {
            CertificateVersion cv = new CertificateVersion(CertificateVersion.V1);
            if (cv == null || cv.getVersion() != CertificateVersion.V1) {
                throw new AssertionError("V1 version test failed");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected exception for V1", e);
        }
    }

    static void testValidVersionV2() {
        try {
            CertificateVersion cv = new CertificateVersion(CertificateVersion.V2);
            if (cv == null || cv.getVersion() != CertificateVersion.V2) {
                throw new AssertionError("V2 version test failed");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected exception for V2", e);
        }
    }

    static void testValidVersionV3() {
        try {
            CertificateVersion cv = new CertificateVersion(CertificateVersion.V3);
            if (cv == null || cv.getVersion() != CertificateVersion.V3) {
                throw new AssertionError("V3 version test failed");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected exception for V3", e);
        }
    }

    static void testInvalidVersion() {
        int invalidVersion = 99;
        try {
            new CertificateVersion(invalidVersion);
            throw new AssertionError("Expected IOException for invalid version");
        } catch (IOException e) {
            if (!e.getMessage().contains("not supported")) {
                throw new AssertionError("Unexpected exception message", e);
            }
        }
    }
}
