/*
 * @test
 * @summary Test sun.security.x509.PolicyConstraintsExtension.get(String) method
 * @run main PolicyConstraintsExtensionWitupTest
 */

import sun.security.x509.PolicyConstraintsExtension;
import java.io.IOException;

public class PolicyConstraintsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetRequire();
        testGetInhibit();
        testGetInvalidName();
    }

    private static void testGetRequire() throws IOException {
        PolicyConstraintsExtension ext = new PolicyConstraintsExtension(5, -1);
        Integer val = ext.get("require");
        if (val == null || val.intValue() != 5) {
            throw new RuntimeException("Expected require=5 but got " + val);
        }
        val = ext.get("REQUIRE");
        if (val == null || val.intValue() != 5) {
            throw new RuntimeException("Case insensitive check failed for REQUIRE");
        }
    }

    private static void testGetInhibit() throws IOException {
        PolicyConstraintsExtension ext = new PolicyConstraintsExtension(-1, 7);
        Integer val = ext.get("inhibit");
        if (val == null || val.intValue() != 7) {
            throw new RuntimeException("Expected inhibit=7 but got " + val);
        }
        val = ext.get("INHIBIT");
        if (val == null || val.intValue() != 7) {
            throw new RuntimeException("Case insensitive check failed for INHIBIT");
        }
    }

    private static void testGetInvalidName() {
        PolicyConstraintsExtension ext = new PolicyConstraintsExtension(1, 2);
        try {
            ext.get("unknown");
            throw new RuntimeException("Expected IOException for unknown attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
