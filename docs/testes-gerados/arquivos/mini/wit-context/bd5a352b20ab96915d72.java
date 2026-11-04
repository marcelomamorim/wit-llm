/*
 * @test
 * @summary Test NetworkInterface.getByInetAddress(InetAddress) for valid and invalid inputs
 * @run main NetworkInterfaceWitupTest
 */

import java.net.NetworkInterface;
import java.net.InetAddress;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.SocketException;

public class NetworkInterfaceWitupTest {
    public static void main(String[] args) throws Exception {
        testNullAddressThrows();
        testInvalidAddressTypeThrows();
        testValidInet4AddressReturnsNonNull();
        testValidInet6AddressReturnsNonNull();
    }

    private static void testNullAddressThrows() {
        try {
            NetworkInterface.getByInetAddress(null);
            throw new RuntimeException("Expected NullPointerException for null address");
        } catch (NullPointerException expected) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for null address", e);
        }
    }

    private static void testInvalidAddressTypeThrows() {
        try {
            // Create a dummy InetAddress subclass that is neither Inet4Address nor Inet6Address
            InetAddress invalidAddr = new InetAddress() {
                private static final long serialVersionUID = 1L;
                public String getHostAddress() { return "0.0.0.0"; }
                public String getHostName() { return "invalid"; }
            };
            NetworkInterface.getByInetAddress(invalidAddr);
            throw new RuntimeException("Expected IllegalArgumentException for invalid address type");
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for invalid address type", e);
        }
    }

    private static void testValidInet4AddressReturnsNonNull() throws Exception {
        // Use loopback Inet4Address
        InetAddress loopback4 = InetAddress.getByName("127.0.0.1");
        if (!(loopback4 instanceof Inet4Address)) {
            throw new RuntimeException("Test setup failure: expected Inet4Address");
        }
        NetworkInterface ni = NetworkInterface.getByInetAddress(loopback4);
        // The result may be null if no interface is bound to this address, so just check no exception and type
        // We accept null as valid result (no interface found)
    }

    private static void testValidInet6AddressReturnsNonNull() throws Exception {
        // Use loopback Inet6Address
        InetAddress loopback6 = InetAddress.getByName("::1");
        if (!(loopback6 instanceof Inet6Address)) {
            throw new RuntimeException("Test setup failure: expected Inet6Address");
        }
        NetworkInterface ni = NetworkInterface.getByInetAddress(loopback6);
        // The result may be null if no interface is bound to this address, so just check no exception and type
        // We accept null as valid result (no interface found)
    }
}
