/*
 * @test
 * @summary Test sun.security.x509.NameConstraintsExtension.set method behavior
 * @run main NameConstraintsExtensionWitupTest
 */

import sun.security.x509.NameConstraintsExtension;
import sun.security.x509.GeneralSubtrees;
import java.io.IOException;

public class NameConstraintsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetPermittedSubtreesWithValidObject();
        testSetExcludedSubtreesWithValidObject();
        testSetWithInvalidObjectType();
        testSetWithUnrecognizedName();
    }

    private static void testSetPermittedSubtreesWithValidObject() throws Exception {
        NameConstraintsExtension ext = new NameConstraintsExtension(null, null);
        GeneralSubtrees gs = new GeneralSubtrees();
        ext.set(NameConstraintsExtension.PERMITTED_SUBTREES, gs);
        // No exception means success
    }

    private static void testSetExcludedSubtreesWithValidObject() throws Exception {
        NameConstraintsExtension ext = new NameConstraintsExtension(null, null);
        GeneralSubtrees gs = new GeneralSubtrees();
        ext.set(NameConstraintsExtension.EXCLUDED_SUBTREES, gs);
        // No exception means success
    }

    private static void testSetWithInvalidObjectType() {
        NameConstraintsExtension ext = new NameConstraintsExtension(null, null);
        try {
            ext.set(NameConstraintsExtension.PERMITTED_SUBTREES, "invalidType");
            throw new RuntimeException("Expected IOException not thrown for invalid object type");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("GeneralSubtrees")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testSetWithUnrecognizedName() {
        NameConstraintsExtension ext = new NameConstraintsExtension(null, null);
        try {
            ext.set("unknown_name", new GeneralSubtrees());
            throw new RuntimeException("Expected IOException not thrown for unrecognized name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
