/* @test    @summary Test PolicyConstraintsExtension.get() method for valid attribute names    @run main PolicyConstraintsExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.PolicyConstraintsExtension;

public class PolicyConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetRequire();
        testGetInhibit();
        testGetInvalidName();
    }

    private static void testGetRequire() throws IOException {
        PolicyConstraintsExtension pce = new PolicyConstraintsExtension(2, 5);
        Integer result = pce.get("require");
        if (result == null || result != 2) {
            throw new AssertionError("Expected require to be 2, but got " + result);
        }
    }

    private static void testGetInhibit() throws IOException {
        PolicyConstraintsExtension pce = new PolicyConstraintsExtension(3, 7);
        Integer result = pce.get("inhibit");
        if (result == null || result != 7) {
            throw new AssertionError("Expected inhibit to be 7, but got " + result);
        }
    }

    private static void testGetInvalidName() {
        PolicyConstraintsExtension pce = new PolicyConstraintsExtension(1, 4);
        try {
            pce.get("unknown");
            throw new AssertionError("Expected IOException for unknown attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + msg, e);
            }
        }
    }
}
