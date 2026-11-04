/*
 * @test
 * @summary Test sun.nio.ch.SocketAdaptor.setSoTimeout(int) for valid and invalid inputs
 * @run main SocketAdaptorWitupTest
 */

import java.net.SocketException;
import sun.nio.ch.SocketAdaptor;
import sun.nio.ch.SocketChannelImpl;

public class SocketAdaptorWitupTest {

    // Minimal stub subclass to allow construction of SocketAdaptor
    static class DummySocketChannelImpl extends SocketChannelImpl {
        protected DummySocketChannelImpl() {
            super(null);
        }
        public boolean isBlocking() { return true; }
        public Object blockingLock() { return this; }
        public void connect(java.net.SocketAddress sa) { }
        public void configureBlocking(boolean b) { }
    }

    public static void main(String[] args) throws Exception {
        DummySocketChannelImpl dummyChannel = new DummySocketChannelImpl();
        SocketAdaptor socket = SocketAdaptor.create(dummyChannel);

        // Test valid timeout values
        socket.setSoTimeout(0);
        socket.setSoTimeout(1000);

        // Test invalid timeout value: negative
        try {
            socket.setSoTimeout(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative timeout");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
