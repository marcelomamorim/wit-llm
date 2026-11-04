/*
 * @test
 * @summary Test sun.security.x509.CRLReasonCodeExtension.delete(String) method
 * @run main CRLReasonCodeExtensionWitupTest
 */

import sun.security.x509.CRLReasonCodeExtension;
import java.io.IOException;

public class CRLReasonCodeExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws Exception {
        // Create instance with reason code non-zero to test reset
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(false, 1);
        ext.delete("reason");
        // After delete("reason"), reasonCode should be reset to 0 and no exception thrown
        // We cannot access reasonCode directly, but encodeThis sets extensionValue to null if reasonCode==0
        // So extensionValue should be null
        if (ext.extensionValue != null) {
            throw new RuntimeException("extensionValue should be null after delete with 'reason'");
        }
    }

    private static void testDeleteWithInvalidName() throws Exception {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(false, 1);
        try {
            ext.delete("invalidName");
            throw new RuntimeException("Expected IOException not thrown for invalid name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!"Name not supported by CRLReasonCodeExtension".equals(msg)) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
