/* @test    @summary Test get method of InhibitAnyPolicyExtension for skip_certs attribute    @run main InhibitAnyPolicyExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.InhibitAnyPolicyExtension;

public class InhibitAnyPolicyExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetSkipCerts();
    }

    private static void testGetSkipCerts() throws IOException {
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(5);
        Integer result = ext.get("skip_certs");
        if (result == null) {
            throw new AssertionError("Expected non-null result for skip_certs");
        }
        if (result != 5) {
            throw new AssertionError("Expected 5, but got " + result);
        }
        // Test case-insensitivity
        Integer resultCaseInsensitive = ext.get("SKIP_CERTS");
        if (resultCaseInsensitive == null) {
            throw new AssertionError("Expected non-null result for SKIP_CERTS");
        }
        if (resultCaseInsensitive != 5) {
            throw new AssertionError("Expected 5, but got " + resultCaseInsensitive);
        }
        // Test unrecognized attribute name
        try {
            ext.get("unknown_attribute");
            throw new AssertionError("Expected IOException for unrecognized attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + msg, e);
            }
        }
    }
}
