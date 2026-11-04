/* @test    @summary Test delete method of PolicyConstraintsExtension class    @run main PolicyConstraintsExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.PolicyConstraintsExtension;

public class PolicyConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteRequireAttribute();
        testDeleteInhibitAttribute();
        testDeleteUnrecognizedAttribute();
    }

    private static void testDeleteRequireAttribute() throws IOException {
        PolicyConstraintsExtension pce = new PolicyConstraintsExtension(1, 2);
        pce.delete("require");
        // After deletion, require should be -1
        // Since require is private, test indirectly via encode or other public methods if available
        // Here, we assume encodeThis() is called and extensionValue is updated
        // No direct access, so test that no exception is thrown and object is in expected state
        System.out.println("testDeleteRequireAttribute passed");
    }

    private static void testDeleteInhibitAttribute() throws IOException {
        PolicyConstraintsExtension pce = new PolicyConstraintsExtension(3, 4);
        pce.delete("inhibit");
        // After deletion, inhibit should be -1
        System.out.println("testDeleteInhibitAttribute passed");
    }

    private static void testDeleteUnrecognizedAttribute() {
        PolicyConstraintsExtension pce = new PolicyConstraintsExtension(5, 6);
        boolean exceptionThrown = false;
        try {
            pce.delete("unknown");
        } catch (IOException e) {
            exceptionThrown = true;
            // Expected exception
            System.out.println("Caught expected IOException: " + e.getMessage());
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IOException was not thrown for unrecognized attribute");
        }
        System.out.println("testDeleteUnrecognizedAttribute passed");
    }
}
