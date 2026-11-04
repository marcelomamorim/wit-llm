/*
 * @test
 * @summary Tests for sun.security.x509.NameConstraintsExtension.get(String)
 * @run main NameConstraintsExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.NameConstraintsExtension;

public class NameConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetPermitted();
        testGetExcluded();
        testGetInvalidName();
    }

    private static void testGetPermitted() throws IOException {
        NameConstraintsExtension nce = new NameConstraintsExtension();
        // Simulate permitted subtrees being set
        // Note: Since no public constructor or setter is available, we test the default null case
        // and assume permitted is null by default.
        // For a more complete test, reflection or package access would be needed, but per rules, avoid that.
        // Here, test the default behavior.
        try {
            GeneralSubtrees result = nce.get("permitted_subtrees");
            if (result != null) {
                throw new AssertionError("Expected null for permitted_subtrees by default");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testGetExcluded() throws IOException {
        NameConstraintsExtension nce = new NameConstraintsExtension();
        // Similar to above, test default null for excluded
        try {
            GeneralSubtrees result = nce.get("excluded_subtrees");
            if (result != null) {
                throw new AssertionError("Expected null for excluded_subtrees by default");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testGetInvalidName() {
        NameConstraintsExtension nce = new NameConstraintsExtension();
        try {
            nce.get("unknown_name");
            throw new AssertionError("Expected IOException for unrecognized attribute name");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e);
            }
        }
    }
}
