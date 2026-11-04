/*
 * @test
 * @summary Witup test for IssuerAlternativeNameExtension.set rejecting invalid attribute names and values
 * @modules java.base/sun.security.x509
 * @run main IssuerAlternativeNameExtensionWitupTest
 */

import java.io.IOException;

import sun.security.x509.IssuerAlternativeNameExtension;

public class IssuerAlternativeNameExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension();

        expectIOException(ext, "unknown", new Object());
        expectIOException(ext, "IssuerAlternativeName", new Object());
        expectIOException(ext, "", new Object());
        expectIOException(ext, "issuer_nam", new Object());
        expectIOException(ext, "issuer_name", null);
        expectIOException(ext, "issuer_name", "not-general-names");
        expectIOException(ext, "ISSUER_NAME", "not-general-names");
        expectIOException(ext, "issuer_name", Integer.valueOf(1));
    }

    static void expectIOException(IssuerAlternativeNameExtension ext,
                                  String name, Object obj) {
        try {
            ext.set(name, obj);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for name=" + name
                + " value=" + obj);
    }
}
