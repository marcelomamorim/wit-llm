/*
 * @test
 * @summary Verify NetworkInterface.getByInetAddress null handling and accepted address types
 * @run main NetworkInterfaceWitupTest
 */

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

public class NetworkInterfaceWitupTest {

    public static void main(String[] args) throws Exception {
        testNullThrowsNullPointerException();
        testIPv4LoopbackDoesNotRejectAddressType();
        testIPv6LoopbackDoesNotRejectAddressType();
    }

    static void testNullThrowsNullPointerException() throws Exception {
        boolean thrown = false;
        try {
            NetworkInterface.getByInetAddress(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null address");
        }
    }

    static void testIPv4LoopbackDoesNotRejectAddressType() throws Exception {
        InetAddress addr = InetAddress.getByAddress(new byte[] {127, 0, 0, 1});
        if (!(addr instanceof java.net.Inet4Address)) {
            throw new RuntimeException("Expected Inet4Address");
        }
        try {
            NetworkInterface ni = NetworkInterface.getByInetAddress(addr);
            if (ni != null && ni.getName() == null) {
                throw new RuntimeException("Returned interface has null name");
            }
        } catch (SocketException e) {
            // An environment-dependent SocketException is an acceptable outcome.
        }
    }

    static void testIPv6LoopbackDoesNotRejectAddressType() throws Exception {
        byte[] loopback = new byte[16];
        loopback[15] = 1;
        InetAddress addr = InetAddress.getByAddress(loopback);
        if (!(addr instanceof java.net.Inet6Address)) {
            throw new RuntimeException("Expected Inet6Address");
        }
        try {
            NetworkInterface ni = NetworkInterface.getByInetAddress(addr);
            if (ni != null && ni.getName() == null) {
                throw new RuntimeException("Returned interface has null name");
            }
        } catch (SocketException e) {
            // An environment-dependent SocketException is an acceptable outcome.
        }
    }
}
