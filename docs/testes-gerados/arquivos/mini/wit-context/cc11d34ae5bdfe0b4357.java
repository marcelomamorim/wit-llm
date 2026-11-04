/*
 * @test
 * @summary Test NameConstraintsExtension.get(String) method behavior
 * @run main NameConstraintsExtensionWitupTest
 */

import sun.security.x509.NameConstraintsExtension;
import sun.security.x509.GeneralSubtrees;
import java.io.IOException;

public class NameConstraintsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        // Setup: create NameConstraintsExtension with null permitted and excluded
        NameConstraintsExtension ext = new NameConstraintsExtension(null, null);

        // Test 1: get with "permitted_subtrees" returns permitted (null expected)
        GeneralSubtrees permitted = ext.get(NameConstraintsExtension.PERMITTED_SUBTREES);
        if (permitted != null) {
            throw new RuntimeException("Expected permitted to be null but was not");
        }

        // Test 2: get with "excluded_subtrees" returns excluded (null expected)
        GeneralSubtrees excluded = ext.get(NameConstraintsExtension.EXCLUDED_SUBTREES);
        if (excluded != null) {
            throw new RuntimeException("Expected excluded to be null but was not");
        }

        // Test 3: get with unrecognized name throws IOException
        boolean threw = false;
        try {
            ext.get("unknown_attribute");
        } catch (IOException e) {
            threw = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException was not thrown for unknown attribute name");
        }

        // Test 4: get with case-insensitive "PERMITTED_SUBTREES" returns permitted
        GeneralSubtrees permittedUpper = ext.get("PERMITTED_SUBTREES");
        if (permittedUpper != null) {
            throw new RuntimeException("Expected permitted to be null for uppercase name but was not");
        }

        // Test 5: get with case-insensitive "Excluded_SubTrees" returns excluded
        GeneralSubtrees excludedMixed = ext.get("Excluded_SubTrees");
        if (excludedMixed != null) {
            throw new RuntimeException("Expected excluded to be null for mixed case name but was not");
        }
    }
}
