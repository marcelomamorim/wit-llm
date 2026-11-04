/*
 * @test
 * @summary Test sun.security.x509.IssuerAlternativeNameExtension.get(String) method
 * @run main IssuerAlternativeNameExtensionWitupTest
 */

import sun.security.x509.IssuerAlternativeNameExtension;
import sun.security.x509.GeneralNames;
import java.io.IOException;

public class IssuerAlternativeNameExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws Exception {
        // Create a minimal GeneralNames instance for testing
        GeneralNames names = new GeneralNames();
        IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension(names);

        GeneralNames result = ext.get("issuer_name");
        if (result == null) {
            throw new RuntimeException("get() returned null for valid name 'issuer_name'");
        }
    }

    private static void testGetWithInvalidName() {
        try {
            GeneralNames names = new GeneralNames();
            IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension(names);
            ext.get("invalid_name");
            throw new RuntimeException("Expected IOException not thrown for invalid name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type: " + e);
        }
    }
}
