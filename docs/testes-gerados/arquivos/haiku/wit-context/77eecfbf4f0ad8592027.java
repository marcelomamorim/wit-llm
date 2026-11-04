/*
 * @test
 * @summary Behavioral checks for ServerSocketChannelImpl.accept() through the public ServerSocketChannel API
 * @run main ServerSocketChannelImplWitupTest
 */

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testNonBlockingAcceptWithoutPendingConnectionReturnsNull();
        testAcceptOnClosedChannelThrowsClosedChannelException();
        testAcceptReturnsPendingConnection();
    }

    static InetSocketAddress loopbackEphemeral() {
        return new InetSocketAddress(InetAddress.getLoopbackAddress(), 0);
    }

    static void testNonBlockingAcceptWithoutPendingConnectionReturnsNull() throws Exception {
        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            ssc.bind(loopbackEphemeral());
            ssc.configureBlocking(false);
            SocketChannel sc = ssc.accept();
            if (sc != null) {
                sc.close();
                throw new RuntimeException("accept() should return null when no connection is pending");
            }
        }
    }

    static void testAcceptOnClosedChannelThrowsClosedChannelException() throws Exception {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.close();
        try {
            ssc.accept();
            throw new RuntimeException("accept() on closed channel should throw ClosedChannelException");
        } catch (ClosedChannelException expected) {
            // expected
        }
    }

    static void testAcceptReturnsPendingConnection() throws Exception {
        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            ssc.bind(loopbackEphemeral());
            InetSocketAddress serverAddr = (InetSocketAddress) ssc.getLocalAddress();
            try (SocketChannel client = SocketChannel.open(serverAddr)) {
                int clientPort = ((InetSocketAddress) client.getLocalAddress()).getPort();
                SocketChannel accepted = ssc.accept();
                if (accepted == null) {
                    throw new RuntimeException("accept() returned null for a pending connection");
                }
                try {
                    if (!accepted.isOpen()) {
                        throw new RuntimeException("accepted channel should be open");
                    }
                    int remotePort = ((InetSocketAddress) accepted.getRemoteAddress()).getPort();
                    if (remotePort != clientPort) {
                        throw new RuntimeException("remote port mismatch: " + remotePort + " != " + clientPort);
                    }
                } finally {
                    accepted.close();
                }
            }
        }
    }
}
