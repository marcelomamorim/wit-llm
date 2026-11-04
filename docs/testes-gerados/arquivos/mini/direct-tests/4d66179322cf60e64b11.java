/*
 * @test
 * @summary Test IssuerAlternativeNameExtension.set method behavior
 * @run main IssuerAlternativeNameExtensionWitupTest
 */

import sun.security.x509.IssuerAlternativeNameExtension;
import sun.security.x509.GeneralNames;
import java.io.IOException;

public class IssuerAlternativeNameExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetWithValidNameAndGeneralNames();
        testSetWithInvalidName();
        testSetWithInvalidObjectType();
    }

    private static void testSetWithValidNameAndGeneralNames() throws Exception {
        IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension();
        GeneralNames gn = new GeneralNames();
        ext.set(IssuerAlternativeNameExtension.ISSUER_NAME, gn);
        // If no exception, test passes
    }

    private static void testSetWithInvalidName() {
        IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension();
        try {
            ext.set("invalid_name", new GeneralNames());
            throw new RuntimeException("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testSetWithInvalidObjectType() {
        IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension();
        try {
            ext.set(IssuerAlternativeNameExtension.ISSUER_NAME, "not a GeneralNames instance");
            throw new RuntimeException("Expected IOException for invalid attribute value type");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute value should be of type GeneralNames")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
