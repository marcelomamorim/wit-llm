/* @test    @summary Test for PolicyConstraintsExtension.get(String) method to verify exception handling and attribute retrieval    @run main PolicyConstraintsExtensionWitupTest */

import sun.security.x509.PolicyConstraintsExtension;
import java.io.IOException;

public class PolicyConstraintsExtensionWitupTest {

    public static void main(String[] args) {
        testGetWithRequire();
        testGetWithInhibit();
        testGetWithInvalidName();
    }

    static void testGetWithRequire() {
        PolicyConstraintsExtension pce = new PolicyConstraintsExtension(1, 2);
        try {
            Integer result = pce.get("require");
            if (result == null || result != 1) {
                throw new AssertionError("Expected require to be 1, but got: " + result);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for 'require': " + e);
        }
    }

    static void testGetWithInhibit() {
        PolicyConstraintsExtension pce = new PolicyConstraintsExtension(3, 4);
        try {
            Integer result = pce.get("inhibit");
            if (result == null || result != 4) {
                throw new AssertionError("Expected inhibit to be 4, but got: " + result);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for 'inhibit': " + e);
        }
    }

    static void testGetWithInvalidName() {
        PolicyConstraintsExtension pce = new PolicyConstraintsExtension(5, 6);
        boolean thrown = false;
        try {
            pce.get("unknown");
        } catch (IOException e) {
            thrown = true;
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected message in IOException: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected IOException for invalid attribute name");
        }
    }
}
