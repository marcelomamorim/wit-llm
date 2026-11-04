/* @test    @summary Test for NameConstraintsExtension.set method handling valid and invalid attribute names and types    @run main NameConstraintsExtensionWitupTest */

import sun.security.x509.NameConstraintsExtension;
import sun.security.util.GeneralSubtrees;
import java.io.IOException;

public class NameConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetWithValidPermitted();
        testSetWithValidExcluded();
        testSetWithInvalidAttributeName();
        testSetWithInvalidObjectType();
    }

    static void testSetWithValidPermitted() throws IOException {
        NameConstraintsExtension nce = new NameConstraintsExtension();
        GeneralSubtrees permitted = new GeneralSubtrees();
        nce.set("permitted_subtrees", permitted);
        System.out.println("Permitted subtrees set successfully.");
    }

    static void testSetWithValidExcluded() throws IOException {
        NameConstraintsExtension nce = new NameConstraintsExtension();
        GeneralSubtrees excluded = new GeneralSubtrees();
        nce.set("excluded_subtrees", excluded);
        System.out.println("Excluded subtrees set successfully.");
    }

    static void testSetWithInvalidAttributeName() {
        NameConstraintsExtension nce = new NameConstraintsExtension();
        try {
            nce.set("invalid_attribute", new GeneralSubtrees());
            System.out.println("Error: Exception expected for invalid attribute name.");
        } catch (IOException e) {
            System.out.println("Caught expected IOException for invalid attribute name.");
        }
    }

    static void testSetWithInvalidObjectType() {
        NameConstraintsExtension nce = new NameConstraintsExtension();
        try {
            nce.set("permitted_subtrees", "not a GeneralSubtrees");
            System.out.println("Error: Exception expected for invalid object type.");
        } catch (IOException e) {
            System.out.println("Caught expected IOException for invalid object type.");
        }
    }
}
