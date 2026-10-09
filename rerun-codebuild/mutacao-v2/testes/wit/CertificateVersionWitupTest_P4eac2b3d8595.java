/*
 * @test
 * @summary Test sun.security.x509.CertificateVersion.get(String) method
 * @run main CertificateVersionWitupTest_P4eac2b3d8595
 */

import java.io.IOException;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest_P4eac2b3d8595 {
    public static void main(String[] args) throws Exception {
        CertificateVersion certVer = new CertificateVersion();

        // Test valid attribute name "number" (case insensitive)
        Integer version = certVer.get("number");
        if (version == null || version.intValue() != 0) {
            throw new RuntimeException("Expected version 0 for 'number', got: " + version);
        }

        version = certVer.get("NUMBER");
        if (version == null || version.intValue() != 0) {
            throw new RuntimeException("Expected version 0 for 'NUMBER', got: " + version);
        }

        // Test invalid attribute name triggers IOException
        boolean threw = false;
        try {
            certVer.get("invalidName");
        } catch (IOException e) {
            threw = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException for invalid attribute name");
        }
    }
}
