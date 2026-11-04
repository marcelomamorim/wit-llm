/* @test    @summary Test get method of NameConstraintsExtension for permitted and excluded subtrees    @run main NameConstraintsExtensionWitupTest */

import sun.security.x509.NameConstraintsExtension;
import sun.security.util.GeneralSubtrees;
import java.io.IOException;

public class NameConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetPermitted();
        testGetExcluded();
        testGetInvalidName();
    }

    static void testGetPermitted() throws IOException {
        NameConstraintsExtension extension = new NameConstraintsExtension();
        GeneralSubtrees permitted = new GeneralSubtrees();
        extension.get("permitted_subtrees"); // Should be null initially
        extension.permitted = permitted;
        assert extension.get("permitted_subtrees") == permitted : "Permitted subtrees should match";
    }

    static void testGetExcluded() throws IOException {
        NameConstraintsExtension extension = new NameConstraintsExtension();
        GeneralSubtrees excluded = new GeneralSubtrees();
        extension.excluded = excluded;
        assert extension.get("excluded_subtrees") == excluded : "Excluded subtrees should match";
    }

    static void testGetInvalidName() {
        try {
            NameConstraintsExtension extension = new NameConstraintsExtension();
            extension.get("invalid_name");
            assert false : "Expected IOException for invalid attribute name";
        } catch (IOException e) {
            assert e.getMessage().contains("Attribute name not recognized") : "Exception message should mention unrecognized attribute";
        }
    }
}
