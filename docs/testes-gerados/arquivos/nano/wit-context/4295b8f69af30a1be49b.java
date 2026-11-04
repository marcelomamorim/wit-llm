/* @test    @summary Test for delete method in CertificateIssuerExtension to verify exception handling and behavior.    @run main CertificateIssuerExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.CertificateIssuerExtension;
import sun.security.util.GeneralNames;

public class CertificateIssuerExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithIssuer();
        testDeleteWithOtherName();
    }

    private static void testDeleteWithIssuer() throws Exception {
        GeneralNames issuerNames = new GeneralNames(); // empty or minimal, as no specific data needed
        CertificateIssuerExtension ext = new CertificateIssuerExtension(issuerNames);
        // Should not throw, deleting "issuer" attribute
        ext.delete("issuer");
        // After deletion, names should be null
        assert ext.names == null : "Expected names to be null after delete("issuer")";
    }

    private static void testDeleteWithOtherName() {
        GeneralNames issuerNames = new GeneralNames();
        try {
            CertificateIssuerExtension ext = new CertificateIssuerExtension(issuerNames);
            ext.delete("notIssuer");
            assert false : "Expected IOException when deleting attribute with name not "issuer"";
        } catch (IOException e) {
            assert e.getMessage().contains("Attribute name not recognized") : "Unexpected exception message: " + e.getMessage();
        }
    }
}
