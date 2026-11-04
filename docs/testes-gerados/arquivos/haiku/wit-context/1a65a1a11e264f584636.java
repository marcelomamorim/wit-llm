/*
 * @test
 * @summary Verify IssuerAlternativeNameExtension.get accepts issuer_name case-insensitively and rejects other names with IOException
 * @modules java.base/sun.security.x509
 * @run main IssuerAlternativeNameExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.IssuerAlternativeNameExtension;

public class IssuerAlternativeNameExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        IssuerAlternativeNameExtension ext = new IssuerAlternativeNameExtension();
        ext.get("issuer_name");
        ext.get("ISSUER_NAME");
        ext.get(IssuerAlternativeNameExtension.ISSUER_NAME);
        checkRejected(ext, "subject_name");
        checkRejected(ext, "");
        checkRejected(ext, "issuer");
    }

    private static void checkRejected(IssuerAlternativeNameExtension ext, String name) {
        try {
            ext.get(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("expected IOException for name: " + name);
    }
}
