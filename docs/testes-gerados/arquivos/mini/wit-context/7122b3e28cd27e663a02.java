/*
 * @test
 * @summary Test sun.security.x509.CRLReasonCodeExtension constructor with int reason
 * @run main CRLReasonCodeExtensionWitupTest
 */

import sun.security.x509.CRLReasonCodeExtension;
import java.io.IOException;

public class CRLReasonCodeExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testValidReasonCode();
    }

    private static void testValidReasonCode() throws IOException {
        // Test with valid reason code 0 (should set extensionValue to null)
        CRLReasonCodeExtension ext0 = new CRLReasonCodeExtension(0);
        if (ext0.extensionValue != null) {
            throw new RuntimeException("extensionValue should be null for reason code 0");
        }

        // Test with a valid non-zero reason code (e.g., 1)
        CRLReasonCodeExtension ext1 = new CRLReasonCodeExtension(1);
        if (ext1.extensionValue == null || ext1.extensionValue.length == 0) {
            throw new RuntimeException("extensionValue should not be null or empty for non-zero reason code");
        }
    }
}
