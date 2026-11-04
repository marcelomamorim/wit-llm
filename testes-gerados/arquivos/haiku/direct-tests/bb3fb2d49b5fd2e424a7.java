/*
 * @test
 * @summary Verifies public getRemoteAddress behavior of AsynchronousSocketChannel before connect and after close
 * @run main AsynchronousSocketChannelImplWitupTest
 */

import java.net.SocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;

public class AsynchronousSocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        AsynchronousSocketChannel ch = AsynchronousSocketChannel.open();
        try {
            SocketAddress before = ch.getRemoteAddress();
            if (before != null) {
                throw new RuntimeException("Expected null remote address before connect, got: " + before);
            }

            ch.close();

            if (ch.isOpen()) {
                throw new RuntimeException("Channel should be closed");
            }

            boolean thrown = false;
            try {
                ch.getRemoteAddress();
            } catch (ClosedChannelException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected ClosedChannelException from getRemoteAddress after close");
            }
        } finally {
            ch.close();
        }
    }
}
