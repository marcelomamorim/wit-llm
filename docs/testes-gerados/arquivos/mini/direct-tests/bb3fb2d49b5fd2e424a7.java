/*
 * @test
 * @summary Test sun.nio.ch.AsynchronousSocketChannelImpl.getRemoteAddress()
 * @run main AsynchronousSocketChannelImplWitupTest
 */

import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.io.IOException;

public class AsynchronousSocketChannelImplWitupTest {

    static class DummyChannel extends sun.nio.ch.AsynchronousSocketChannelImpl {
        private boolean open = true;
        private SocketAddress remoteAddr;

        DummyChannel(SocketAddress remoteAddr) throws IOException {
            super((sun.nio.ch.AsynchronousChannelGroupImpl)null);
            this.remoteAddress = remoteAddr;
            this.state = ST_CONNECTED;
        }

        @Override
        public boolean isOpen() {
            return open;
        }

        void closeChannel() {
            open = false;
        }
    }

    public static void main(String[] args) throws Exception {
        // Test 1: getRemoteAddress returns the remoteAddress when open
        java.net.InetSocketAddress remote = new java.net.InetSocketAddress("127.0.0.1", 12345);
        DummyChannel channel = new DummyChannel(remote);
        SocketAddress addr = channel.getRemoteAddress();
        if (!remote.equals(addr)) {
            throw new AssertionError("Expected remote address to be " + remote + " but got " + addr);
        }

        // Test 2: getRemoteAddress throws ClosedChannelException when channel is closed
        channel.closeChannel();
        try {
            channel.getRemoteAddress();
            throw new AssertionError("Expected ClosedChannelException when channel is closed");
        } catch (ClosedChannelException expected) {
            // expected
        }

        System.out.println("Test passed");
    }
}
