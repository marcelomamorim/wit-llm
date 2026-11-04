/* @test    @summary Test NetworkInterface.getByInetAddress with valid and invalid inputs    @run main NetworkInterfaceWitupTest */

import java.net.InetAddress;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.SocketException;

public class NetworkInterfaceWitupTest {

    public static void main(String[] args) {
        testGetByInetAddressWithNull();
        testGetByInetAddressWithInvalidType();
        testGetByInetAddressWithValidAddress();
    }

    static void testGetByInetAddressWithNull() {
        try {
            NetworkInterface.getByInetAddress(null);
            System.out.println("FAIL: Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testGetByInetAddressWithInvalidType() {
        try {
            // Creating a dummy InetAddress subclass to simulate invalid type
            InetAddress invalidAddress = new InetAddress() {
                @Override
                public String getHostAddress() { return "invalid"; }
                @Override
                public String getHostName() { return "invalid"; }
                @Override
                public boolean isAnyLocalAddress() { return false; }
                @Override
                public boolean isLoopbackAddress() { return false; }
                @Override
                public boolean isLinkLocalAddress() { return false; }
                @Override
                public boolean isSiteLocalAddress() { return false; }
                @Override
                public boolean isMulticastAddress() { return false; }
                @Override
                public boolean isReachable(int timeout) { return false; }
                @Override
                public boolean isReachable(java.net.NetworkInterface netif, int ttl, int timeout) { return false; }
                @Override
                public byte[] getAddress() { return new byte[0]; }
            };
            NetworkInterface.getByInetAddress(invalidAddress);
            System.out.println("FAIL: Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testGetByInetAddressWithValidAddress() {
        try {
            // Use localhost address for IPv4 or IPv6 depending on environment
            InetAddress addr = InetAddress.getByName("127.0.0.1");
            NetworkInterface ni = NetworkInterface.getByInetAddress(addr);
            if (ni != null && ni.getName() != null) {
                System.out.println("PASS: getByInetAddress returned NetworkInterface with name: " + ni.getName());
            } else {
                System.out.println("FAIL: getByInetAddress returned null or invalid NetworkInterface");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
