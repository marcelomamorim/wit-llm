/*
 * @test
 * @summary Test NameConstraintsExtension.delete(String) method behavior
 * @run main NameConstraintsExtensionWitupTest
 */

import sun.security.x509.NameConstraintsExtension;
import sun.security.x509.GeneralSubtrees;
import java.io.IOException;

public class NameConstraintsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeletePermittedSubtrees();
        testDeleteExcludedSubtrees();
        testDeleteInvalidNameThrows();
    }

    private static void testDeletePermittedSubtrees() throws Exception {
        GeneralSubtrees permitted = new GeneralSubtrees();
        GeneralSubtrees excluded = new GeneralSubtrees();
        NameConstraintsExtension ext = new NameConstraintsExtension(permitted, excluded);

        ext.delete(NameConstraintsExtension.PERMITTED_SUBTREES);

        if (ext.get(NameConstraintsExtension.PERMITTED_SUBTREES) != null) {
            throw new RuntimeException("delete did not clear permitted subtrees");
        }
    }

    private static void testDeleteExcludedSubtrees() throws Exception {
        GeneralSubtrees permitted = new GeneralSubtrees();
        GeneralSubtrees excluded = new GeneralSubtrees();
        NameConstraintsExtension ext = new NameConstraintsExtension(permitted, excluded);

        ext.delete(NameConstraintsExtension.EXCLUDED_SUBTREES);

        if (ext.get(NameConstraintsExtension.EXCLUDED_SUBTREES) != null) {
            throw new RuntimeException("delete did not clear excluded subtrees");
        }
    }

    private static void testDeleteInvalidNameThrows() throws Exception {
        GeneralSubtrees permitted = new GeneralSubtrees();
        GeneralSubtrees excluded = new GeneralSubtrees();
        NameConstraintsExtension ext = new NameConstraintsExtension(permitted, excluded);

        boolean threw = false;
        try {
            ext.delete("invalid_name");
        } catch (IOException e) {
            threw = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
        if (!threw) {
            throw new RuntimeException("delete did not throw IOException for invalid name");
        }
    }
}
