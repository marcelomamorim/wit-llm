/*
 * @test
 * @summary White-box-free behavioral checks for CertificateVersion.get(String) name handling and version constructor validation
 * @modules java.base/sun.security.x509
 * @run main CertificateVersionWitupTest
 */
import java.io.IOException;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest {

    public static void main(String[] args) throws Exception {
        testDefaultVersionIsV1();
        testGetNumberOnV3();
        testGetIsCaseInsensitive();
        testGetUnknownNameThrows();
        testGetVersionNameThrows();
        testInvalidVersionConstructorThrows();
    }

    static void testDefaultVersionIsV1() throws IOException {
        CertificateVersion cv = new CertificateVersion();
        check(cv.get(CertificateVersion.VERSION).intValue() == CertificateVersion.V1,
              "default version should be V1 (0)");
    }

    static void testGetNumberOnV3() throws IOException {
        CertificateVersion cv = new CertificateVersion(CertificateVersion.V3);
        check(cv.get("number").intValue() == 2, "get(number) on V3 should return 2");
    }

    static void testGetIsCaseInsensitive() throws IOException {
        CertificateVersion cv = new CertificateVersion(CertificateVersion.V2);
        check(cv.get("NuMbEr").intValue() == 1, "get should accept case-insensitive name");
    }

    static void testGetUnknownNameThrows() throws IOException {
        CertificateVersion cv = new CertificateVersion();
        try {
            cv.get("unknown");
            throw new RuntimeException("expected IOException for unknown attribute name");
        } catch (IOException expected) {
        }
    }

    static void testGetVersionNameThrows() throws IOException {
        CertificateVersion cv = new CertificateVersion();
        try {
            cv.get("version");
            throw new RuntimeException("expected IOException for name 'version'");
        } catch (IOException expected) {
        }
    }

    static void testInvalidVersionConstructorThrows() {
        try {
            new CertificateVersion(3);
            throw new RuntimeException("expected IOException for unsupported version 3");
        } catch (IOException expected) {
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
