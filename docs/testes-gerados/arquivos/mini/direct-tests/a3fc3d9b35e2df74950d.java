/*
 * @test
 * @summary Test sun.security.x509.InhibitAnyPolicyExtension.delete method behavior
 * @run main InhibitAnyPolicyExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.InhibitAnyPolicyExtension;

public class InhibitAnyPolicyExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteSkipCertsThrows();
        testDeleteUnknownNameThrows();
    }

    private static void testDeleteSkipCertsThrows() {
        InhibitAnyPolicyExtension ext = null;
        try {
            ext = new InhibitAnyPolicyExtension(0);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in constructor", e);
        }
        try {
            ext.delete("skip_certs");
            throw new RuntimeException("Expected IOException not thrown for skip_certs");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("skip_certs may not be deleted")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testDeleteUnknownNameThrows() {
        InhibitAnyPolicyExtension ext = null;
        try {
            ext = new InhibitAnyPolicyExtension(0);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in constructor", e);
        }
        try {
            ext.delete("unknown_attribute");
            throw new RuntimeException("Expected IOException not thrown for unknown attribute");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
