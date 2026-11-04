/* @test    @summary Test for IssuerAlternativeNameExtension.set method behavior.    @run main IssuerAlternativeNameExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.IssuerAlternativeNameExtension;
import sun.security.util.GeneralNames;

public class IssuerAlternativeNameExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetWithValidGeneralNames();
        testSetWithInvalidName();
        testSetWithInvalidObjectType();
    }

    static void testSetWithValidGeneralNames() throws IOException {
        GeneralNames gn = new GeneralNames();
        IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension();
        ext.set("issuer_name", gn);
        System.out.println("testSetWithValidGeneralNames passed.");
    }

    static void testSetWithInvalidName() {
        IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension();
        try {
            ext.set("invalid_name", new GeneralNames());
            System.err.println("testSetWithInvalidName failed: exception not thrown.");
        } catch (IOException e) {
            System.out.println("testSetWithInvalidName passed.");
        }
    }

    static void testSetWithInvalidObjectType() {
        IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension();
        try {
            ext.set("issuer_name", new Object());
            System.err.println("testSetWithInvalidObjectType failed: exception not thrown.");
        } catch (IOException e) {
            System.out.println("testSetWithInvalidObjectType passed.");
        }
    }
}
