/* @test    @summary Test for sun.security.x509.InhibitAnyPolicyExtension.get(String) method
 * @run main InhibitAnyPolicyExtensionWitupTest
 */

import sun.security.x509.InhibitAnyPolicyExtension;
import java.io.IOException;

public class InhibitAnyPolicyExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithSkipCerts();
        testGetWithUnrecognizedName();
    }

    private static void testGetWithSkipCerts() throws IOException {
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(5);
        Integer result = ext.get("skip_certs");
        if (result == null || result != 5) {
            throw new AssertionError("Expected skip_certs to be 5, but got " + result);
        }
    }

    private static void testGetWithUnrecognizedName() {
        InhibitAnyPolicyExtension ext;
        try {
            ext = new InhibitAnyPolicyExtension(3);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            ext.get("unknown_attribute");
            throw new AssertionError("Expected IOException for unrecognized attribute");
        } catch (IOException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + message);
            }
        }
    }
}
