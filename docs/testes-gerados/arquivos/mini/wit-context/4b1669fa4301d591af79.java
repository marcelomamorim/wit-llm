/*
 * @test
 * @summary Test CertificateVersion(int) constructor for valid and invalid versions
 * @run main CertificateVersionWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest {
    public static void main(String[] args) throws Exception {
        testValidVersions();
        testInvalidVersions();
        System.out.println("Test passed.");
    }

    private static void testValidVersions() throws IOException {
        // Valid versions: 0 (V1), 1 (V2), 2 (V3)
        CertificateVersion cv1 = new CertificateVersion(0);
        CertificateVersion cv2 = new CertificateVersion(1);
        CertificateVersion cv3 = new CertificateVersion(2);
        // No exception means pass
        if (cv1 == null || cv2 == null || cv3 == null) {
            throw new AssertionError("Constructor returned null for valid versions");
        }
    }

    private static void testInvalidVersions() {
        int[] invalidVersions = { -1, 3, 10, Integer.MIN_VALUE, Integer.MAX_VALUE };
        for (int v : invalidVersions) {
            try {
                new CertificateVersion(v);
                throw new AssertionError("Expected IOException for version: " + v);
            } catch (IOException e) {
                String msg = e.getMessage();
                if (msg == null || !msg.contains("not supported")) {
                    throw new AssertionError("Unexpected exception message: " + msg);
                }
            }
        }
    }
}
