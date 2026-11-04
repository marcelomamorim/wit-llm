/*
 * @test
 * @summary Test sun.security.x509.PolicyConstraintsExtension.delete method
 * @run main PolicyConstraintsExtensionWitupTest
 */

import sun.security.x509.PolicyConstraintsExtension;
import java.io.IOException;

public class PolicyConstraintsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteRequire();
        testDeleteInhibit();
        testDeleteInvalid();
    }

    private static void testDeleteRequire() throws IOException {
        PolicyConstraintsExtension ext = new PolicyConstraintsExtension(5, 10);
        ext.delete(PolicyConstraintsExtension.REQUIRE);
        // After delete REQUIRE, require should be reset to -1, so deleting again should not fail
        ext.delete(PolicyConstraintsExtension.REQUIRE);
    }

    private static void testDeleteInhibit() throws IOException {
        PolicyConstraintsExtension ext = new PolicyConstraintsExtension(5, 10);
        ext.delete(PolicyConstraintsExtension.INHIBIT);
        // After delete INHIBIT, inhibit should be reset to -1, so deleting again should not fail
        ext.delete(PolicyConstraintsExtension.INHIBIT);
    }

    private static void testDeleteInvalid() {
        PolicyConstraintsExtension ext = new PolicyConstraintsExtension(5, 10);
        try {
            ext.delete("invalidName");
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
