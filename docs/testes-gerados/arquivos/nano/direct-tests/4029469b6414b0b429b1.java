/* @test    @summary Test for CertificateIssuerExtension.set method behavior    @run main CertificateIssuerExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.CertificateIssuerExtension;
import sun.security.util.GeneralNames;

public class CertificateIssuerExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetWithValidGeneralNames();
        testSetWithInvalidObject();
        testSetWithUnrecognizedName();
    }

    private static void testSetWithValidGeneralNames() throws Exception {
        GeneralNames validNames = new GeneralNames();
        CertificateIssuerExtension ext = new CertificateIssuerExtension(validNames);
        ext.set("issuer", validNames);
        System.out.println("testSetWithValidGeneralNames passed");
    }

    private static void testSetWithInvalidObject() {
        try {
            GeneralNames validNames = new GeneralNames();
            CertificateIssuerExtension ext = new CertificateIssuerExtension(validNames);
            ext.set("issuer", new Object());
            throw new AssertionError("Expected IOException not thrown");
        } catch (IOException e) {
            System.out.println("testSetWithInvalidObject passed: " + e.getMessage());
        }
    }

    private static void testSetWithUnrecognizedName() {
        try {
            GeneralNames validNames = new GeneralNames();
            CertificateIssuerExtension ext = new CertificateIssuerExtension(validNames);
            ext.set("unknown", validNames);
            throw new AssertionError("Expected IOException not thrown");
        } catch (IOException e) {
            System.out.println("testSetWithUnrecognizedName passed: " + e.getMessage());
        }
    }
}
