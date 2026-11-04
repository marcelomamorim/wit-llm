/*
 * @test
 * @summary WITUP generated test for NameConstraintsExtension.set exception paths
 * @modules java.base/sun.security.x509
 * @run main NameConstraintsExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.NameConstraintsExtension;

public class NameConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        NameConstraintsExtension ext = new NameConstraintsExtension(null, null);

        expectIOException(ext, "unknown_name", null);
        expectIOException(ext, "permitted_subtrees", "not a GeneralSubtrees");
        expectIOException(ext, "EXCLUDED_SUBTREES", Integer.valueOf(1));
        expectIOException(ext, "permitted_subtrees", null);
        expectIOException(ext, "excluded_subtrees", null);
    }

    private static void expectIOException(NameConstraintsExtension ext,
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
