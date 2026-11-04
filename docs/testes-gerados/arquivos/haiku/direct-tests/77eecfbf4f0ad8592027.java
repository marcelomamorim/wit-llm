/*
 * @test
 * @summary Checks observable behavior of ServerSocketChannelImpl.accept() through the public ServerSocketChannel API
 * @run main ServerSocketChannelImplWitupTest
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testNonBlockingAcceptReturnsNullWhenNoPendingConnection();
        testBlockingAcceptReturnsConnectedBlockingChannel();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNonBlockingAcceptReturnsNullWhenNoPendingConnection() throws IOException {
        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            ssc.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
            ssc.configureBlocking(false);
            SocketChannel sc = ssc.accept();
            check(sc == null, 'accept() on non-blocking channel with no pending connection should return null');
        }
    }

    static void testBlockingAcceptReturnsConnectedBlockingChannel() throws IOException {
        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            ssc.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
            InetSocketAddress serverLocal = (InetSocketAddress) ssc.getLocalAddress();

            try (SocketChannel client = SocketChannel.open(serverLocal)) {
                InetSocketAddress clientLocal = (InetSocketAddress) client.getLocalAddress();
                SocketChannel accepted = ssc.accept();
                check(accepted != null, 'accept() on blocking channel with pending connection should not return null');
                try (SocketChannel a = accepted) {
                    check(a.isOpen(), 'accepted channel should be open');
                    check(a.isConnected(), 'accepted channel should be connected');
                    check(a.isBlocking(), 'accepted channel should be in blocking mode');

                    InetSocketAddress remote = (InetSocketAddress) a.getRemoteAddress();
                    check(remote.getPort() == clientLocal.getPort(),
                          'remote port of accepted channel should match client local port');

                    InetSocketAddress acceptedLocal = (InetSocketAddress) a.getLocalAddress();
                    check(acceptedLocal.getPort() == serverLocal.getPort(),
                          'local port of accepted channel should match server port');
                }
            }
        }
    }
}
