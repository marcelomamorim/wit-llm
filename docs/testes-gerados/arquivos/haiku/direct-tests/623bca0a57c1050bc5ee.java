/*
 * @test
 * @summary Verify PolicyConstraintsExtension.delete accepts REQUIRE/INHIBIT case-insensitively and throws IOException for unknown names
 * @modules java.base/sun.security.x509
 * @run main PolicyConstraintsExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.PolicyConstraintsExtension;

public class PolicyConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteUnknownNameThrowsIOException();
        testDeleteRequireAndInhibitDoNotThrow();
        testDeleteIsCaseInsensitive();
        testDeleteTwiceIsIdempotent();
        testDeleteOnCriticalExtension();
    }

    static void testDeleteUnknownNameThrowsIOException() throws Exception {
        PolicyConstraintsExtension ext = new PolicyConstraintsExtension(1, 2);
        boolean thrown = false;
        try {
            ext.delete("unknown");
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for unrecognized attribute name");
        }
    }

    static void testDeleteRequireAndInhibitDoNotThrow() throws Exception {
        PolicyConstraintsExtension ext = new PolicyConstraintsExtension(1, 2);
        ext.delete(PolicyConstraintsExtension.REQUIRE);
        ext.delete(PolicyConstraintsExtension.INHIBIT);
    }

    static void testDeleteIsCaseInsensitive() throws Exception {
        PolicyConstraintsExtension ext = new PolicyConstraintsExtension(3, 4);
        ext.delete("REQUIRE");
        ext.delete("Inhibit");

        PolicyConstraintsExtension ext2 = new PolicyConstraintsExtension(3, 4);
        ext2.delete("require");
        ext2.delete("iNhIbIt");
    }

    static void testDeleteTwiceIsIdempotent() throws Exception {
        PolicyConstraintsExtension ext = new PolicyConstraintsExtension(0, 0);
        ext.delete(PolicyConstraintsExtension.REQUIRE);
        ext.delete(PolicyConstraintsExtension.REQUIRE);
        ext.delete(PolicyConstraintsExtension.INHIBIT);
        ext.delete(PolicyConstraintsExtension.INHIBIT);
    }

    static void testDeleteOnCriticalExtension() throws Exception {
        PolicyConstraintsExtension ext = new PolicyConstraintsExtension(Boolean.TRUE, 1, 1);
        ext.delete(PolicyConstraintsExtension.REQUIRE);
        ext.delete(PolicyConstraintsExtension.INHIBIT);

        boolean thrown = false;
        try {
            ext.delete("bogus");
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for unrecognized attribute name on critical extension");
        }
    }
}
