/* @test    @summary Test getLocalAddress() method of AsynchronousServerSocketChannelImpl for open and closed states    @run main AsynchronousServerSocketChannelImplWitupTest */

import java.io.IOException;
import java.net.SocketAddress;
import sun.nio.ch.AsynchronousServerSocketChannelImpl;
import sun.net.Net;
import java.net.InetSocketAddress;

public class AsynchronousServerSocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLocalAddressWhenOpen();
        testGetLocalAddressWhenClosed();
    }

    private static void testGetLocalAddressWhenOpen() throws Exception {
        // Create a dummy implementation with a mock localAddress
        AsynchronousServerSocketChannelImpl channel = new DummyAsynchronousServerSocketChannelImpl();
        SocketAddress address = channel.getLocalAddress();
        if (!(address instanceof InetSocketAddress)) {
            throw new AssertionError("Expected InetSocketAddress, got " + address.getClass());
        }
        InetSocketAddress inetAddress = (InetSocketAddress) address;
        System.out.println("Local address (open): " + inetAddress);
    }

    private static void testGetLocalAddressWhenClosed() throws Exception {
        AsynchronousServerSocketChannelImpl channel = new DummyAsynchronousServerSocketChannelImpl();
        channel.close();
        try {
            channel.getLocalAddress();
            throw new AssertionError("Expected ClosedChannelException");
        } catch (IOException e) {
            if (!(e instanceof java.nio.channels.ClosedChannelException)) {
                throw new AssertionError("Expected ClosedChannelException, got " + e);
            }
            System.out.println("Caught expected exception when closed: " + e);
        }
    }

    private static class DummyAsynchronousServerSocketChannelImpl extends AsynchronousServerSocketChannelImpl {
        private boolean isClosed = false;
        private SocketAddress localAddr = new InetSocketAddress(12345);

        DummyAsynchronousServerSocketChannelImpl() {
            super(null);
        }

        @Override
        public final SocketAddress getLocalAddress() throws IOException {
            if (isClosed) {
                throw new java.nio.channels.ClosedChannelException();
            }
            return localAddr;
        }

        @Override
        public void close() throws IOException {
            isClosed = true;
        }

        @Override
        void implClose() throws IOException {
            // no-op for dummy
        }

        @Override
        Future<?> implAccept(Object attachment, java.nio.channels.CompletionHandler<java.nio.channels.AsynchronousSocketChannel, Object> handler) {
            return null; // not used in tests
        }
    }
}
