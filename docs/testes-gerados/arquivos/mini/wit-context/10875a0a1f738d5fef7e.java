/*
 * @test
 * @summary Test sun.security.x509.CRLReasonCodeExtension.get(String) method
 * @run main CRLReasonCodeExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.CRLReasonCodeExtension;

public class CRLReasonCodeExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        // Create instance with reason code 3 (arbitrary valid reason)
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(3);
        Integer reason = ext.get("reason");
        if (reason == null || reason.intValue() != 3) {
            throw new RuntimeException("Expected reason code 3, got: " + reason);
        }
    }

    private static void testGetWithInvalidName() {
        try {
            CRLReasonCodeExtension ext = new CRLReasonCodeExtension(1);
            ext.get("invalidName");
            throw new RuntimeException("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Name not supported")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
