/*
 * @test
 * @summary Witup-derived checks for NameConstraintsExtension.get(String)
 * @modules java.base/sun.security.x509
 * @run main NameConstraintsExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.NameConstraintsExtension;

public class NameConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        NameConstraintsExtension ext = new NameConstraintsExtension(null, null);

        if (ext.get(NameConstraintsExtension.PERMITTED_SUBTREES) != null) {
            throw new RuntimeException("permitted subtrees expected to be null");
        }
        if (ext.get(NameConstraintsExtension.EXCLUDED_SUBTREES) != null) {
            throw new RuntimeException("excluded subtrees expected to be null");
        }
        if (ext.get("PERMITTED_SUBTREES") != null) {
            throw new RuntimeException("case-insensitive permitted lookup failed");
        }
        if (ext.get("Excluded_Subtrees") != null) {
            throw new RuntimeException("case-insensitive excluded lookup failed");
        }

        boolean thrown = false;
        try {
            ext.get("unknown_name");
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("IOException expected for unrecognized name");
        }

        thrown = false;
        try {
            ext.get("");
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("IOException expected for empty name");
        }
    }
}
