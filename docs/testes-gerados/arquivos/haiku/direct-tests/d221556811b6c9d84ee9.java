/*
 * @test
 * @summary Verify OCSPNonceExtension(int length) accepts positive lengths and rejects non-positive lengths
 * @modules java.base/sun.security.provider.certpath
 * @run main OCSPNonceExtensionWitupTest
 */

import sun.security.provider.certpath.OCSPNonceExtension;

public class OCSPNonceExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        OCSPNonceExtension ext = new OCSPNonceExtension(16);
        if (ext == null) {
            throw new RuntimeException("Extension with positive length must not be null");
        }

        OCSPNonceExtension minExt = new OCSPNonceExtension(1);
        if (minExt == null) {
            throw new RuntimeException("Extension with length 1 must not be null");
        }

        expectIllegalArgument(0);
        expectIllegalArgument(-1);
        expectIllegalArgument(Integer.MIN_VALUE);
    }

    private static void expectIllegalArgument(int length) throws Exception {
        try {
            new OCSPNonceExtension(length);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for length " + length);
    }
}
