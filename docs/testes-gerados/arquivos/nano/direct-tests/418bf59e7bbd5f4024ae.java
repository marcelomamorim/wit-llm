/* @test    @summary Test NameConstraintsExtension.set method for valid and invalid inputs    @run main NameConstraintsExtensionWitupTest */

import sun.security.x509.NameConstraintsExtension;
import sun.security.util.GeneralSubtrees;
import java.io.IOException;

public class NameConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetWithValidGeneralSubtrees();
        testSetWithInvalidAttributeName();
        testSetWithInvalidObjectType();
    }

    private static void testSetWithValidGeneralSubtrees() throws IOException {
        NameConstraintsExtension nce = new NameConstraintsExtension();
        GeneralSubtrees permitted = new GeneralSubtrees();
        GeneralSubtrees excluded = new GeneralSubtrees();
        nce.set("permitted_subtrees", permitted);
        nce.set("excluded_subtrees", excluded);
        // No exception should be thrown, attributes set correctly
        System.out.println("testSetWithValidGeneralSubtrees passed");
    }

    private static void testSetWithInvalidAttributeName() {
        NameConstraintsExtension nce = new NameConstraintsExtension();
        boolean thrown = false;
        try {
            nce.set("invalid_name", new GeneralSubtrees());
        } catch (IOException e) {
            thrown = true;
            System.out.println("Caught expected IOException for invalid attribute name: " + e.getMessage());
        }
        if (!thrown) {
            throw new AssertionError("Expected IOException for invalid attribute name not thrown");
        }
    }

    private static void testSetWithInvalidObjectType() {
        NameConstraintsExtension nce = new NameConstraintsExtension();
        boolean thrown = false;
        try {
            nce.set("permitted_subtrees", "not a GeneralSubtrees object");
        } catch (IOException e) {
            thrown = true;
            System.out.println("Caught expected IOException for invalid object type: " + e.getMessage());
        }
        if (!thrown) {
            throw new AssertionError("Expected IOException for invalid object type not thrown");
        }
    }
}
