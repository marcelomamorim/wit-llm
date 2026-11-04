/*
 * @test
 * @summary Test sun.nio.ch.SocketChannelImpl.bind(SocketAddress) method
 * @run main SocketChannelImplWitupTest
 */

import sun.nio.ch.SocketChannelImpl;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.ConnectionPendingException;
import java.io.IOException;

public class SocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testBindNullAddress();
        testBindAlreadyBound();
        testBindConnectionPending();
    }

    private static void testBindNullAddress() throws IOException {
        SocketChannelImpl channel = new DummySocketChannelImpl();
        channel.state = 0; // ST_UNCONNECTED
        channel.localAddress = null;
        SocketChannelImpl result = channel.bind(null);
        if (result != channel) {
            throw new AssertionError("bind(null) should return this");
        }
        if (channel.localAddress == null) {
            throw new AssertionError("localAddress should be set after bind(null)");
        }
    }

    private static void testBindAlreadyBound() throws IOException {
        SocketChannelImpl channel = new DummySocketChannelImpl();
        channel.state = 0; // ST_UNCONNECTED
        channel.localAddress = new InetSocketAddress(0);
        try {
            channel.bind(new InetSocketAddress(0));
            throw new AssertionError("Expected AlreadyBoundException");
        } catch (AlreadyBoundException expected) {
        }
    }

    private static void testBindConnectionPending() throws IOException {
        SocketChannelImpl channel = new DummySocketChannelImpl();
        channel.state = 1; // ST_CONNECTIONPENDING
        channel.localAddress = null;
        try {
            channel.bind(new InetSocketAddress(0));
            throw new AssertionError("Expected ConnectionPendingException");
        } catch (ConnectionPendingException expected) {
        }
    }

    // Minimal subclass to allow instantiation and override native calls
    private static class DummySocketChannelImpl extends SocketChannelImpl {
        DummySocketChannelImpl() throws IOException {
            super(null);
        }

        @Override
        protected void ensureOpen() throws IOException {
            if (closed) throw new IOException("Closed");
        }

        @Override
        protected void NetHooks_beforeTcpBind(java.io.FileDescriptor fd, java.net.InetAddress addr, int port) {
            // no-op
        }

        @Override
        protected void Net_bind(java.io.FileDescriptor fd, java.net.InetAddress addr, int port) throws IOException {
            // no-op
        }

        @Override
        protected InetSocketAddress Net_localAddress(java.io.FileDescriptor fd) {
            return new InetSocketAddress(12345);
        }

        boolean closed = false;

        @Override
        public void close() throws IOException {
            closed = true;
        }
    }
}