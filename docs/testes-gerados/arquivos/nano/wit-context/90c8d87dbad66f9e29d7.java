/* @test    @summary Test for CertificateIssuerExtension.get(String) method
 * @run main CertificateIssuerExtensionWitupTest
 */

import sun.security.x509.CertificateIssuerExtension;
import sun.security.util.GeneralNames;
import java.io.IOException;

public class CertificateIssuerExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithIssuerName();
        testGetWithUnknownName();
    }

    private static void testGetWithIssuerName() throws IOException {
        GeneralNames gn = new GeneralNames();
        CertificateIssuerExtension ext = new CertificateIssuerExtension(gn);
        GeneralNames result = ext.get("issuer");
        if (result != gn) {
            throw new AssertionError("Expected to retrieve the same GeneralNames instance for 'issuer'");
        }
    }

    private static void testGetWithUnknownName() {
        try {
            GeneralNames gn = new GeneralNames();
            CertificateIssuerExtension ext = new CertificateIssuerExtension(gn);
            ext.get("unknown");
            throw new AssertionError("Expected IOException for unknown attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + msg, e);
            }
        }
    }
}
