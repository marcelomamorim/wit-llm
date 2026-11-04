/*
 * @test
 * @summary Test sun.security.x509.CertificateVersion.delete(String) method
 * @run main CertificateVersionWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws IOException {
        CertificateVersion cv = new CertificateVersion();
        cv.delete("number");
        // After delete with "number", version should be reset to V1 (0)
        // We verify by deleting again with "number" (should not throw)
        cv.delete("number");
    }

    private static void testDeleteWithInvalidName() {
        CertificateVersion cv = new CertificateVersion();
        try {
            cv.delete("invalidName");
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
