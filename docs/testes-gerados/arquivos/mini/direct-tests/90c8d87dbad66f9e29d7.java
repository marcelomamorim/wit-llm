/*
 * @test
 * @summary Test CertificateIssuerExtension.get(String) method behavior
 * @run main CertificateIssuerExtensionWitupTest
 */

import sun.security.x509.CertificateIssuerExtension;
import sun.security.x509.GeneralNames;
import java.io.IOException;

public class CertificateIssuerExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws Exception {
        GeneralNames gn = new GeneralNames();
        CertificateIssuerExtension ext = new CertificateIssuerExtension(gn);
        GeneralNames result = ext.get("issuer");
        if (result != gn) {
            throw new RuntimeException("get(\"issuer\") did not return the expected GeneralNames instance");
        }
        // Also test case-insensitivity
        result = ext.get("ISSUER");
        if (result != gn) {
            throw new RuntimeException("get(\"ISSUER\") did not return the expected GeneralNames instance");
        }
    }

    private static void testGetWithInvalidName() {
        try {
            GeneralNames gn = new GeneralNames();
            CertificateIssuerExtension ext = new CertificateIssuerExtension(gn);
            ext.get("invalidName");
            throw new RuntimeException("Expected IOException was not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
