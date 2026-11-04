/* @test    @summary Test for sun.security.x509.CRLReasonCodeExtension.delete(String) method    @run main CRLReasonCodeExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.CRLReasonCodeExtension;

public class CRLReasonCodeExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws Exception {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(1); // reason code 1
        ext.delete("reason");
        // After deletion, reasonCode should be 0
        if (ext.reasonCode != 0) {
            throw new AssertionError("Expected reasonCode to be 0 after delete with 'reason'");
        }
    }

    private static void testDeleteWithInvalidName() {
        CRLReasonCodeExtension ext;
        try {
            ext = new CRLReasonCodeExtension(1); // reason code 1
            ext.delete("invalidName");
            throw new AssertionError("Expected IOException for invalid name");
        } catch (IOException e) {
            if (!e.getMessage().equals("Name not supported by CRLReasonCodeExtension")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
