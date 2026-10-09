/* @test    @summary Test for sun.security.x509.CRLReasonCodeExtension.get(String) method.
 * @run main CRLReasonCodeExtensionWitupTest_Pa008461a1fe0
 */

import java.io.IOException;
import sun.security.x509.CRLReasonCodeExtension;

public class CRLReasonCodeExtensionWitupTest_Pa008461a1fe0 {

    public static void main(String[] args) throws Exception {
        testGetWithValidReason();
        testGetWithInvalidName();
    }

    private static void testGetWithValidReason() throws Exception {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(1); // reason code 1
        Integer result = ext.get("reason");
        if (result == null || result.intValue() != 1) {
            throw new AssertionError("Expected reason code 1, but got: " + result);
        }
    }

    private static void testGetWithInvalidName() {
        CRLReasonCodeExtension ext;
        try {
            ext = new CRLReasonCodeExtension(1); // reason code 1
        } catch (IOException e) {
            throw new RuntimeException("Failed to create extension", e);
        }
        try {
            ext.get("invalidName");
            throw new AssertionError("Expected IOException for invalid name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Name not supported")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
