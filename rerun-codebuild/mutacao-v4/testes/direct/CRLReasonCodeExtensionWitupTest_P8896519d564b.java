/* @test
   @summary Tests the get method of CRLReasonCodeExtension for valid and invalid attribute names.
   @run main CRLReasonCodeExtensionWitupTest_P8896519d564b
 */
import java.io.IOException;
import sun.security.x509.CRLReasonCodeExtension;

public class CRLReasonCodeExtensionWitupTest_P8896519d564b {
    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws Exception {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(2); // Reason code 2
        Integer reason = ext.get("reason");
        if (reason == null || reason.intValue() != 2) {
            throw new AssertionError("Expected reason code 2, but got: " + reason);
        }
    }

    private static void testGetWithInvalidName() {
        try {
            CRLReasonCodeExtension ext = new CRLReasonCodeExtension(1); // Reason code 1
            ext.get("invalidName");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            if (!e.getMessage().contains("Name not supported")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
            // expected
        }
    }
}
