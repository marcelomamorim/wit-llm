/*
 * @test
 * @summary Test sun.security.x509.InhibitAnyPolicyExtension.get(String) method
 * @run main InhibitAnyPolicyExtensionWitupTest
 */

import java.io.IOException;

public class InhibitAnyPolicyExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
        System.out.println("All tests passed.");
    }

    private static void testGetWithValidName() throws IOException {
        // Create instance with skipCerts = 5
        sun.security.x509.InhibitAnyPolicyExtension ext = new sun.security.x509.InhibitAnyPolicyExtension(5);
        Integer result = ext.get("skip_certs");
        if (result == null || result.intValue() != 5) {
            throw new AssertionError("Expected skipCerts=5, got: " + result);
        }
        // Also test case-insensitivity
        Integer result2 = ext.get("SKIP_CERTS");
        if (result2 == null || result2.intValue() != 5) {
            throw new AssertionError("Expected skipCerts=5 for uppercase key, got: " + result2);
        }
    }

    private static void testGetWithInvalidName() {
        try {
            sun.security.x509.InhibitAnyPolicyExtension ext = new sun.security.x509.InhibitAnyPolicyExtension(3);
            ext.get("invalid_name");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
