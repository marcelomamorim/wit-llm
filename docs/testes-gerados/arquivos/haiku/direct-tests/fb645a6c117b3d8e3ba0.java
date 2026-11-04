/*
 * @test
 * @summary Verify InhibitAnyPolicyExtension.get(String) returns the skip-certs value or throws IOException
 * @modules java.base/sun.security.x509
 * @run main InhibitAnyPolicyExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.InhibitAnyPolicyExtension;

public class InhibitAnyPolicyExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        InhibitAnyPolicyExtension five = new InhibitAnyPolicyExtension(5);
        check(five.get(InhibitAnyPolicyExtension.SKIP_CERTS).intValue() == 5,
              "skip_certs should be 5");
        check(five.get("SKIP_CERTS").intValue() == 5,
              "attribute name lookup should be case-insensitive");
        check(new InhibitAnyPolicyExtension(0).get("skip_certs").intValue() == 0,
              "skip_certs should be 0");
        check(new InhibitAnyPolicyExtension(-1).get("skip_certs").intValue() == Integer.MAX_VALUE,
              "-1 should map to unlimited depth (Integer.MAX_VALUE)");

        try {
            new InhibitAnyPolicyExtension(-2);
            throw new RuntimeException("constructor should reject skipCerts < -1");
        } catch (IOException expected) {
        }

        try {
            five.get(InhibitAnyPolicyExtension.NAME);
            throw new RuntimeException("get(NAME) should throw IOException");
        } catch (IOException expected) {
        }

        try {
            five.get("unknown");
            throw new RuntimeException("unknown attribute should throw IOException");
        } catch (IOException expected) {
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
