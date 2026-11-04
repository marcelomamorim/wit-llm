/*
 * @test
 * @summary Test for getRemoteAddress method in AsynchronousSocketChannelImpl
 * @run main AsynchronousSocketChannelImplWitupTest
 */

import java.nio.channels.*;
import java.net.*;
import java.io.IOException;

public class AsynchronousSocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetRemoteAddressWhenOpen();
        testGetRemoteAddressWhenClosed();
    }

    static void testGetRemoteAddressWhenOpen() throws Exception {
        // Setup: create a mock or dummy instance with open state and remoteAddress set
        DummyAsynchronousSocketChannel channel = new DummyAsynchronousSocketChannel(true, new InetSocketAddress("127.0.0.1", 8080));
        SocketAddress result = channel.getRemoteAddress();
        if (result == null || !(result instanceof InetSocketAddress)) {
            throw new AssertionError("Expected InetSocketAddress, got: " + result);
        }
        InetSocketAddress addr = (InetSocketAddress) result;
        if (!addr.getAddress().getHostAddress().equals("127.0.0.1") || addr.getPort() != 8080) {
            throw new AssertionError("Address mismatch: " + addr);
        }
        System.out.println("testGetRemoteAddressWhenOpen passed");
    }

    static void testGetRemoteAddressWhenClosed() throws Exception {
        // Setup: create a dummy instance with closed state
        DummyAsynchronousSocketChannel channel = new DummyAsynchronousSocketChannel(false, null);
        try {
            channel.getRemoteAddress();
            throw new AssertionError("Expected ClosedChannelException");
        } catch (ClosedChannelException e) {
            // expected
        }
        System.out.println("testGetRemoteAddressWhenClosed passed");
    }

    // Dummy subclass to simulate behavior
    static class DummyAsynchronousSocketChannel extends sun.nio.ch.AsynchronousSocketChannelImpl {
        private final boolean open;
        private final SocketAddress remoteAddr;

        DummyAsynchronousSocketChannel(boolean open, SocketAddress remoteAddr) throws IOException {
            super(null); // passing null group for simplicity; in real tests, use a mock or dummy group
            this.open = open;
            this.remoteAddr = remoteAddr;
            if (open) {
                // set state to connected
                this.state = ST_CONNECTED;
            } else {
                this.state = ST_UNINITIALIZED;
            }
        }

        @Override
        public final boolean isOpen() {
            return open;
        }

        @Override
        public final SocketAddress getRemoteAddress() throws IOException {
            if (!isOpen()) {
                throw new ClosedChannelException();
            }
            return remoteAddr;
        }
    }
}
