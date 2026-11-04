/*
 * @test
 * @summary Test sun.nio.ch.SocketChannelImpl connect method behavior
 * @run main SocketChannelImplWitupTest
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import sun.nio.ch.SocketChannelImpl;
import sun.net.NetHooks;

public class SocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testConnectWithNullAddress();
        testConnectWithUnresolvedAddress();
        testConnectWithLocalhost();
    }

    private static void testConnectWithNullAddress() {
        SocketChannelImpl channel = null;
        try {
            channel = createSocketChannelImpl();
            try {
                channel.connect(null);
                throw new RuntimeException("Expected NullPointerException for null address");
            } catch (NullPointerException npe) {
                // Expected
            } catch (IOException ioe) {
                // IOException also possible if Net.checkAddress throws
            }
        } finally {
            closeChannel(channel);
        }
    }

    private static void testConnectWithUnresolvedAddress() throws Exception {
        SocketChannelImpl channel = null;
        try {
            channel = createSocketChannelImpl();
            InetSocketAddress unresolved = InetSocketAddress.createUnresolved("unresolved.host", 80);
            try {
                channel.connect(unresolved);
                // connect may fail with IOException or SecurityException
            } catch (IOException | SecurityException e) {
                // Expected possible exceptions
            }
        } finally {
            closeChannel(channel);
        }
    }

    private static void testConnectWithLocalhost() throws Exception {
        SocketChannelImpl channel = null;
        try {
            channel = createSocketChannelImpl();
            InetSocketAddress local = new InetSocketAddress(InetAddress.getLocalHost(), 0);
            boolean result = false;
            try {
                result = channel.connect(local);
            } catch (IOException ioe) {
                // connect may fail if port 0 or no server listening
            }
            // result is boolean, no assert, just ensure no exception thrown
        } finally {
            closeChannel(channel);
        }
    }

    private static SocketChannelImpl createSocketChannelImpl() throws IOException {
        // Use public SelectorProvider to open SocketChannel and cast if possible
        SocketChannel sc = SocketChannel.open();
        if (sc instanceof SocketChannelImpl) {
            return (SocketChannelImpl) sc;
        } else {
            // fallback: create dummy subclass to test connect
            return new DummySocketChannelImpl(sc);
        }
    }

    private static void closeChannel(SocketChannelImpl channel) {
        if (channel != null) {
            try {
                channel.close();
            } catch (IOException ignored) {
            }
        }
    }

    // Dummy subclass to delegate to underlying SocketChannel
    private static class DummySocketChannelImpl extends SocketChannelImpl {
        private final SocketChannel delegate;

        DummySocketChannelImpl(SocketChannel delegate) {
            super(delegate.provider());
            this.delegate = delegate;
        }

        @Override
        public boolean connect(SocketAddress sa) throws IOException {
            return delegate.connect(sa);
        }

        @Override
        public boolean isBlocking() {
            return delegate.isBlocking();
        }

        @Override
        public void close() throws IOException {
            delegate.close();
        }
    }
}
