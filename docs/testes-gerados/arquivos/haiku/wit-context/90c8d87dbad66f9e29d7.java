/*
 * @test
 * @summary Checks CertificateIssuerExtension.get(String) returns the issuer for the "issuer" name case-insensitively and throws IOException for other names
 * @modules java.base/sun.security.x509
 * @run main CertificateIssuerExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateIssuerExtension;
import sun.security.x509.GeneralNames;

public class CertificateIssuerExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        GeneralNames names = new GeneralNames();
        CertificateIssuerExtension ext = new CertificateIssuerExtension(names);

        if (ext.get("issuer") != names) {
            throw new RuntimeException("get(\"issuer\") did not return the issuer");
        }
        if (ext.get("ISSUER") != names) {
            throw new RuntimeException("get(\"ISSUER\") did not return the issuer");
        }
        if (ext.get("Issuer") != names) {
            throw new RuntimeException("get(\"Issuer\") did not return the issuer");
        }

        CertificateIssuerExtension nullExt =
                new CertificateIssuerExtension((GeneralNames) null);
        if (nullExt.get("issuer") != null) {
            throw new RuntimeException("expected null issuer for null names");
        }

        expectIOException(ext, "subject");
        expectIOException(ext, "");
        expectIOException(ext, "issuers");
    }

    private static void expectIOException(CertificateIssuerExtension ext, String name) {
        try {
            ext.get(name);
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("expected IOException for name: " + name);
    }
}
