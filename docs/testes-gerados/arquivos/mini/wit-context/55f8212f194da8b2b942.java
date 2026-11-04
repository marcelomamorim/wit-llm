/*
 * @test
 * @summary Test sun.security.x509.IPAddressName.subtreeDepth() throws UnsupportedOperationException
 * @run main IPAddressNameWitupTest
 */

import sun.security.x509.IPAddressName;
import sun.security.util.DerValue;
import java.io.IOException;

public class IPAddressNameWitupTest {
    public static void main(String[] args) throws Exception {
        testSubtreeDepthThrows();
    }

    private static void testSubtreeDepthThrows() throws IOException {
        // Create a valid IPv4 address byte array (4 bytes)
        byte[] ipv4Address = new byte[] {(byte)192, (byte)168, 1, 1};
        IPAddressName ipName = new IPAddressName(ipv4Address);

        boolean thrown = false;
        try {
            ipName.subtreeDepth();
        } catch (UnsupportedOperationException e) {
            thrown = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("subtreeDepth() not defined")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        }
    }
}
