/*
 * @test
 * @summary Checks the public int reason constructor of CRLReasonCodeExtension
 * @modules java.base/sun.security.x509
 * @run main CRLReasonCodeExtensionWitupTest
 */

import java.util.Arrays;
import sun.security.x509.CRLReasonCodeExtension;

public class CRLReasonCodeExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        CRLReasonCodeExtension zero = new CRLReasonCodeExtension(0);
        check(!zero.isCritical(), "reason 0 must be non-critical");
        check(zero.getExtensionValue() == null, "reason 0 must produce null value");

        CRLReasonCodeExtension ext = new CRLReasonCodeExtension(1);
        check(!ext.isCritical(), "int constructor must be non-critical");
        byte[] expected1 = {0x0A, 0x01, 0x01};
        check(Arrays.equals(expected1, ext.getExtensionValue()),
              "reason 1 must be DER enumerated 0x0A 0x01 0x01");

        CRLReasonCodeExtension crit = new CRLReasonCodeExtension(true, 4);
        check(crit.isCritical(), "boolean constructor must keep critical=true");
        byte[] expected4 = {0x0A, 0x01, 0x04};
        check(Arrays.equals(expected4, crit.getExtensionValue()),
              "reason 4 must be DER enumerated 0x0A 0x01 0x04");

        CRLReasonCodeExtension parsed = new CRLReasonCodeExtension(Boolean.TRUE, expected4);
        check(parsed.isCritical(), "parsed extension must be critical");
        check(Arrays.equals(expected4, parsed.getExtensionValue()),
              "parsed extension must preserve DER value");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
