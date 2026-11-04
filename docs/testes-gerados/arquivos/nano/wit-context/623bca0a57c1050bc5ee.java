/* @test    @summary Test delete method of PolicyConstraintsExtension for known attribute names and exception path.    @run main PolicyConstraintsExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.PolicyConstraintsExtension;

public class PolicyConstraintsExtensionWitupTest {

    public static void main(String[] args) {
        testDeleteRequire();
        testDeleteInhibit();
        testDeleteUnknown();
    }

    static void testDeleteRequire() {
        PolicyConstraintsExtension pce = new PolicyConstraintsExtension(1, 2);
        try {
            pce.delete("require");
            assert pce != null : "pce should not be null";
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in delete("require")", e);
        }
    }

    static void testDeleteInhibit() {
        PolicyConstraintsExtension pce = new PolicyConstraintsExtension(1, 2);
        try {
            pce.delete("inhibit");
            assert pce != null : "pce should not be null";
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in delete("inhibit")", e);
        }
    }

    static void testDeleteUnknown() {
        PolicyConstraintsExtension pce = new PolicyConstraintsExtension(1, 2);
        boolean thrown = false;
        try {
            pce.delete("unknown");
        } catch (IOException e) {
            thrown = true;
            assert e.getMessage().contains("Attribute name not recognized") : "Exception message mismatch";
        }
        assert thrown : "Expected IOException for unknown attribute name";
    }
}
