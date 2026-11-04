/*
 * @test
 * @summary Test OCSPNonceExtension constructor with byte[] nonce input
 * @run main OCSPNonceExtensionWitupTest
 */

import java.io.IOException;
import sun.security.provider.certpath.OCSPNonceExtension;

public class OCSPNonceExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testValidNonce();
        testNullNonce();
        testEmptyNonce();
    }

    private static void testValidNonce() throws IOException {
        byte[] nonce = new byte[] {1, 2, 3, 4, 5};
        OCSPNonceExtension ext = new OCSPNonceExtension(nonce);
        if (ext == null) {
            throw new RuntimeException("OCSPNonceExtension instance is null");
        }
    }

    private static void testNullNonce() {
        try {
            new OCSPNonceExtension((byte[]) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testEmptyNonce() throws IOException {
        byte[] empty = new byte[0];
        OCSPNonceExtension ext = new OCSPNonceExtension(empty);
        if (ext == null) {
            throw new RuntimeException("OCSPNonceExtension instance is null for empty nonce");
        }
    }
}
