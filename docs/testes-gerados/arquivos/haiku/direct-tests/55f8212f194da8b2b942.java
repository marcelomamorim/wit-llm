/*
 * @test
 * @summary IPAddressName.subtreeDepth() must throw UnsupportedOperationException with the documented message
 * @modules java.base/sun.security.x509
 * @run main IPAddressNameWitupTest
 */

import sun.security.x509.IPAddressName;

public class IPAddressNameWitupTest {

    private static final String EXPECTED_MESSAGE =
        "subtreeDepth() not defined for IPAddressName";

    public static void main(String[] args) throws Exception {
        IPAddressName ipv4 = new IPAddressName(new byte[] {10, 0, 0, 1});
        checkThrows(ipv4);

        IPAddressName ipv4WithMask =
            new IPAddressName(new byte[] {10, 9, 8, 0, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0});
        checkThrows(ipv4WithMask);

        IPAddressName ipv6 = new IPAddressName(new byte[16]);
        checkThrows(ipv6);
    }

    private static void checkThrows(IPAddressName name) {
        try {
            name.subtreeDepth();
        } catch (UnsupportedOperationException e) {
            if (!EXPECTED_MESSAGE.equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("subtreeDepth() did not throw UnsupportedOperationException");
    }
}
