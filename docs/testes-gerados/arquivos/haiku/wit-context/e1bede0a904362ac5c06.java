/*
 * @test
 * @summary Verify DatagramChannel.disconnect() on unconnected, connected and closed channels
 * @run main DatagramChannelImplWitupTest
 */

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

public class DatagramChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testDisconnectUnconnected();
        testDisconnectConnected();
        testDisconnectClosed();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testDisconnectUnconnected() throws Exception {
        try (DatagramChannel dc = DatagramChannel.open()) {
            check(dc.disconnect() == dc, "disconnect on unconnected channel must return this");
            check(!dc.isConnected(), "channel must not be connected");
            check(dc.disconnect() == dc, "repeated disconnect must return this");
            check(!dc.isConnected(), "channel must remain unconnected");
        }
    }

    static void testDisconnectConnected() throws Exception {
        try (DatagramChannel dc = DatagramChannel.open();
             DatagramChannel peer = DatagramChannel.open()) {
            peer.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
            dc.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
            dc.connect(peer.getLocalAddress());
            check(dc.isConnected(), "channel must be connected before disconnect");
            check(dc.disconnect() == dc, "disconnect on connected channel must return this");
            check(!dc.isConnected(), "channel must not be connected after disconnect");
            check(dc.getRemoteAddress() == null, "remote address must be null after disconnect");
            check(dc.getLocalAddress() != null, "local address must remain available after disconnect");
        }
    }

    static void testDisconnectClosed() throws Exception {
        DatagramChannel dc = DatagramChannel.open();
        dc.close();
        check(dc.disconnect() == dc, "disconnect on closed channel must return this without throwing");
    }
}
