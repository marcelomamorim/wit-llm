/* @test    @summary Test connect method of DatagramSocket for null address and valid address    @run main DatagramSocketWitupTest */

import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class DatagramSocketWitupTest {

    public static void main(String[] args) throws Exception {
        testConnectWithNullAddress();
        testConnectWithUnresolvedAddress();
        testConnectWithValidAddress();
    }

    private static void testConnectWithNullAddress() {
        DatagramSocket ds = new DatagramSocket();
        try {
            ds.connect((SocketAddress) null);
            System.err.println("Failed: Expected IllegalArgumentException for null address");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: connect with null address throws IllegalArgumentException");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for null address: " + e);
        }
    }

    private static void testConnectWithUnresolvedAddress() {
        DatagramSocket ds = new DatagramSocket();
        InetSocketAddress unresolved = InetSocketAddress.createUnresolved("invalid.host", 12345);
        try {
            ds.connect(unresolved);
            System.err.println("Failed: Expected SocketException for unresolved address");
        } catch (SocketException e) {
            System.out.println("Passed: connect with unresolved address throws SocketException");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for unresolved address: " + e);
        }
    }

    private static void testConnectWithValidAddress() {
        DatagramSocket ds = new DatagramSocket();
        InetSocketAddress validAddress = new InetSocketAddress("localhost", 9999);
        try {
            ds.connect(validAddress);
            System.out.println("Passed: connect with valid address succeeded");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for valid address: " + e);
        }
    }
}
