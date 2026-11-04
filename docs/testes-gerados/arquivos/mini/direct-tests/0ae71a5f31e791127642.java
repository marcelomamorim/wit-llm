/*
 * @test
 * @summary Test sun.nio.ch.AsynchronousServerSocketChannelImpl.getLocalAddress()
 * @run main AsynchronousServerSocketChannelImplWitupTest
 */

import java.nio.channels.ClosedChannelException;
import java.net.SocketAddress;
import java.io.IOException;
import sun.nio.ch.AsynchronousServerSocketChannelImpl;
import sun.net.Net;
import java.net.InetSocketAddress;

public class AsynchronousServerSocketChannelImplWitupTest {

    // Minimal concrete subclass to test abstract class
    static class TestChannel extends AsynchronousServerSocketChannelImpl {
        TestChannel() {
            super(null);
        }
        @Override
        void implClose() throws IOException {
            // no-op
        }
        @Override
        public java.util.concurrent.Future<java.nio.channels.AsynchronousSocketChannel> implAccept(Object attachment, java.nio.channels.CompletionHandler<java.nio.channels.AsynchronousSocketChannel,Object> handler) {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        TestChannel channel = new TestChannel();

        // Test getLocalAddress when open and localAddress is null
        channel.localAddress = null;
        SocketAddress addr = channel.getLocalAddress();
        if (addr != null) {
            throw new RuntimeException("Expected null local address when none set");
        }

        // Test getLocalAddress when open and localAddress is set
        InetSocketAddress isa = new InetSocketAddress("127.0.0.1", 0);
        channel.localAddress = isa;
        SocketAddress returned = channel.getLocalAddress();
        if (!isa.equals(returned)) {
            throw new RuntimeException("Returned local address does not match set address");
        }

        // Test getLocalAddress when channel is closed - expect ClosedChannelException
        channel.close();
        try {
            channel.getLocalAddress();
            throw new RuntimeException("Expected ClosedChannelException when channel is closed");
        } catch (ClosedChannelException expected) {
            // expected
        }

        System.out.println("PASS");
    }
}
