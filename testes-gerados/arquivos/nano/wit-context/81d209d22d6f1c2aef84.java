/* @test    @summary Test for NameConstraintsExtension.delete(String) method to verify exception handling and behavior.    @run main NameConstraintsExtensionWitupTest */

import sun.security.x509.NameConstraintsExtension;
import java.io.IOException;

public class NameConstraintsExtensionWitupTest {

    public static void main(String[] args) {
        testDeleteRecognizedAttribute();
        testDeleteUnrecognizedAttribute();
    }

    private static void testDeleteRecognizedAttribute() {
        NameConstraintsExtension nce = new NameConstraintsExtension();
        try {
            // Test deleting "permitted_subtrees"
            nce.delete("permitted_subtrees");
            // After deletion, permitted should be null
            if (nce.permitted != null) {
                throw new AssertionError("Expected permitted to be null after delete.");
            }
            // Excluded should remain unaffected
            if (nce.excluded != null) {
                // OK, as default is null
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }

        try {
            // Test deleting "excluded_subtrees"
            nce.delete("excluded_subtrees");
            // After deletion, excluded should be null
            if (nce.excluded != null) {
                throw new AssertionError("Expected excluded to be null after delete.");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }

    private static void testDeleteUnrecognizedAttribute() {
        NameConstraintsExtension nce = new NameConstraintsExtension();
        boolean exceptionThrown = false;
        try {
            nce.delete("unknown_attribute");
        } catch (IOException e) {
            exceptionThrown = true;
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IOException to be thrown for unrecognized attribute.");
        }
    }
}
