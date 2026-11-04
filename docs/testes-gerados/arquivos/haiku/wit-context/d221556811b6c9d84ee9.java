/*
 * @test
 * @summary Verify that the OCSPNonceExtension(int length) constructor rejects non-positive lengths and accepts positive lengths
 * @modules java.base/sun.security.provider.certpath
 * @run main OCSPNonceExtensionWitupTest
 */

import java.io.IOException;
import sun.security.provider.certpath.OCSPNonceExtension;

public class OCSPNonceExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testZeroLengthRejected();
        testNegativeLengthRejected();
        testPositiveLengthAccepted();
    }

    static void testZeroLengthRejected() throws Exception {
        expectIllegalArgument(0);
    }

    static void testNegativeLengthRejected() throws Exception {
        expectIllegalArgument(-1);
    }

    static void expectIllegalArgument(int length) throws Exception {
        try {
            new OCSPNonceExtension(length);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for length " + length);
    }

    static void testPositiveLengthAccepted() throws IOException {
        OCSPNonceExtension ext = new OCSPNonceExtension(16);
        if (ext == null) {
            throw new RuntimeException("Extension must not be null");
        }
    }
}
