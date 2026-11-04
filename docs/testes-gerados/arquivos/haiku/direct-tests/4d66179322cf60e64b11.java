/*
 * @test
 * @summary Verify IssuerAlternativeNameExtension.set accepts GeneralNames for issuer_name and rejects invalid names or values with IOException
 * @modules java.base/sun.security.x509
 * @run main IssuerAlternativeNameExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.GeneralNames;
import sun.security.x509.IssuerAlternativeNameExtension;

public class IssuerAlternativeNameExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension();

        ext.set(IssuerAlternativeNameExtension.ISSUER_NAME, new GeneralNames());
        ext.set("ISSUER_NAME", new GeneralNames());
        ext.set("issuer_name", new GeneralNames());

        expectIOException(ext, IssuerAlternativeNameExtension.ISSUER_NAME, "not a GeneralNames");
        expectIOException(ext, IssuerAlternativeNameExtension.ISSUER_NAME, null);
        expectIOException(ext, "unknown_attribute", new GeneralNames());
        expectIOException(ext, "", new GeneralNames());

        ext.set(IssuerAlternativeNameExtension.ISSUER_NAME, new GeneralNames());
    }

    private static void expectIOException(IssuerAlternativeNameExtension ext,
                                          String name, Object value) {
        try {
            ext.set(name, value);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for name=" + name
                + " value=" + value);
    }
}
