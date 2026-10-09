/* @test    @summary Test for CRLReasonCodeExtension.get(String) method    @run main CRLReasonCodeExtensionWitupTest_Pa008461a1fe0 */

import sun.security.x509.CRLReasonCodeExtension;
import java.io.IOException;

public class CRLReasonCodeExtensionWitupTest_Pa008461a1fe0 {

    public static void main(String[] args) throws Exception {
        testGetWithValidReason();
        testGetWithInvalidName();
    }

    private static void testGetWithValidReason() throws IOException {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(2); // Reason code 2
        Integer result = ext.get("reason");
        if (result == null || result.intValue() != 2) {
            throw new AssertionError("Expected reason code 2, but got: " + result);
        }
    }

    private static void testGetWithInvalidName() {
        CRLReasonCodeExtension ext;
        try {
            ext = new CRLReasonCodeExtension(1); // Reason code 1
        } catch (IOException e) {
            throw new RuntimeException("Failed to create extension", e);
        }
        try {
            ext.get("invalidName");
            throw new AssertionError("Expected IOException for invalid name");
        } catch (IOException e) {
            if (!e.getMessage().contains("Name not supported")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
