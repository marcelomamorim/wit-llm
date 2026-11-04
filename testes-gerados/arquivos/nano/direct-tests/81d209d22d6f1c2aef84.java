/* @test    @summary Test delete method of NameConstraintsExtension class    @run main NameConstraintsExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.NameConstraintsExtension;

public class NameConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeletePermitted();
        testDeleteExcluded();
        testDeleteInvalidAttribute();
    }

    static void testDeletePermitted() throws IOException {
        NameConstraintsExtension nce = new NameConstraintsExtension();
        nce.delete("permitted_subtrees");
        // After deletion, permitted should be null
        assert nce.permitted == null : "Permitted subtrees should be null after delete";
    }

    static void testDeleteExcluded() throws IOException {
        NameConstraintsExtension nce = new NameConstraintsExtension();
        nce.delete("excluded_subtrees");
        // After deletion, excluded should be null
        assert nce.excluded == null : "Excluded subtrees should be null after delete";
    }

    static void testDeleteInvalidAttribute() {
        NameConstraintsExtension nce = new NameConstraintsExtension();
        boolean exceptionThrown = false;
        try {
            nce.delete("invalid_attribute");
        } catch (IOException e) {
            exceptionThrown = true;
            assert e.getMessage().contains("Attribute name not recognized") : "Exception message should mention unrecognized attribute";
        }
        assert exceptionThrown : "Expected IOException for invalid attribute";
    }
}
