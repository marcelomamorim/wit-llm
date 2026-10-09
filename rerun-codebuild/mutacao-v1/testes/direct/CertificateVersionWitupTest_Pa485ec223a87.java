/* @test    @summary Test the get(String name) method of CertificateVersion for valid and invalid attribute names    @run main CertificateVersionWitupTest_Pa485ec223a87 */

import sun.security.x509.CertificateVersion;
import java.io.IOException;

public class CertificateVersionWitupTest_Pa485ec223a87 {
    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        CertificateVersion cv = new CertificateVersion();
        Integer version = cv.get("version");
        if (version == null) {
            throw new AssertionError("Expected non-null version for valid name");
        }
        if (version.intValue() != CertificateVersion.V1) {
            throw new AssertionError("Expected version to be V1 (0), but got " + version);
        }
    }

    private static void testGetWithInvalidName() {
        CertificateVersion cv = new CertificateVersion();
        try {
            cv.get("invalidName");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
