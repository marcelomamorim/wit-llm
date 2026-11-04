/*
 * @test
 * @summary Witup-generated checks for DatagramSocket.connect(SocketAddress)
 * @run main DatagramSocketWitupTest
 */

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

public class DatagramSocketWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static final class OtherAddress extends SocketAddress {
    }

    public static void main(String[] args) throws Exception {
        try (DatagramSocket s = new DatagramSocket()) {
            try {
                s.connect(null);
                throw new RuntimeException("expected IllegalArgumentException for null address");
            } catch (IllegalArgumentException expected) {
            }

            try {
                s.connect(new OtherAddress());
                throw new RuntimeException("expected IllegalArgumentException for unsupported address type");
            } catch (IllegalArgumentException expected) {
            }

            InetSocketAddress unresolved = InetSocketAddress.createUnresolved("unresolved.invalid", 9999);
            try {
                s.connect(unresolved);
                throw new RuntimeException("expected SocketException for unresolved address");
            } catch (SocketException expected) {
            }

            InetAddress loopback = InetAddress.getLoopbackAddress();
            InetSocketAddress dst = new InetSocketAddress(loopback, 9);
            s.connect(dst);
            check(s.isConnected(), "socket should be connected after connect()");
            check(s.getPort() == 9, "unexpected remote port: " + s.getPort());
            check(loopback.equals(s.getInetAddress()), "unexpected remote address: " + s.getInetAddress());
        }
    }
}
