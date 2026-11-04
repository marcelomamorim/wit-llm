/*
 * @test
 * @summary Test sun.security.x509.IssuerAlternativeNameExtension.delete(String) method
 * @run main IssuerAlternativeNameExtensionWitupTest
 */

import sun.security.x509.IssuerAlternativeNameExtension;
import sun.security.x509.GeneralNames;
import sun.security.x509.PKIXExtensions;
import java.io.IOException;

public class IssuerAlternativeNameExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws Exception {
        // Create a dummy GeneralNames instance with no names (empty)
        GeneralNames emptyNames = new GeneralNames();
        IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension(emptyNames);

        // Initially names is not null (empty GeneralNames instance)
        if (ext == null) {
            throw new Exception("Extension instance is null");
        }

        // Delete with valid name ISSUER_NAME should set names to null
        ext.delete(IssuerAlternativeNameExtension.ISSUER_NAME);

        // After delete, names should be null and extensionValue should be null
        // We cannot access names directly, but encodeThis sets extensionValue to null if names is null
        // So we test by calling delete again with valid name and expect no exception
        try {
            ext.delete(IssuerAlternativeNameExtension.ISSUER_NAME);
        } catch (IOException e) {
            throw new Exception("delete threw IOException unexpectedly after names set to null");
        }
    }

    private static void testDeleteWithInvalidName() throws Exception {
        GeneralNames emptyNames = new GeneralNames();
        IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension(emptyNames);

        boolean threw = false;
        try {
            ext.delete("invalid_name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new Exception("IOException message unexpected: " + msg);
            }
            threw = true;
        }
        if (!threw) {
            throw new Exception("Expected IOException was not thrown for invalid attribute name");
        }
    }
}
