/*
 * @test
 * @summary Verifies the public OCSPNonceExtension(byte[]) constructor rejects null and empty nonces and accepts non-empty nonces
 * @modules java.base/sun.security.provider.certpath
 * @run main OCSPNonceExtensionWitupTest
 */

import sun.security.provider.certpath.OCSPNonceExtension;

public class OCSPNonceExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testNullNonceThrowsNullPointerException();
        testEmptyNonceThrowsIllegalArgumentException();
        testNonEmptyNonceIsAccepted();
        testSingleByteNonceIsAccepted();
    }

    private static void testNullNonceThrowsNullPointerException() throws Exception {
        try {
            new OCSPNonceExtension((byte[]) null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null nonce");
    }

    private static void testEmptyNonceThrowsIllegalArgumentException() throws Exception {
        try {
            new OCSPNonceExtension(new byte[0]);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for empty nonce");
    }

    private static void testNonEmptyNonceIsAccepted() throws Exception {
        byte[] nonce = new byte[] {1, 2, 3, 4, 5, 6, 7, 8};
        OCSPNonceExtension ext = new OCSPNonceExtension(nonce);
        if (ext == null) {
            throw new RuntimeException("Constructor returned null object");
        }
    }

    private static void testSingleByteNonceIsAccepted() throws Exception {
        OCSPNonceExtension ext = new OCSPNonceExtension(new byte[] {42});
        if (ext == null) {
            throw new RuntimeException("Constructor returned null object");
        }
    }
}
