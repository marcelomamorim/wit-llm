/*
 * @test
 * @summary Test IssuerAlternativeNameExtension.delete(String) method behavior
 * @run main IssuerAlternativeNameExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.IssuerAlternativeNameExtension;

public class IssuerAlternativeNameExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithIssuerName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithIssuerName() throws Exception {
        IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension();
        ext.delete(IssuerAlternativeNameExtension.ISSUER_NAME);
        // After deletion with ISSUER_NAME, names should be null and no exception thrown
        // encodeThis() is called internally, no exception expected
    }

    private static void testDeleteWithInvalidName() {
        IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension();
        try {
            ext.delete("invalid_name");
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
