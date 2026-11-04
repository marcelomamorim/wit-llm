/* @test    @summary Test for CertificateIssuerExtension.set(String, Object) handling valid and invalid inputs    @run main CertificateIssuerExtensionWitupTest */

import sun.security.x509.CertificateIssuerExtension;
import sun.security.util.GeneralNames;
import java.io.IOException;

public class CertificateIssuerExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetWithValidGeneralNames();
        testSetWithInvalidObject();
    }

    private static void testSetWithValidGeneralNames() throws Exception {
        GeneralNames validNames = new GeneralNames();
        CertificateIssuerExtension ext = new CertificateIssuerExtension(validNames);
        // Test setting with correct attribute name and valid object
        ext.set("issuer", validNames);
        System.out.println("testSetWithValidGeneralNames passed");
    }

    private static void testSetWithInvalidObject() {
        try {
            GeneralNames validNames = new GeneralNames();
            CertificateIssuerExtension ext = new CertificateIssuerExtension(validNames);
            // Test setting with correct attribute name but invalid object type
            ext.set("issuer", new Object());
            System.err.println("testSetWithInvalidObject failed: exception not thrown");
        } catch (IOException e) {
            // Expected exception
            System.out.println("testSetWithInvalidObject passed");
        } catch (Exception e) {
            System.err.println("testSetWithInvalidObject failed: " + e);
        }
    }
}
