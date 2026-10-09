/* @test
   @summary Verifica comportamento do método get de CRLReasonCodeExtension.
   @run main CRLReasonCodeExtensionWitupTest_P8896519d564b
 */
import java.io.IOException;
import sun.security.x509.CRLReasonCodeExtension;
public class CRLReasonCodeExtensionWitupTest_P8896519d564b {
    public static void main(String[] args) throws Exception {
        testGetWithValidReason();
        testGetWithInvalidName();
    }
    private static void testGetWithValidReason() throws Exception {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(1); // reason code 1
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
            if (!"Name not supported by CRLReasonCodeExtension".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
