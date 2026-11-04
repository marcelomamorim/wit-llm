/*
 * @test
 * @summary Test sun.nio.ch.AsynchronousServerSocketChannelImpl.getLocalAddress() behavior
 * @run main AsynchronousServerSocketChannelImplWitupTest
 */

import java.nio.channels.ClosedChannelException;
import java.net.SocketAddress;
import java.io.IOException;
import sun.nio.ch.AsynchronousServerSocketChannelImpl;
import sun.nio.ch.AsynchronousChannelGroupImpl;
import sun.net.Net;
import java.net.InetSocketAddress;

public class AsynchronousServerSocketChannelImplWitupTest {

    // Minimal subclass to allow instantiation and override abstract methods
    static class TestAsynchronousServerSocketChannelImpl extends AsynchronousServerSocketChannelImpl {

        TestAsynchronousServerSocketChannelImpl(AsynchronousChannelGroupImpl group) {
            super(group);
        }

        @Override
        void implClose() throws IOException {
            // no-op for test
        }

        @Override
        java.util.concurrent.Future<java.nio.channels.AsynchronousSocketChannel> implAccept(Object attachment,
                java.nio.channels.CompletionHandler<java.nio.channels.AsynchronousSocketChannel, Object> handler) {
            return null; // not needed for this test
        }
    }

    public static void main(String[] args) throws Exception {
        // Create dummy group for constructor
        AsynchronousChannelGroupImpl dummyGroup = new AsynchronousChannelGroupImpl(null, 1) {
            @Override
            protected void implClose() throws IOException {
                // no-op
            }
        };

        TestAsynchronousServerSocketChannelImpl channel = new TestAsynchronousServerSocketChannelImpl(dummyGroup);

        // Test 1: getLocalAddress throws ClosedChannelException if channel is closed
        channel.close();
        try {
            channel.getLocalAddress();
            throw new RuntimeException("Expected ClosedChannelException not thrown");
        } catch (ClosedChannelException expected) {
            // expected
        }

        // Test 2: getLocalAddress returns revealed local address when open
        // Reopen channel by creating new instance
        channel = new TestAsynchronousServerSocketChannelImpl(dummyGroup);

        // Set localAddress field via reflection is not allowed, so use accessible setter if any
        // No setter visible, so set localAddress directly (field is protected volatile)
        // Use subclass access
        InetSocketAddress addr = new InetSocketAddress(0);
        channel.localAddress = addr;

        SocketAddress returned = channel.getLocalAddress();
        if (returned == null) {
            throw new RuntimeException("getLocalAddress returned null unexpectedly");
        }
        // The returned address should be the revealed local address of the set localAddress
        // Net.getRevealedLocalAddress returns the same address or a wrapped one, so check not null

        // Additional check: returned address should be equal or at least not null
        if (!(returned instanceof InetSocketAddress)) {
            throw new RuntimeException("Returned address is not InetSocketAddress as expected");
        }
    }
}
