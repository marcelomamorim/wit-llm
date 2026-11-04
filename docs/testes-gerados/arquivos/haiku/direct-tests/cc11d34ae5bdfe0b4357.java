/*
 * @test
 * @summary Verifies NameConstraintsExtension.get(String) returns the permitted and excluded subtrees by name and rejects unknown names
 * @modules java.base/sun.security.x509
 * @run main NameConstraintsExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.NameConstraintsExtension;

public class NameConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        NameConstraintsExtension ext = new NameConstraintsExtension(null, null);

        if (ext.get(NameConstraintsExtension.PERMITTED_SUBTREES) != null) {
            throw new RuntimeException("permitted subtrees should be null");
        }
        if (ext.get(NameConstraintsExtension.EXCLUDED_SUBTREES) != null) {
            throw new RuntimeException("excluded subtrees should be null");
        }
        if (ext.get("PERMITTED_SUBTREES") != null) {
            throw new RuntimeException("case-insensitive permitted lookup failed");
        }
        if (ext.get("Excluded_Subtrees") != null) {
            throw new RuntimeException("case-insensitive excluded lookup failed");
        }

        checkUnknown(ext, "bogus");
        checkUnknown(ext, "");
        checkUnknown(ext, "permitted");
    }

    private static void checkUnknown(NameConstraintsExtension ext, String name) {
        try {
            ext.get(name);
            throw new RuntimeException("expected IOException for name: " + name);
        } catch (IOException expected) {
            // expected
        }
    }
}
