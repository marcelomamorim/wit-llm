/*
 * @test
 * @summary Test sun.nio.ch.SocketAdaptor.setReceiveBufferSize(int) for valid and invalid inputs
 * @run main SocketAdaptorWitupTest
 */

import java.net.SocketException;
import sun.nio.ch.SocketAdaptor;
import sun.nio.ch.SocketChannelImpl;
import java.net.StandardSocketOptions;

public class SocketAdaptorWitupTest {

    // Minimal stub subclass to allow construction
    static class DummySocketChannelImpl extends SocketChannelImpl {
        protected DummySocketChannelImpl() {
            super(null);
        }
        public boolean isBlocking() { return true; }
        public Object blockingLock() { return this; }
        public void connect(java.net.SocketAddress sa) { }
        public void configureBlocking(boolean b) { }
        public boolean connect(java.net.SocketAddress sa, int timeout) { return true; }
        public void setOption(java.net.SocketOption<Integer> name, Integer value) throws SocketException {
            if (name == StandardSocketOptions.SO_RCVBUF) {
                if (value == null || value <= 0) {
                    throw new IllegalArgumentException("Invalid receive size");
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        DummySocketChannelImpl dummyChannel = new DummySocketChannelImpl();
        SocketAdaptor socket = SocketAdaptor.create(dummyChannel);

        // Test valid positive size
        try {
            socket.setReceiveBufferSize(1024);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for valid size", e);
        }

        // Test invalid zero size - expect IllegalArgumentException
        try {
            socket.setReceiveBufferSize(0);
            throw new RuntimeException("Expected IllegalArgumentException for size=0");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test invalid negative size - expect IllegalArgumentException
        try {
            socket.setReceiveBufferSize(-1);
            throw new RuntimeException("Expected IllegalArgumentException for size=-1");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
