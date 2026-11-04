/*
 * @test
 * @summary DatagramSocket.connect validates null, unsupported, unresolved and valid InetSocketAddress arguments
 * @run main DatagramSocketWitupTest
 */

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

public class DatagramSocketWitupTest {

    static class OtherAddress extends SocketAddress {
        private static final long serialVersionUID = 1L;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIllegalArgument(DatagramSocket s, SocketAddress addr, String label) {
        try {
            s.connect(addr);
            throw new RuntimeException(label + ": expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        } catch (SocketException e) {
            throw new RuntimeException(label + ": unexpected SocketException", e);
        }
    }

    static void expectSocketException(DatagramSocket s, SocketAddress addr, String label) {
        try {
            s.connect(addr);
            throw new RuntimeException(label + ": expected SocketException");
        } catch (SocketException expected) {
            // expected
        }
    }

    public static void main(String[] args) throws Exception {
        try (DatagramSocket s = new DatagramSocket()) {
            check(!s.isConnected(), "socket should start unconnected");

            expectIllegalArgument(s, null, "null address");
            expectIllegalArgument(s, new OtherAddress(), "unsupported address type");
            check(!s.isConnected(), "failed connect must not connect the socket");

            InetSocketAddress unresolved =
                    InetSocketAddress.createUnresolved("unresolved.invalid", 9999);
            expectSocketException(s, unresolved, "unresolved address");
            check(!s.isConnected(), "unresolved connect must not connect the socket");

            InetAddress loopback = InetAddress.getLoopbackAddress();
            InetSocketAddress target = new InetSocketAddress(loopback, 9999);
            s.connect(target);
            check(s.isConnected(), "socket should be connected after valid connect");
            check(s.getPort() == 9999, "unexpected connected port: " + s.getPort());
            check(loopback.equals(s.getInetAddress()), "unexpected connected address");
        }
    }
}
