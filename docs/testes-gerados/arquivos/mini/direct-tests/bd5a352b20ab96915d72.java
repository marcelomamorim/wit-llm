/*
 * @test
 * @summary Test NetworkInterface.getByInetAddress with valid and invalid inputs
 * @run main NetworkInterfaceWitupTest
 */

import java.net.InetAddress;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.NetworkInterface;
import java.net.SocketException;

public class NetworkInterfaceWitupTest {
    public static void main(String[] args) throws Exception {
        testNullAddress();
        testInvalidAddressType();
        testValidInet4Address();
        testValidInet6Address();
    }

    private static void testNullAddress() {
        try {
            NetworkInterface.getByInetAddress(null);
            throw new RuntimeException("Expected NullPointerException for null address");
        } catch (NullPointerException expected) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for null address", e);
        }
    }

    private static void testInvalidAddressType() throws Exception {
        // Create a subclass of InetAddress that is neither Inet4Address nor Inet6Address
        InetAddress invalidAddr = new InetAddress() {
            private static final long serialVersionUID = 1L;
            public String getHostAddress() { return "0.0.0.0"; }
            public String getHostName() { return "invalid"; }
        };

        try {
            NetworkInterface.getByInetAddress(invalidAddr);
            throw new RuntimeException("Expected IllegalArgumentException for invalid address type");
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for invalid address type", e);
        }
    }

    private static void testValidInet4Address() throws Exception {
        InetAddress loopback4 = InetAddress.getByName("127.0.0.1");
        if (!(loopback4 instanceof Inet4Address)) {
            throw new RuntimeException("Test setup failure: expected Inet4Address");
        }
        NetworkInterface ni = NetworkInterface.getByInetAddress(loopback4);
        if (ni == null) {
            throw new RuntimeException("Expected non-null NetworkInterface for 127.0.0.1");
        }
        String name = ni.getName();
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("NetworkInterface name should not be null or empty");
        }
    }

    private static void testValidInet6Address() throws Exception {
        InetAddress loopback6 = InetAddress.getByName("::1");
        if (!(loopback6 instanceof Inet6Address)) {
            throw new RuntimeException("Test setup failure: expected Inet6Address");
        }
        NetworkInterface ni = NetworkInterface.getByInetAddress(loopback6);
        if (ni == null) {
            throw new RuntimeException("Expected non-null NetworkInterface for ::1");
        }
        String name = ni.getName();
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("NetworkInterface name should not be null or empty");
        }
    }
}
