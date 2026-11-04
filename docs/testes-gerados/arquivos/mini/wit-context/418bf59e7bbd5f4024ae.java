/*
 * @test
 * @summary Test NameConstraintsExtension.set(String,Object) for valid and invalid inputs
 * @run main NameConstraintsExtensionWitupTest
 */

import sun.security.x509.NameConstraintsExtension;
import sun.security.x509.GeneralSubtrees;
import java.io.IOException;

public class NameConstraintsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetWithValidPermittedSubtrees();
        testSetWithValidExcludedSubtrees();
        testSetWithInvalidType();
        testSetWithUnrecognizedName();
    }

    private static void testSetWithValidPermittedSubtrees() throws Exception {
        GeneralSubtrees gs = new GeneralSubtrees();
        NameConstraintsExtension nce = new NameConstraintsExtension(null, null);
        nce.set(NameConstraintsExtension.PERMITTED_SUBTREES, gs);
        // No exception means pass
    }

    private static void testSetWithValidExcludedSubtrees() throws Exception {
        GeneralSubtrees gs = new GeneralSubtrees();
        NameConstraintsExtension nce = new NameConstraintsExtension(null, null);
        nce.set(NameConstraintsExtension.EXCLUDED_SUBTREES, gs);
        // No exception means pass
    }

    private static void testSetWithInvalidType() {
        NameConstraintsExtension nce = new NameConstraintsExtension(null, null);
        try {
            nce.set(NameConstraintsExtension.PERMITTED_SUBTREES, "invalidType");
            throw new RuntimeException("Expected IOException not thrown for invalid type");
        } catch (IOException e) {
            // Expected
        }
        try {
            nce.set(NameConstraintsExtension.EXCLUDED_SUBTREES, Integer.valueOf(5));
            throw new RuntimeException("Expected IOException not thrown for invalid type");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testSetWithUnrecognizedName() {
        NameConstraintsExtension nce = new NameConstraintsExtension(null, null);
        try {
            nce.set("unknown_name", new GeneralSubtrees());
            throw new RuntimeException("Expected IOException not thrown for unrecognized name");
        } catch (IOException e) {
            // Expected
        }
    }
}
