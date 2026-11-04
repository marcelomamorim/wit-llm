/*
 * @test
 * @summary Test DatagramSocket.connect(SocketAddress) method for valid and invalid inputs
 * @run main DatagramSocketWitupTest
 */

import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class DatagramSocketWitupTest {
    public static void main(String[] args) throws Exception {
        testConnectNullAddress();
        testConnectUnsupportedAddress();
        testConnectUnresolvedAddress();
        testConnectValidAddress();
    }

    static void testConnectNullAddress() {
        DatagramSocket ds = new DatagramSocket();
        try {
            ds.connect(null);
            throw new RuntimeException("Expected IllegalArgumentException for null address");
        } catch (IllegalArgumentException e) {
            if (!"Address can't be null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for null address", e);
            }
        } finally {
            ds.close();
        }
    }

    static void testConnectUnsupportedAddress() {
        DatagramSocket ds = new DatagramSocket();
        try {
            ds.connect(new SocketAddress() {}); // anonymous subclass, unsupported
            throw new RuntimeException("Expected IllegalArgumentException for unsupported address type");
        } catch (IllegalArgumentException e) {
            if (!"Unsupported address type".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for unsupported address", e);
            }
        } finally {
            ds.close();
        }
    }

    static void testConnectUnresolvedAddress() {
        DatagramSocket ds = new DatagramSocket();
        InetSocketAddress unresolved = InetSocketAddress.createUnresolved("unknown.host", 12345);
        try {
            ds.connect(unresolved);
            throw new RuntimeException("Expected SocketException for unresolved address");
        } catch (SocketException e) {
            if (!"Unresolved address".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for unresolved address", e);
            }
        } finally {
            ds.close();
        }
    }

    static void testConnectValidAddress() throws Exception {
        DatagramSocket ds = new DatagramSocket();
        InetSocketAddress resolved = new InetSocketAddress("127.0.0.1", 0);
        ds.connect(resolved);
        // No exception means success
        ds.close();
    }
}
