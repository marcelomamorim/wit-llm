/* @test    @summary Test constructor CRLReasonCodeExtension(int reason) for sun.security.x509.CRLReasonCodeExtension    @run main CRLReasonCodeExtensionWitupTest */

import sun.security.x509.CRLReasonCodeExtension;
import java.io.IOException;

public class CRLReasonCodeExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithReason();
        testConstructorWithReasonAndCritical();
    }

    static void testConstructorWithReason() throws IOException {
        int reason = 1; // Example reason code
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(reason);
        assert ext != null : "Extension should not be null";
    }

    static void testConstructorWithReasonAndCritical() throws IOException {
        int reason = 2; // Example reason code
        boolean critical = true;
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(critical, reason);
        assert ext != null : "Extension should not be null";
    }
}
