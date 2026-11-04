/*
 * @test
 * @summary Verifies PolicyConstraintsExtension.get(String) returns require/inhibit values and rejects unknown names
 * @modules java.base/sun.security.x509
 * @run main PolicyConstraintsExtensionWitupTest
 */

import java.io.IOException;

public class PolicyConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        PolicyConstraintsExtension ext = new PolicyConstraintsExtension(5, 7);
        check(ext.get("require"), 5);
        check(ext.get("inhibit"), 7);
        check(ext.get("REQUIRE"), 5);
        check(ext.get("Inhibit"), 7);

        PolicyConstraintsExtension unset = new PolicyConstraintsExtension(-1, 3);
        check(unset.get("require"), -1);
        check(unset.get("inhibit"), 3);

        PolicyConstraintsExtension both = new PolicyConstraintsExtension(-1, -1);
        check(both.get("require"), -1);
        check(both.get("inhibit"), -1);

        PolicyConstraintsExtension zero = new PolicyConstraintsExtension(Boolean.TRUE, 0, 9);
        check(zero.get("require"), 0);
        check(zero.get("inhibit"), 9);

        expectIOException(ext, "policy");
        expectIOException(ext, "");
        expectIOException(ext, "requireX");

        try {
            ext.get(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void expectIOException(PolicyConstraintsExtension ext, String name) {
        try {
            ext.get(name);
            throw new RuntimeException("Expected IOException for name: " + name);
        } catch (IOException e) {
            // expected
        }
    }

    private static void check(Integer actual, int expected) {
        if (actual == null || actual.intValue() != expected) {
            throw new RuntimeException("Expected " + expected + " but got " + actual);
        }
    }
}
