/*
 * @test
 * @summary Test sun.security.x509.CertificateIssuerExtension.set method
 * @run main CertificateIssuerExtensionWitupTest
 */

import sun.security.x509.CertificateIssuerExtension;
import sun.security.x509.GeneralNames;
import java.io.IOException;

public class CertificateIssuerExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetWithValidGeneralNames();
        testSetWithInvalidType();
        testSetWithInvalidName();
    }

    private static void testSetWithValidGeneralNames() throws Exception {
        GeneralNames gn = new GeneralNames();
        CertificateIssuerExtension ext = new CertificateIssuerExtension(gn);
        GeneralNames newGn = new GeneralNames();
        ext.set("issuer", newGn);
        // No exception means pass
    }

    private static void testSetWithInvalidType() {
        try {
            GeneralNames gn = new GeneralNames();
            CertificateIssuerExtension ext = new CertificateIssuerExtension(gn);
            ext.set("issuer", "not a GeneralNames");
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("GeneralNames")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }

    private static void testSetWithInvalidName() {
        try {
            GeneralNames gn = new GeneralNames();
            CertificateIssuerExtension ext = new CertificateIssuerExtension(gn);
            ext.set("invalidName", gn);
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }
}
