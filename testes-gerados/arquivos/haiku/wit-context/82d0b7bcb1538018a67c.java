/*
 * @test
 * @summary Witup-generated checks for BasicConstraintsExtension.get
 * @modules java.base/sun.security.x509
 * @run main BasicConstraintsExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.BasicConstraintsExtension;

public class BasicConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        BasicConstraintsExtension caExt = new BasicConstraintsExtension(true, 2);
        check(Boolean.TRUE.equals(caExt.get("is_ca")), "is_ca should be TRUE for CA extension");
        check(Integer.valueOf(2).equals(caExt.get("path_len")), "path_len should be 2 for CA extension");
        check(Boolean.TRUE.equals(caExt.get("IS_CA")), "name lookup should be case-insensitive for is_ca");
        check(Integer.valueOf(2).equals(caExt.get("Path_Len")), "name lookup should be case-insensitive for path_len");

        BasicConstraintsExtension leafExt = new BasicConstraintsExtension(false, -1);
        check(Boolean.FALSE.equals(leafExt.get("is_ca")), "is_ca should be FALSE for non-CA extension");
        check(Integer.valueOf(-1).equals(leafExt.get("path_len")), "path_len should reflect constructor value");

        BasicConstraintsExtension criticalExt = new BasicConstraintsExtension(Boolean.TRUE, true, 0);
        check(Boolean.TRUE.equals(criticalExt.get("is_ca")), "is_ca should be TRUE with critical constructor");
        check(Integer.valueOf(0).equals(criticalExt.get("path_len")), "path_len should be 0 with critical constructor");

        expectIOException(caExt, "unknown");
        expectIOException(caExt, "");
        expectIOException(caExt, "is_ca2");
    }

    private static void expectIOException(BasicConstraintsExtension ext, String name) {
        try {
            Object v = ext.get(name);
            throw new RuntimeException("expected IOException for name " + name + " but got " + v);
        } catch (IOException expected) {
            // expected
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
