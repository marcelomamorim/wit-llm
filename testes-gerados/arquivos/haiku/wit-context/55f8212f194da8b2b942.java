/*
 * @test
 * @summary Verify IPAddressName.subtreeDepth() always throws UnsupportedOperationException
 * @modules java.base/sun.security.x509
 * @run main IPAddressNameWitupTest
 */

import sun.security.x509.IPAddressName;

public class IPAddressNameWitupTest {

    public static void main(String[] args) throws Exception {
        IPAddressName ipv4 = new IPAddressName(new byte[] {10, 9, 8, 1});
        checkSubtreeDepthThrowsUOE(ipv4);

        IPAddressName ipv6 = new IPAddressName(new byte[16]);
        checkSubtreeDepthThrowsUOE(ipv6);
    }

    private static void checkSubtreeDepthThrowsUOE(IPAddressName name) {
        try {
            int depth = name.subtreeDepth();
            throw new RuntimeException("Expected UnsupportedOperationException, got depth " + depth);
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
