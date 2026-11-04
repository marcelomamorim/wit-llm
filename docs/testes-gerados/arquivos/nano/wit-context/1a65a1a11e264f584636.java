/* @test    @summary Test for sun.security.x509.IssuerAlternativeNameExtension.get(String) method
 * @run main IssuerAlternativeNameExtensionWitupTest
 */

import sun.security.x509.IssuerAlternativeNameExtension;
import sun.security.util.GeneralNames;
import java.io.IOException;

public class IssuerAlternativeNameExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithIssuerName();
        testGetWithUnknownName();
    }

    private static void testGetWithIssuerName() throws IOException {
        // Setup: create a GeneralNames object (null for simplicity, as encodeThis handles null)
        GeneralNames names = null;
        IssuerAlternativeNameExtension extension = new IssuerAlternativeNameExtension(names);
        // Call get with the recognized name
        GeneralNames result = extension.get("issuer_name");
        if (result != null) {
            System.out.println("testGetWithIssuerName passed");
        } else {
            System.out.println("testGetWithIssuerName failed: result is null");
        }
    }

    private static void testGetWithUnknownName() {
        try {
            GeneralNames names = null;
            IssuerAlternativeNameExtension extension = new IssuerAlternativeNameExtension(names);
            extension.get("unknown_name");
            System.out.println("testGetWithUnknownName failed: no exception thrown");
        } catch (IOException e) {
            String message = e.getMessage();
            if (message != null && message.contains("Attribute name not recognized")) {
                System.out.println("testGetWithUnknownName passed");
            } else {
                System.out.println("testGetWithUnknownName failed: wrong exception message");
            }
        } catch (Exception e) {
            System.out.println("testGetWithUnknownName failed: unexpected exception " + e);
        }
    }
}
