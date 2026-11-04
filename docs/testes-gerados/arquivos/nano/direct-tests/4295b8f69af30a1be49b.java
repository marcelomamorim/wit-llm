/* @test    @summary Test delete method of CertificateIssuerExtension for recognized attribute name    @run main CertificateIssuerExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.CertificateIssuerExtension;
import sun.security.util.GeneralNames;

public class CertificateIssuerExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithIssuerName();
        testDeleteWithUnrecognizedName();
    }

    private static void testDeleteWithIssuerName() throws Exception {
        GeneralNames gn = new GeneralNames();
        CertificateIssuerExtension ext = new CertificateIssuerExtension(gn);
        ext.delete("issuer");
        if (ext == null) {
            throw new AssertionError("Extension should not be null after delete.");
        }
        // After deletion, names should be null
        if (ext.names != null) {
            throw new AssertionError("Names should be null after delete.");
        }
        // encodeThis should not throw
        ext.encodeThis();
    }

    private static void testDeleteWithUnrecognizedName() {
        GeneralNames gn = new GeneralNames();
        try {
            CertificateIssuerExtension ext = new CertificateIssuerExtension(gn);
            ext.delete("unknownAttribute");
            throw new AssertionError("Expected IOException for unrecognized attribute name.");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
