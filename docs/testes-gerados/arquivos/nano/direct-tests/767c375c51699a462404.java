/* @test    @summary Test delete method of IssuerAlternativeNameExtension for known attribute name    @run main IssuerAlternativeNameExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.IssuerAlternativeNameExtension;
import sun.security.util.GeneralNames;

public class IssuerAlternativeNameExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithIssuerName();
        testDeleteWithUnknownName();
    }

    static void testDeleteWithIssuerName() throws IOException {
        GeneralNames gn = new GeneralNames();
        IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension(gn);
        ext.delete("issuer_name");
        if (ext.names != null) {
            throw new AssertionError("Expected names to be null after delete") ;
        }
    }

    static void testDeleteWithUnknownName() {
        GeneralNames gn = new GeneralNames();
        IssuerAlternativeNameExtension ext;
        try {
            ext = new IssuerAlternativeNameExtension(gn);
            ext.delete("unknown_name");
            throw new AssertionError("Expected IOException for unknown attribute name") ;
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
