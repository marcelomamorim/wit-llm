/*
 * @test
 * @summary Test CertificateIssuerExtension.set(String,Object) method behavior
 * @run main CertificateIssuerExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateIssuerExtension;
import sun.security.x509.GeneralNames;

public class CertificateIssuerExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetWithValidNameAndGeneralNames();
        testSetWithInvalidNameThrowsIOException();
        testSetWithInvalidObjectTypeThrowsIOException();
    }

    private static void testSetWithValidNameAndGeneralNames() throws Exception {
        GeneralNames gn = new GeneralNames();
        CertificateIssuerExtension ext = new CertificateIssuerExtension(gn);
        GeneralNames newGn = new GeneralNames();
        ext.set("issuer", newGn);
        // No exception means pass
    }

    private static void testSetWithInvalidNameThrowsIOException() {
        try {
            GeneralNames gn = new GeneralNames();
            CertificateIssuerExtension ext = new CertificateIssuerExtension(gn);
            ext.set("invalidName", gn);
            throw new RuntimeException("Expected IOException not thrown for invalid name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }

    private static void testSetWithInvalidObjectTypeThrowsIOException() {
        try {
            GeneralNames gn = new GeneralNames();
            CertificateIssuerExtension ext = new CertificateIssuerExtension(gn);
            ext.set("issuer", "not a GeneralNames instance");
            throw new RuntimeException("Expected IOException not thrown for invalid object type");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute value must be of type")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }
}
