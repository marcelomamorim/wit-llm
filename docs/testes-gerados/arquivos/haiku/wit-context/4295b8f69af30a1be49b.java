/*
 * @test
 * @summary Checks CertificateIssuerExtension.delete(String) name handling: accepted issuer name (case-insensitive), IOException for unknown names, NPE for null name
 * @modules java.base/sun.security.x509
 * @run main CertificateIssuerExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateIssuerExtension;
import sun.security.x509.GeneralNames;

public class CertificateIssuerExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        CertificateIssuerExtension ext = new CertificateIssuerExtension((GeneralNames) null);
        ext.delete("issuer");

        ext = new CertificateIssuerExtension((GeneralNames) null);
        ext.delete("ISSUER");

        ext = new CertificateIssuerExtension((GeneralNames) null);
        boolean thrown = false;
        try {
            ext.delete("subject");
        } catch (IOException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("IOException expected for unrecognized attribute name");
        }

        ext = new CertificateIssuerExtension((GeneralNames) null);
        thrown = false;
        try {
            ext.delete(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("NullPointerException expected for null name");
        }
    }
}
