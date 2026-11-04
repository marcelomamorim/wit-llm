/*
 * @test
 * @summary Test IPAddressName.subtreeDepth throws UnsupportedOperationException
 * @run main IPAddressNameWitupTest
 */

import sun.security.x509.IPAddressName;

public class IPAddressNameWitupTest {
    public static void main(String[] args) throws Exception {
        IPAddressName ipName = new IPAddressName(new byte[] {127, 0, 0, 1});
        try {
            ipName.subtreeDepth();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("subtreeDepth() not defined for IPAddressName")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
