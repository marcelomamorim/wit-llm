/*
 * @test
 * @summary Verify CertificateIssuerExtension.get(String) returns the issuer for the "issuer" name case-insensitively and throws IOException for unrecognized names
 * @modules java.base/sun.security.x509
 * @run main CertificateIssuerExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateIssuerExtension;

public class CertificateIssuerExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        CertificateIssuerExtension ext = new CertificateIssuerExtension(null);

        check(ext.get("issuer") == null, "get(\"issuer\") should return names (null)");
        check(ext.get("ISSUER") == null, "get(\"ISSUER\") should match case-insensitively");
        check(ext.get("Issuer") == null, "get(\"Issuer\") should match case-insensitively");

        expectIOException(ext, "subject");
        expectIOException(ext, "");
        expectIOException(ext, "issuerX");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void expectIOException(CertificateIssuerExtension ext, String name) {
        try {
            ext.get(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for attribute name: \"" + name + "\"");
    }
}
