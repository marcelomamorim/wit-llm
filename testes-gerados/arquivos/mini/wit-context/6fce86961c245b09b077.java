/*
 * @test
 * @summary Test sun.nio.ch.SocketAdaptor connect(SocketAddress) method
 * @run main SocketAdaptorWitupTest
 */

import java.io.IOException;
import java.net.SocketAddress;
import sun.nio.ch.SocketAdaptor;
import sun.nio.ch.SocketChannelImpl;

public class SocketAdaptorWitupTest {

    // Minimal stub subclass to allow construction of SocketAdaptor
    static class DummySocketChannelImpl extends SocketChannelImpl {
        protected DummySocketChannelImpl() {
            super(null);
        }
        @Override
        public boolean isBlocking() {
            return true;
        }
        @Override
        public Object blockingLock() {
            return this;
        }
        @Override
        public void connect(SocketAddress remote) throws IOException {
            // no-op
        }
        @Override
        public void configureBlocking(boolean block) throws IOException {
            // no-op
        }
    }

    public static void main(String[] args) throws Exception {
        testConnectNullAddress();
        testConnectValidAddress();
    }

    private static void testConnectNullAddress() {
        SocketChannelImpl sc = new DummySocketChannelImpl();
        SocketAdaptor socket = SocketAdaptor.create(sc);
        try {
            socket.connect(null);
            throw new RuntimeException("Expected IllegalArgumentException for null address");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testConnectValidAddress() throws IOException {
        SocketChannelImpl sc = new DummySocketChannelImpl();
        SocketAdaptor socket = SocketAdaptor.create(sc);
        // Use a dummy SocketAddress subclass since no public constructor for InetSocketAddress without host
        SocketAddress dummyAddress = new SocketAddress() {};
        socket.connect(dummyAddress);
        // If no exception, test passes
    }
}
