/*
 * @test
 * @summary Verifies getLocalAddress behavior of AsynchronousServerSocketChannelImpl through the public AsynchronousServerSocketChannel API
 * @run main AsynchronousServerSocketChannelImplWitupTest
 */

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.ClosedChannelException;

public class AsynchronousServerSocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        AsynchronousServerSocketChannel ch = AsynchronousServerSocketChannel.open(null);
        try {
            ch.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
            SocketAddress sa = ch.getLocalAddress();
            if (!(sa instanceof InetSocketAddress)) {
                throw new RuntimeException("Expected InetSocketAddress but got: " + sa);
            }
            int port = ((InetSocketAddress) sa).getPort();
            if (port <= 0) {
                throw new RuntimeException("Expected bound port > 0 but got: " + port);
            }
        } finally {
            ch.close();
        }

        if (ch.isOpen()) {
            throw new RuntimeException("Channel should be closed");
        }

        boolean thrown = false;
        try {
            ch.getLocalAddress();
        } catch (ClosedChannelException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected ClosedChannelException from getLocalAddress on closed channel");
        }
    }
}
