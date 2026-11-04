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
        PolicyConstraintsExtension ext = new PolicyConstraintsExtension(5, 10);
        Integer requireValue = ext.get("require");
        if (requireValue == null || requireValue.intValue() != 5) {
            throw new RuntimeException("Expected require=5 but got " + requireValue);
        }
    }

    private static void testGetInhibit() throws IOException {
        PolicyConstraintsExtension ext = new PolicyConstraintsExtension(5, 10);
        Integer inhibitValue = ext.get("inhibit");
        if (inhibitValue == null || inhibitValue.intValue() != 10) {
            throw new RuntimeException("Expected inhibit=10 but got " + inhibitValue);
        }
    }

    private static void testGetInvalidName() {
        PolicyConstraintsExtension ext = new PolicyConstraintsExtension(5, 10);
        try {
            ext.get("invalidName");
            throw new RuntimeException("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
