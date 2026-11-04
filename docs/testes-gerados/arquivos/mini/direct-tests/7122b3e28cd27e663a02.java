/*
 * @test
 * @summary Test CRLReasonCodeExtension constructor with int reason
 * @run main CRLReasonCodeExtensionWitupTest
 */

import sun.security.x509.CRLReasonCodeExtension;
import java.io.IOException;

public class CRLReasonCodeExtensionWitupTest {
    public static void main(String[] args) throws IOException {
        // Test with reason code 0 (should produce null extensionValue)
        CRLReasonCodeExtension ext0 = new CRLReasonCodeExtension(0);
        if (ext0.extensionValue != null) {
            throw new RuntimeException("extensionValue should be null for reason 0");
        }

        // Test with a valid non-zero reason code (e.g. 1)
        CRLReasonCodeExtension ext1 = new CRLReasonCodeExtension(1);
        if (ext1.extensionValue == null || ext1.extensionValue.length == 0) {
            throw new RuntimeException("extensionValue should not be null or empty for reason 1");
        }

        // Test with a larger reason code (e.g. 7)
        CRLReasonCodeExtension ext7 = new CRLReasonCodeExtension(7);
        if (ext7.extensionValue == null || ext7.extensionValue.length == 0) {
            throw new RuntimeException("extensionValue should not be null or empty for reason 7");
        }
    }
}
