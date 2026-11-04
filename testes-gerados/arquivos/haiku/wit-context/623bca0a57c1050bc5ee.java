/*
 * @test
 * @summary Verifies PolicyConstraintsExtension.delete accepts require/inhibit case-insensitively, rejects unknown names with IOException, and updates the encoded extension.
 * @modules java.base/sun.security.x509
 * @run main PolicyConstraintsExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.PolicyConstraintsExtension;

public class PolicyConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testUnknownNameThrowsAndKeepsState();
        testDeleteRequireMatchesRequireOnlyEncoding();
        testDeleteInhibitCaseInsensitiveMatchesInhibitOnlyEncoding();
        testDeleteBothAttributesDoesNotThrow();
    }

    static byte[] enc(PolicyConstraintsExtension e) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        e.encode(bos);
        return bos.toByteArray();
    }

    static boolean sameBytes(byte[] a, byte[] b) {
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    static void testUnknownNameThrowsAndKeepsState() throws Exception {
        String[] bad = {"critical", "foo", ""};
        for (String n : bad) {
            PolicyConstraintsExtension e = new PolicyConstraintsExtension(1, 2);
            byte[] before = enc(e);
            boolean threw = false;
            try {
                e.delete(n);
            } catch (IOException ex) {
                threw = true;
            }
            if (!threw) {
                throw new RuntimeException("Expected IOException for name: " + n);
            }
            if (!sameBytes(before, enc(e))) {
                throw new RuntimeException("State changed after rejected name: " + n);
            }
        }
    }

    static void testDeleteRequireMatchesRequireOnlyEncoding() throws Exception {
        PolicyConstraintsExtension e = new PolicyConstraintsExtension(1, 2);
        e.delete("REQUIRE");
        PolicyConstraintsExtension expected = new PolicyConstraintsExtension(-1, 2);
        if (!sameBytes(enc(expected), enc(e))) {
            throw new RuntimeException("delete(\"REQUIRE\") did not clear require");
        }
    }

    static void testDeleteInhibitCaseInsensitiveMatchesInhibitOnlyEncoding() throws Exception {
        PolicyConstraintsExtension e = new PolicyConstraintsExtension(1, 2);
        e.delete("Inhibit");
        PolicyConstraintsExtension expected = new PolicyConstraintsExtension(1, -1);
        if (!sameBytes(enc(expected), enc(e))) {
            throw new RuntimeException("delete(\"Inhibit\") did not clear inhibit");
        }
    }

    static void testDeleteBothAttributesDoesNotThrow() throws Exception {
        PolicyConstraintsExtension e = new PolicyConstraintsExtension(1, 2);
        e.delete("require");
        e.delete("inhibit");
    }
}
