/*
 * @test
 * @summary Verifies IssuerAlternativeNameExtension.delete accepts the issuer_name attribute (case-insensitively) and rejects unknown attribute names with IOException
 * @modules java.base/sun.security.x509
 * @run main IssuerAlternativeNameExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.IssuerAlternativeNameExtension;

public class IssuerAlternativeNameExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteIssuerNameExact();
        testDeleteIssuerNameIgnoreCase();
        testDeleteUnknownNameThrows("foo");
        testDeleteUnknownNameThrows("");
        testDeleteUnknownNameThrows(IssuerAlternativeNameExtension.NAME);
        testDeleteUnknownNameThrows(IssuerAlternativeNameExtension.IDENT);
    }

    private static void testDeleteIssuerNameExact() throws Exception {
        IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension();
        ext.delete(IssuerAlternativeNameExtension.ISSUER_NAME);
    }

    private static void testDeleteIssuerNameIgnoreCase() throws Exception {
        IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension();
        ext.delete("ISSUER_NAME");
        ext = new IssuerAlternativeNameExtension();
        ext.delete("Issuer_Name");
    }

    private static void testDeleteUnknownNameThrows(String name) throws Exception {
        IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension();
        try {
            ext.delete(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for name: " + name);
    }
}
