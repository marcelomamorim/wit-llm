/*
 * @test
 * @summary Test sun.security.x509.CRLReasonCodeExtension.delete method
 * @run main CRLReasonCodeExtensionWitupTest
 */

import sun.security.x509.CRLReasonCodeExtension;
import java.io.IOException;

public class CRLReasonCodeExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithReasonName();
        testDeleteWithUnsupportedName();
    }

    private static void testDeleteWithReasonName() throws Exception {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(5); // arbitrary reason code
        ext.delete("reason");
        // After delete("reason"), reasonCode should be reset to 0 and extensionValue null
        // We cannot access reasonCode directly, but extensionValue is public in Extension
        // Check that extensionValue is null (encoded as null when reasonCode == 0)
        if (ext.extensionValue != null) {
            throw new RuntimeException("extensionValue should be null after delete with 'reason'");
        }
    }

    private static void testDeleteWithUnsupportedName() {
        try {
            CRLReasonCodeExtension ext = new CRLReasonCodeExtension(3);
            ext.delete("unsupportedName");
            throw new RuntimeException("Expected IOException not thrown for unsupported name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!"Name not supported by CRLReasonCodeExtension".equals(msg)) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }
}
