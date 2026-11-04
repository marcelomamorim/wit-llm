/*
 * @test
 * @summary Test IssuerAlternativeNameExtension.set(String,Object) method behavior
 * @run main IssuerAlternativeNameExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.IssuerAlternativeNameExtension;
import sun.security.x509.GeneralNames;

public class IssuerAlternativeNameExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetWithValidNameAndGeneralNames();
        testSetWithValidNameAndInvalidObject();
        testSetWithInvalidName();
    }

    private static void testSetWithValidNameAndGeneralNames() throws Exception {
        GeneralNames gn = new GeneralNames();
        IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension();
        ext.set(IssuerAlternativeNameExtension.ISSUER_NAME, gn);
        // If no exception, test passes for valid input
    }

    private static void testSetWithValidNameAndInvalidObject() {
        IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension();
        try {
            ext.set(IssuerAlternativeNameExtension.ISSUER_NAME, "not a GeneralNames instance");
            throw new RuntimeException("Expected IOException not thrown for invalid object type");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("GeneralNames")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }

    private static void testSetWithInvalidName() {
        IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension();
        try {
            ext.set("invalid_name", new GeneralNames());
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
