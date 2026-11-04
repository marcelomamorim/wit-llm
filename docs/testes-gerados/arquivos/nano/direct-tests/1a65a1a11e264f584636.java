/* @test    @summary Test for IssuerAlternativeNameExtension.get(String) method    @run main IssuerAlternativeNameExtensionWitupTest */

import sun.security.x509.IssuerAlternativeNameExtension;
import sun.security.util.GeneralNames;
import java.io.IOException;

public class IssuerAlternativeNameExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        GeneralNames genNames = new GeneralNames();
        IssuerAlternativeNameExtension extension = new IssuerAlternativeNameExtension(genNames);
        GeneralNames result = extension.get("issuer_name");
        if (result != genNames) {
            throw new AssertionError("Expected to retrieve the same GeneralNames instance.");
        }
    }

    private static void testGetWithInvalidName() {
        GeneralNames genNames = new GeneralNames();
        IssuerAlternativeNameExtension extension;
        try {
            extension = new IssuerAlternativeNameExtension(genNames);
            extension.get("invalid_name");
            throw new AssertionError("Expected IOException for unrecognized attribute name.");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
