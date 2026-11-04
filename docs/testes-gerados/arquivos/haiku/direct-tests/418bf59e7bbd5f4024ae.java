/*
 * @test
 * @summary NameConstraintsExtension.set rejects wrong value types and unknown attribute names
 * @modules java.base/sun.security.x509
 * @run main NameConstraintsExtensionWitupTest
 */

import java.io.IOException;

import sun.security.x509.NameConstraintsExtension;

public class NameConstraintsExtensionWitupTest {

    private static int failures = 0;

    private static void expectIOException(NameConstraintsExtension ext,
                                          String name, Object value) {
        try {
            ext.set(name, value);
            System.err.println("FAIL: no IOException for name=" + name
                               + " value=" + value);
            failures++;
        } catch (IOException expected) {
            // expected
        }
    }

    public static void main(String[] args) throws Exception {
        // Empty NameConstraints SEQUENCE: SEQUENCE { } = 0x30 0x00
        NameConstraintsExtension ext =
            new NameConstraintsExtension(Boolean.FALSE, new byte[] {0x30, 0x00});

        expectIOException(ext, NameConstraintsExtension.PERMITTED_SUBTREES, "x");
        expectIOException(ext, NameConstraintsExtension.EXCLUDED_SUBTREES, "x");
        expectIOException(ext, NameConstraintsExtension.PERMITTED_SUBTREES, null);
        expectIOException(ext, NameConstraintsExtension.EXCLUDED_SUBTREES, null);
        expectIOException(ext, NameConstraintsExtension.PERMITTED_SUBTREES, Integer.valueOf(1));
        expectIOException(ext, "permitted_subtrees_unknown", null);
        expectIOException(ext, "bogus", "x");
        expectIOException(ext, "", null);

        // Case-insensitive name matching: upper-case names still reach the type check
        expectIOException(ext, "PERMITTED_SUBTREES", "x");
        expectIOException(ext, "Excluded_Subtrees", "x");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
