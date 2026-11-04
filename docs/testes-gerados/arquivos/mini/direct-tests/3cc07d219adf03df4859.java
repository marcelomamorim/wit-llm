/*
 * @test
 * @summary Test sun.nio.ch.SocketAdaptor.setSendBufferSize(int) method
 * @run main SocketAdaptorWitupTest
 */

import sun.nio.ch.SocketAdaptor;
import sun.nio.ch.SocketChannelImpl;
import java.net.SocketException;
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
        public boolean connect(java.net.SocketAddress sa) { return true; }
        public void setOption(java.net.SocketOption<Integer> name, Integer value) throws java.io.IOException { }
    }

    public static void main(String[] args) throws Exception {
        DummySocketChannelImpl dummy = new DummySocketChannelImpl();
        SocketAdaptor socket = SocketAdaptor.create(dummy);

        // Test valid positive size
        try {
            socket.setSendBufferSize(1024);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for valid size", e);
        }

        // Test zero size - should throw IllegalArgumentException
        try {
            socket.setSendBufferSize(0);
            throw new RuntimeException("Expected IllegalArgumentException for size 0");
        } catch (IllegalArgumentException expected) {
        }

        // Test negative size - should throw IllegalArgumentException
        try {
            socket.setSendBufferSize(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException expected) {
        }
    }
}
