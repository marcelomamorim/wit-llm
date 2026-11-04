/*
 * @test
 * @summary Verify getRemoteAddress on an unconnected and a closed asynchronous socket channel
 * @run main AsynchronousSocketChannelImplWitupTest
 */

import java.net.SocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;

public class AsynchronousSocketChannelImplWitupTest {
    public static void main(String[] args) throws Exception {
        AsynchronousSocketChannel ch = AsynchronousSocketChannel.open();
        SocketAddress remote = ch.getRemoteAddress();
        if (remote != null) {
            throw new RuntimeException("expected null remote address for unconnected channel: " + remote);
        }
        ch.close();
        if (ch.isOpen()) {
            throw new RuntimeException("channel should be closed");
        }
        try {
            ch.getRemoteAddress();
            throw new RuntimeException("expected ClosedChannelException");
        } catch (ClosedChannelException expected) {
            // expected behavior
        }
    }
}
