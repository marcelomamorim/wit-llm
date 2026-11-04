/*
 * @test
 * @summary Test NameConstraintsExtension.delete(String) method behavior
 * @run main NameConstraintsExtensionWitupTest
 */

import sun.security.x509.NameConstraintsExtension;
import java.io.IOException;

public class NameConstraintsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeletePermittedSubtrees();
        testDeleteExcludedSubtrees();
        testDeleteInvalidNameThrows();
    }

    private static void testDeletePermittedSubtrees() throws IOException {
        NameConstraintsExtension ext = new NameConstraintsExtension(null, null);
        ext.delete(NameConstraintsExtension.PERMITTED_SUBTREES);
        // After deletion, permitted should be null and encodeThis() should not throw
        // No direct getter, so just call delete again with excluded to check no exception
        ext.delete(NameConstraintsExtension.EXCLUDED_SUBTREES);
    }

    private static void testDeleteExcludedSubtrees() throws IOException {
        NameConstraintsExtension ext = new NameConstraintsExtension(null, null);
        ext.delete(NameConstraintsExtension.EXCLUDED_SUBTREES);
        // After deletion, excluded should be null and encodeThis() should not throw
        // No direct getter, so just call delete again with permitted to check no exception
        ext.delete(NameConstraintsExtension.PERMITTED_SUBTREES);
    }

    private static void testDeleteInvalidNameThrows() {
        NameConstraintsExtension ext = new NameConstraintsExtension(null, null);
        try {
            ext.delete("invalid_name");
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
