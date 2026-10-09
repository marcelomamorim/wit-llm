/* @test    @summary Test for sun.security.x509.CertificateVersion.get(String) method.
 * @run main CertificateVersionWitupTest_Pa9dc6a948e47
 */

import java.io.IOException;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest_Pa9dc6a948e47 {

    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        CertificateVersion cv = new CertificateVersion();
        Integer version = cv.get("x509.info.version");
        if (version == null) {
            throw new AssertionError("Expected non-null version for valid name");
        }
        if (version != 0 && version != 1 && version != 2) {
            throw new AssertionError("Unexpected version value: " + version);
        }
    }

    private static void testGetWithInvalidName() {
        try {
            CertificateVersion cv = new CertificateVersion();
            cv.get("invalid.name");
            throw new AssertionError("Expected IOException for invalid name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + msg, e);
            }
        }
    }
}
