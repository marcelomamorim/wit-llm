/*
 * @test
 * @summary Verifies observable behavior of SocketChannelImpl.connect through the public SocketChannel API: successful blocking connect to a listening loopback server, and IOException with the channel closed when no server listens.
 * @run main SocketChannelImplWitupTest
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SocketChannel;

public class SocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testConnectToListeningServerReturnsTrue();
        testConnectToClosedPortThrowsIOExceptionAndClosesChannel();
    }

    static void testConnectToListeningServerReturnsTrue() throws IOException {
        try (ServerSocket server = new ServerSocket(0, 50, InetAddress.getLoopbackAddress());
             SocketChannel sc = SocketChannel.open()) {
            InetSocketAddress isa =
                new InetSocketAddress(InetAddress.getLoopbackAddress(), server.getLocalPort());
            boolean connected = sc.connect(isa);
            if (!connected) {
                throw new RuntimeException("connect returned false in blocking mode");
            }
            if (!sc.isConnected()) {
                throw new RuntimeException("channel not connected after connect returned true");
            }
        }
    }

    static void testConnectToClosedPortThrowsIOExceptionAndClosesChannel() throws IOException {
        InetAddress lo = InetAddress.getLoopbackAddress();
        int port;
        try (ServerSocket tmp = new ServerSocket(0, 50, lo)) {
            port = tmp.getLocalPort();
        }
        SocketChannel sc = SocketChannel.open();
        try {
            sc.connect(new InetSocketAddress(lo, port));
            throw new RuntimeException("expected IOException connecting to closed port");
        } catch (IOException expected) {
            // expected: connect failed
        }
        if (sc.isOpen()) {
            throw new RuntimeException("channel should be closed after failed connect");
        }
    }
}
