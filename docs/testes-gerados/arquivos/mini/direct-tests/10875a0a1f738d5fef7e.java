/*
 * @test
 * @summary Test sun.security.x509.CRLReasonCodeExtension.get(String) method
 * @run main CRLReasonCodeExtensionWitupTest
 */

import sun.security.x509.CRLReasonCodeExtension;
import java.io.IOException;

public class CRLReasonCodeExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetWithReasonName();
        testGetWithDifferentCaseReasonName();
        testGetWithUnsupportedName();
    }

    private static void testGetWithReasonName() throws Exception {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(false, 5);
        Integer reason = ext.get("reason");
        if (reason == null || reason.intValue() != 5) {
            throw new RuntimeException("Expected reason code 5, got " + reason);
        }
    }

    private static void testGetWithDifferentCaseReasonName() throws Exception {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(false, 3);
        Integer reason = ext.get("ReAsOn");
        if (reason == null || reason.intValue() != 3) {
            throw new RuntimeException("Expected reason code 3, got " + reason);
        }
    }

    private static void testGetWithUnsupportedName() {
        try {
            CRLReasonCodeExtension ext = new CRLReasonCodeExtension(false, 1);
            ext.get("unsupportedName");
            throw new RuntimeException("Expected IOException for unsupported name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Name not supported")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Expected IOException but got " + e.getClass().getName(), e);
        }
    }
}
