/*
 * @test
 * @summary Witup-generated tests for PolicyConstraintsExtension.get(String)
 * @modules java.base/sun.security.x509
 * @run main PolicyConstraintsExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.PolicyConstraintsExtension;

public class PolicyConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testReturnsConfiguredValues();
        testCaseInsensitiveNames();
        testOptionalValuesReturnMinusOne();
        testUnknownNameThrowsIOException();
        testEmptyNameThrowsIOException();
    }

    static void testReturnsConfiguredValues() throws Exception {
        PolicyConstraintsExtension ext = new PolicyConstraintsExtension(2, 3);
        check(ext.get("require") == 2, "require should be 2");
        check(ext.get("inhibit") == 3, "inhibit should be 3");
    }

    static void testCaseInsensitiveNames() throws Exception {
        PolicyConstraintsExtension ext = new PolicyConstraintsExtension(4, 5);
        check(ext.get("REQUIRE") == 4, "REQUIRE should map to require");
        check(ext.get("Inhibit") == 5, "Inhibit should map to inhibit");
    }

    static void testOptionalValuesReturnMinusOne() throws Exception {
        PolicyConstraintsExtension ext = new PolicyConstraintsExtension(-1, -1);
        check(ext.get("require") == -1, "optional require should be -1");
        check(ext.get("inhibit") == -1, "optional inhibit should be -1");
    }

    static void testUnknownNameThrowsIOException() throws Exception {
        PolicyConstraintsExtension ext = new PolicyConstraintsExtension(1, 1);
        try {
            ext.get("unknown");
            throw new RuntimeException("expected IOException for unknown name");
        } catch (IOException e) {
            // expected
        }
    }

    static void testEmptyNameThrowsIOException() throws Exception {
        PolicyConstraintsExtension ext = new PolicyConstraintsExtension(1, 1);
        try {
            ext.get("");
            throw new RuntimeException("expected IOException for empty name");
        } catch (IOException e) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
