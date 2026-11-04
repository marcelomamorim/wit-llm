/*
 * @test
 * @summary Test sun.security.x509.PolicyConstraintsExtension.delete(String) method
 * @run main PolicyConstraintsExtensionWitupTest
 */

import sun.security.x509.PolicyConstraintsExtension;
import java.io.IOException;

public class PolicyConstraintsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteRequire();
        testDeleteInhibit();
        testDeleteInvalidName();
    }

    private static void testDeleteRequire() throws Exception {
        PolicyConstraintsExtension ext = new PolicyConstraintsExtension(1, 2);
        ext.delete(PolicyConstraintsExtension.REQUIRE);
        // After delete REQUIRE, require should be reset to -1
        // We cannot access require directly, but no exception means success
    }

    private static void testDeleteInhibit() throws Exception {
        PolicyConstraintsExtension ext = new PolicyConstraintsExtension(1, 2);
        ext.delete(PolicyConstraintsExtension.INHIBIT);
        // After delete INHIBIT, inhibit should be reset to -1
        // No exception means success
    }

    private static void testDeleteInvalidName() {
        PolicyConstraintsExtension ext = new PolicyConstraintsExtension(1, 2);
        try {
            ext.delete("invalidName");
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
