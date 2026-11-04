/* @test    @summary Test for sun.security.x509.CRLReasonCodeExtension constructor with reason parameter    @run main CRLReasonCodeExtensionWitupTest */

import sun.security.x509.CRLReasonCodeExtension;
import java.io.IOException;

public class CRLReasonCodeExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithReason();
    }

    private static void testConstructorWithReason() throws IOException {
        int reason = 1; // Example reason code
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(reason);
        if (ext == null) {
            throw new AssertionError("CRLReasonCodeExtension instance is null");
        }
    }
}
