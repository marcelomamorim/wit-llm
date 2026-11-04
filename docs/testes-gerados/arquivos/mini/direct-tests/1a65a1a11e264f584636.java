/*
 * @test
 * @summary Test sun.security.x509.IssuerAlternativeNameExtension.get(String) method
 * @run main IssuerAlternativeNameExtensionWitupTest
 */

import sun.security.x509.IssuerAlternativeNameExtension;
import sun.security.x509.GeneralNames;
import sun.security.x509.PKIXExtensions;
import java.io.IOException;

public class IssuerAlternativeNameExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws Exception {
        // Create an empty GeneralNames instance
        GeneralNames emptyNames = new GeneralNames();
        IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension(emptyNames);

        GeneralNames result = ext.get("issuer_name");
        if (result == null) {
            throw new RuntimeException("get() returned null for valid name");
        }
        // The returned GeneralNames should be the same instance passed in constructor
        if (result != emptyNames) {
            throw new RuntimeException("get() did not return the expected GeneralNames instance");
        }
    }

    private static void testGetWithInvalidName() {
        try {
            GeneralNames emptyNames = new GeneralNames();
            IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension(emptyNames);
            ext.get("invalid_name");
            throw new RuntimeException("get() did not throw IOException for invalid name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type: " + e);
        }
    }
}
