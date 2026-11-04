/*
 * @test
 * @summary Verify that OCSPNoCheckExtension.delete(String) always throws IOException with the documented message
 * @modules java.base/sun.security.x509
 * @run main OCSPNoCheckExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.OCSPNoCheckExtension;

public class OCSPNoCheckExtensionWitupTest {

    private static final String EXPECTED_MESSAGE =
        "No attribute is allowed by CertAttrSet:OCSPNoCheckExtension.";

    public static void main(String[] args) throws Exception {
        OCSPNoCheckExtension ext = new OCSPNoCheckExtension();
        expectDeleteFailure(ext, "OCSPNoCheck");
        expectDeleteFailure(ext, "");
        expectDeleteFailure(ext, null);

        OCSPNoCheckExtension extCritical = new OCSPNoCheckExtension(Boolean.TRUE, null);
        expectDeleteFailure(extCritical, "OCSPNoCheck");

        OCSPNoCheckExtension extNonCritical = new OCSPNoCheckExtension(Boolean.FALSE, null);
        expectDeleteFailure(extNonCritical, "OCSPNoCheck");
    }

    private static void expectDeleteFailure(OCSPNoCheckExtension ext, String name) {
        try {
            ext.delete(name);
        } catch (IOException e) {
            if (!EXPECTED_MESSAGE.equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("delete did not throw for name: " + name);
    }
}
