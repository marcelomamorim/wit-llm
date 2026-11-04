/*
 * @test
 * @summary Verifies CRLReasonCodeExtension.get(String) returns the reason for "reason" (case-insensitive) and throws IOException for unsupported names
 * @modules java.base/sun.security.x509
 * @run main CRLReasonCodeExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.CRLReasonCodeExtension;

public class CRLReasonCodeExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(1);

        check(ext.get("reason").intValue() == 1, "get(\"reason\") should return 1");
        check(ext.get("REASON").intValue() == 1, "get(\"REASON\") should be case-insensitive");
        check(ext.get("ReAsOn").intValue() == 1, "get(\"ReAsOn\") should be case-insensitive");

        CRLReasonCodeExtension critical = new CRLReasonCodeExtension(true, 3);
        check(critical.get(CRLReasonCodeExtension.REASON).intValue() == 3,
              "reason 3 should be returned for critical extension");

        CRLReasonCodeExtension zero = new CRLReasonCodeExtension(0);
        check(zero.get("reason").intValue() == 0, "reason 0 should be returned as 0");

        checkUnsupported(ext, "unknown");
        checkUnsupported(ext, "");
        checkUnsupported(ext, "reasons");
    }

    private static void checkUnsupported(CRLReasonCodeExtension ext, String name) {
        try {
            ext.get(name);
            throw new RuntimeException("IOException expected for name: " + name);
        } catch (IOException e) {
            // expected
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
