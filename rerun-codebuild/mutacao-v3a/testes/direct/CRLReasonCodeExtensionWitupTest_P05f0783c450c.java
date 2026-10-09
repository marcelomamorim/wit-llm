/*
 * @test
 * @summary Test get method of CRLReasonCodeExtension for reason attribute.
 * @run main CRLReasonCodeExtensionWitupTest_P05f0783c450c
 */

import sun.security.x509.CRLReasonCodeExtension;
import java.io.IOException;

public class CRLReasonCodeExtensionWitupTest_P05f0783c450c {

    public static void main(String[] args) throws Exception {
        testGetReason();
    }

    static void testGetReason() throws IOException {
        int reasonValue = 3;
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(reasonValue);
        Integer result = ext.get("reason");
        if (result == null) {
            throw new AssertionError("get("reason") returned null");
        }
        if (result.intValue() != reasonValue) {
            throw new AssertionError("Expected " + reasonValue + " but got " + result);
        }
    }
}
