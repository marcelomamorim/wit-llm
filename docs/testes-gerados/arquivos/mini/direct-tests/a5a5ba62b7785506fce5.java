/*
 * @test
 * @summary Test sun.security.x509.CertificateVersion.delete(String) method
 * @run main CertificateVersionWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteValidName();
        testDeleteInvalidName();
    }

    private static void testDeleteValidName() throws IOException {
        CertificateVersion cv = new CertificateVersion(2); // V3
        cv.delete("number");
        int versionAfterDelete = getVersion(cv);
        if (versionAfterDelete != CertificateVersion.V1) {
            throw new RuntimeException("delete('number') did not reset version to V1");
        }
    }

    private static void testDeleteInvalidName() {
        CertificateVersion cv = null;
        try {
            cv = new CertificateVersion();
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in constructor", e);
        }
        try {
            cv.delete("invalidName");
            throw new RuntimeException("delete with invalid name did not throw IOException");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    // Helper method to access private field 'version' via public API
    // Since getVersion() is private, we use toString() which returns the version number as string
    private static int getVersion(CertificateVersion cv) {
        String s = cv.toString();
        // toString returns version number as string
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new RuntimeException("toString() did not return a valid integer", e);
        }
    }
}
