/*
 * @test
 * @summary Test sun.nio.ch.SocketAdaptor.setSendBufferSize(int) for valid and invalid inputs
 * @run main SocketAdaptorWitupTest
 */

import java.net.SocketException;
import sun.nio.ch.SocketAdaptor;
import sun.nio.ch.SocketChannelImpl;
import java.net.StandardSocketOptions;

public class SocketAdaptorWitupTest {

    // Minimal stub subclass to allow construction of SocketAdaptor
    static class DummySocketChannelImpl extends SocketChannelImpl {
        protected DummySocketChannelImpl() {
            super(null);
        }
        @Override
        public boolean isBlocking() { return true; }
        @Override
        public Object blockingLock() { return this; }
        @Override
        public void connect(java.net.SocketAddress remote) { }
        @Override
        public void configureBlocking(boolean block) { }
        @Override
        public boolean connect(java.net.SocketAddress remote, int timeout) { return true; }
        @Override
        public void setOption(java.net.SocketOption<?> name, Object value) { }
    }

    public static void main(String[] args) throws Exception {
        DummySocketChannelImpl dummyChannel = new DummySocketChannelImpl();
        SocketAdaptor socket = SocketAdaptor.create(dummyChannel);

        // Test valid positive size
        try {
            socket.setSendBufferSize(1024);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for valid size", e);
        }

        // Test zero size - expect IllegalArgumentException
        try {
            socket.setSendBufferSize(0);
            throw new RuntimeException("Expected IllegalArgumentException for size=0");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test negative size - expect IllegalArgumentException
        try {
            socket.setSendBufferSize(-1);
            throw new RuntimeException("Expected IllegalArgumentException for size=-1");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
