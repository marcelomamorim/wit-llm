/*
 * @test
 * @summary Test OCSPNonceExtension constructor with byte[] input for null and empty array handling
 * @run main OCSPNonceExtensionWitupTest
 */

import java.io.IOException;
import sun.security.provider.certpath.OCSPNonceExtension;

public class OCSPNonceExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testNullIncomingNonce();
        testEmptyIncomingNonce();
        testValidIncomingNonce();
    }

    private static void testNullIncomingNonce() {
        try {
            new OCSPNonceExtension((byte[]) null);
            throw new RuntimeException("Expected NullPointerException not thrown for null incomingNonce");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for null incomingNonce", e);
        }
    }

    private static void testEmptyIncomingNonce() {
        try {
            new OCSPNonceExtension(new byte[0]);
            throw new RuntimeException("Expected IllegalArgumentException not thrown for empty incomingNonce");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for empty incomingNonce", e);
        }
    }

    private static void testValidIncomingNonce() throws IOException {
        byte[] validNonce = new byte[] {1, 2, 3};
        OCSPNonceExtension ext = new OCSPNonceExtension(validNonce);
        if (ext == null) {
            throw new RuntimeException("OCSPNonceExtension constructor returned null for valid input");
        }
    }
}
