/*
 * @test
 * @summary Test OCSPNonceExtension constructor with int length
 * @run main OCSPNonceExtensionWitupTest
 */

import sun.security.provider.certpath.OCSPNonceExtension;
import java.io.IOException;

public class OCSPNonceExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testValidLength();
        testInvalidLength();
    }

    private static void testValidLength() throws IOException {
        // Test with a valid positive length
        int length = 16;
        OCSPNonceExtension ext = new OCSPNonceExtension(length);
        if (ext == null) {
            throw new RuntimeException("OCSPNonceExtension constructor returned null");
        }
        // The extensionValue field is protected in Extension, but we cannot access it directly.
        // Instead, we check that no exception was thrown and object is created.
    }

    private static void testInvalidLength() {
        // Test with zero length, expect IllegalArgumentException
        try {
            new OCSPNonceExtension(0);
            throw new RuntimeException("Expected IllegalArgumentException for length=0");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for length=0", e);
        }

        // Test with negative length, expect IllegalArgumentException
        try {
            new OCSPNonceExtension(-1);
            throw new RuntimeException("Expected IllegalArgumentException for length=-1");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for length=-1", e);
        }
    }
}
