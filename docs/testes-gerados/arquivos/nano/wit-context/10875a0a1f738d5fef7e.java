/* @test    @summary Test for sun.security.x509.CRLReasonCodeExtension.get(String) method.
 * @run main CRLReasonCodeExtensionWitupTest
 */

import sun.security.x509.CRLReasonCodeExtension;
import java.io.IOException;

public class CRLReasonCodeExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidReason();
        testGetWithInvalidName();
    }

    private static void testGetWithValidReason() throws IOException {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(1); // Reason code 1
        Integer result = ext.get("reason");
        if (result == null || result != 1) {
            throw new AssertionError("Expected reason code 1, got: " + result);
        }
    }

    private static void testGetWithInvalidName() {
        CRLReasonCodeExtension ext;
        try {
            ext = new CRLReasonCodeExtension(1);
            ext.get("invalidName");
            throw new AssertionError("Expected IOException for invalid name");
        } catch (IOException e) {
            if (!e.getMessage().equals("Name not supported by CRLReasonCodeExtension")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
