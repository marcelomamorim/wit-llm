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
        // After delete("issuer"), names should be null and extensionValue null
        if (ext.names != null) {
            throw new RuntimeException("Failed: names should be null after delete(\"issuer\")");
        }
        if (ext.extensionValue != null) {
            throw new RuntimeException("Failed: extensionValue should be null after delete(\"issuer\")");
        }
    }

    private static void testDeleteWithInvalidName() throws Exception {
        GeneralNames gn = new GeneralNames();
        CertificateIssuerExtension ext = new CertificateIssuerExtension(gn);
        try {
            ext.delete("invalidName");
            throw new RuntimeException("Failed: IOException expected for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Failed: IOException message unexpected: " + msg);
            }
        }
    }
}
