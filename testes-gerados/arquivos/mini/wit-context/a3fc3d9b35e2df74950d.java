/*
 * @test
 * @summary Test sun.security.x509.InhibitAnyPolicyExtension.delete(String) throws IOException for recognized and unrecognized attribute names
 * @run main InhibitAnyPolicyExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.InhibitAnyPolicyExtension;

public class InhibitAnyPolicyExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteThrowsForSkipCerts();
        testDeleteThrowsForUnrecognizedName();
    }

    private static void testDeleteThrowsForSkipCerts() {
        try {
            InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
            ext.delete("skip_certs");
            throw new RuntimeException("Expected IOException not thrown for skip_certs");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("skip_certs")) {
                throw new RuntimeException("IOException message does not mention skip_certs: " + msg);
            }
        }
    }

    private static void testDeleteThrowsForUnrecognizedName() {
        try {
            InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
            ext.delete("unknown_attribute");
            throw new RuntimeException("Expected IOException not thrown for unknown attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("IOException message does not mention not recognized: " + msg);
            }
        }
    }
}
