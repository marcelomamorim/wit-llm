/* @test    @summary Test OCSPNonceExtension constructor with byte array input.    @run main OCSPNonceExtensionWitupTest */

import java.io.IOException;
import sun.security.provider.certpath.OCSPNonceExtension;

public class OCSPNonceExtensionWitupTest {

    public static void main(String[] args) throws IOException {
        testConstructorWithValidNonce();
        testConstructorWithNullNonce();
    }

    private static void testConstructorWithValidNonce() throws IOException {
        byte[] nonce = {1, 2, 3, 4, 5};
        OCSPNonceExtension ext = new OCSPNonceExtension(nonce);
        assert ext != null : "Extension should not be null";
    }

    private static void testConstructorWithNullNonce() {
        try {
            OCSPNonceExtension ext = new OCSPNonceExtension((byte[])null);
            assert ext != null : "Extension should be created even with null nonce";
        } catch (IOException e) {
            assert false : "IOException should not be thrown with null nonce";
        }
    }
}
