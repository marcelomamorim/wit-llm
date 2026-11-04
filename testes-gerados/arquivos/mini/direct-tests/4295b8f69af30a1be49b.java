/*
 * @test
 * @summary Test CertificateIssuerExtension.delete(String) method behavior
 * @run main CertificateIssuerExtensionWitupTest
 */

import sun.security.x509.CertificateIssuerExtension;
import sun.security.x509.GeneralNames;
import java.io.IOException;

public class CertificateIssuerExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithIssuerName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithIssuerName() throws Exception {
        GeneralNames gn = new GeneralNames();
        CertificateIssuerExtension ext = new CertificateIssuerExtension(gn);
        ext.delete("issuer");
        // After deletion, names should be null and extensionValue null
        // We check by trying to delete again and expecting IOException
        try {
            ext.delete("issuer");
        } catch (IOException e) {
            // Should not throw here because "issuer" is recognized
            throw new RuntimeException("Unexpected IOException on second delete with 'issuer'", e);
        }
    }

    private static void testDeleteWithInvalidName() throws Exception {
        GeneralNames gn = new GeneralNames();
        CertificateIssuerExtension ext = new CertificateIssuerExtension(gn);
        try {
            ext.delete("invalidName");
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
