/*
 * @test
 * @summary Verify IssuerAlternativeNameExtension.get name handling and IOException for unrecognized names
 * @modules java.base/sun.security.x509
 * @run main IssuerAlternativeNameExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.GeneralNames;
import sun.security.x509.IssuerAlternativeNameExtension;

public class IssuerAlternativeNameExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension();

        if (ext.get(IssuerAlternativeNameExtension.ISSUER_NAME) != null) {
            throw new RuntimeException("Default extension should return null names");
        }
        if (ext.get("ISSUER_NAME") != null) {
            throw new RuntimeException("Case-insensitive match should return null names");
        }
        if (ext.get("Issuer_Name") != null) {
            throw new RuntimeException("Mixed-case match should return null names");
        }

        expectIOException(ext, IssuerAlternativeNameExtension.NAME);
        expectIOException(ext, "unknown");
        expectIOException(ext, "");

        IssuerAlternativeNameExtension critical =
                new IssuerAlternativeNameExtension(Boolean.TRUE, (GeneralNames) null);
        if (critical.get(IssuerAlternativeNameExtension.ISSUER_NAME) != null) {
            throw new RuntimeException("Critical extension with null names should return null");
        }
        expectIOException(critical, IssuerAlternativeNameExtension.NAME);
    }

    private static void expectIOException(IssuerAlternativeNameExtension ext, String name) {
        try {
            ext.get(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for attribute name: " + name);
    }
}
