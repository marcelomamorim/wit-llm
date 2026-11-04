/*
 * @test
 * @summary Verifies BasicConstraintsExtension.get returns observable attribute values and rejects unknown names
 * @modules java.base/sun.security.x509
 * @run main BasicConstraintsExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.BasicConstraintsExtension;

public class BasicConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        BasicConstraintsExtension ca = new BasicConstraintsExtension(true, 3);
        check(Boolean.TRUE.equals(ca.get("is_ca")), "is_ca should be TRUE for CA");
        check(Integer.valueOf(3).equals(ca.get("path_len")), "path_len should be 3");
        check(Boolean.TRUE.equals(ca.get("IS_CA")), "is_ca lookup should be case-insensitive");
        check(Integer.valueOf(3).equals(ca.get("Path_Len")), "path_len lookup should be case-insensitive");

        BasicConstraintsExtension leaf = new BasicConstraintsExtension(false, 5);
        check(Boolean.FALSE.equals(leaf.get("is_ca")), "is_ca should be FALSE for non-CA");
        check(Integer.valueOf(5).equals(leaf.get("path_len")), "path_len should return the given length");

        BasicConstraintsExtension noLen = new BasicConstraintsExtension(true, -1);
        check(Integer.valueOf(-1).equals(noLen.get("path_len")), "path_len should be -1 when no limit given");

        BasicConstraintsExtension crit = new BasicConstraintsExtension(Boolean.FALSE, true, 0);
        check(Boolean.TRUE.equals(crit.get(BasicConstraintsExtension.IS_CA)), "constant IS_CA lookup should be TRUE");
        check(Integer.valueOf(0).equals(crit.get(BasicConstraintsExtension.PATH_LEN)), "constant PATH_LEN lookup should be 0");

        checkUnknown(ca, "unknown");
        checkUnknown(ca, "");
        checkUnknown(leaf, "basic");
    }

    private static void checkUnknown(BasicConstraintsExtension ext, String name) {
        try {
            ext.get(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("expected IOException for attribute name: " + name);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
