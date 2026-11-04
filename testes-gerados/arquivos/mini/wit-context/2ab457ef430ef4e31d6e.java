/*
 * @test
 * @summary Test java.net.DatagramSocket.connect(SocketAddress) for exception and normal behavior
 * @run main DatagramSocketWitupTest
 */

import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.net.SocketException;
import java.lang.IllegalArgumentException;

public class DatagramSocketWitupTest {
    public static void main(String[] args) throws Exception {
        testConnectNullAddress();
        testConnectUnsupportedAddressType();
        testConnectUnresolvedAddress();
        testConnectValidAddress();
    }

    private static void testConnectNullAddress() {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
            try {
                ds.connect(null);
                throw new RuntimeException("Expected IllegalArgumentException for null address");
            } catch (IllegalArgumentException iae) {
                // expected
            } catch (Exception e) {
                throw new RuntimeException("Unexpected exception for null address", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception during socket creation", e);
        } finally {
            if (ds != null) ds.close();
        }
    }

    private static void testConnectUnsupportedAddressType() {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
            SocketAddress unsupported = new SocketAddress() {};
            try {
                ds.connect(unsupported);
                throw new RuntimeException("Expected IllegalArgumentException for unsupported address type");
            } catch (IllegalArgumentException iae) {
                // expected
            } catch (Exception e) {
                throw new RuntimeException("Unexpected exception for unsupported address type", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception during socket creation", e);
        } finally {
            if (ds != null) ds.close();
        }
    }

    private static void testConnectUnresolvedAddress() {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
            InetSocketAddress unresolved = InetSocketAddress.createUnresolved("unresolved.host", 12345);
            try {
                ds.connect(unresolved);
                throw new RuntimeException("Expected SocketException for unresolved address");
            } catch (SocketException se) {
                // expected
            } catch (Exception e) {
                throw new RuntimeException("Unexpected exception for unresolved address", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception during socket creation", e);
        } finally {
            if (ds != null) ds.close();
        }
    }

    private static void testConnectValidAddress() throws Exception {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
            InetAddress loopback = InetAddress.getByName("127.0.0.1");
            InetSocketAddress valid = new InetSocketAddress(loopback, 0);
            ds.connect(valid);
            // No exception expected
        } finally {
            if (ds != null) ds.close();
        }
    }
}
