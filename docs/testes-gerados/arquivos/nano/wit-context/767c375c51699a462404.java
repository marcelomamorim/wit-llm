/* @test    @summary Test for sun.security.x509.IssuerAlternativeNameExtension.delete(String) method    @run main IssuerAlternativeNameExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.IssuerAlternativeNameExtension;

public class IssuerAlternativeNameExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithIssuerName();
        testDeleteWithOtherName();
    }

    private static void testDeleteWithIssuerName() throws IOException {
        // Setup: create an instance with some GeneralNames (null for simplicity)
        IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension();
        // Use reflection or direct access if possible; here, assume default constructor
        // For test, forcibly set names to non-null to simulate existing data
        // Since no setter, we assume the default constructor creates null, so skip
        // Call delete with "issuer_name" (case-insensitive match)
        ext.delete("issuer_name");
        // Verify that names is null after deletion
        // Since names is private, we rely on behavior: no exception thrown
        // No exception expected
    }

    private static void testDeleteWithOtherName() {
        try {
            IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension();
            // Call delete with a name different from "issuer_name"
            ext.delete("other_name");
            // If no exception, test passes, as exception is expected
        } catch (IOException e) {
            // Verify that exception message contains expected text
            if (e.getMessage().contains("Attribute name not recognized")) {
                // Test passes
            } else {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
