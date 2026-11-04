/*
 * @test
 * @summary Test sun.security.x509.InhibitAnyPolicyExtension.get(String) method
 * @run main InhibitAnyPolicyExtensionWitupTest
 */

import sun.security.x509.InhibitAnyPolicyExtension;
import java.io.IOException;

public class InhibitAnyPolicyExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetSkipCerts();
        testGetInvalidName();
    }

    private static void testGetSkipCerts() throws Exception {
        // Create instance with skipCerts = 5
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(5);
        Integer val = ext.get("skip_certs");
        if (val == null || val.intValue() != 5) {
            throw new RuntimeException("Expected skipCerts=5, got: " + val);
        }
        // Also test case-insensitivity
        val = ext.get("SKIP_CERTS");
        if (val == null || val.intValue() != 5) {
            throw new RuntimeException("Expected skipCerts=5 for uppercase key, got: " + val);
        }
    }

    private static void testGetInvalidName() {
        try {
            InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
            ext.get("invalid_name");
            throw new RuntimeException("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
