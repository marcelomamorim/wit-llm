/*
 * @test
 * @summary Test sun.nio.ch.SocketAdaptor.setReceiveBufferSize(int) method
 * @run main SocketAdaptorWitupTest
 */

import sun.nio.ch.SocketAdaptor;
import sun.nio.ch.SocketChannelImpl;
import java.net.SocketException;
import java.net.StandardSocketOptions;

public class SocketAdaptorWitupTest {

    public static void main(String[] args) throws Exception {
        testSetReceiveBufferSizeValid();
        testSetReceiveBufferSizeZero();
        testSetReceiveBufferSizeNegative();
    }

    private static void testSetReceiveBufferSizeValid() throws Exception {
        SocketChannelImpl sc = new DummySocketChannelImpl();
        SocketAdaptor socket = SocketAdaptor.create(sc);
        int validSize = 1024;
        socket.setReceiveBufferSize(validSize);
        if (((DummySocketChannelImpl) sc).lastSetOption != StandardSocketOptions.SO_RCVBUF ||
            ((DummySocketChannelImpl) sc).lastSetValue != validSize) {
            throw new RuntimeException("setReceiveBufferSize did not set the correct option/value");
        }
    }

    private static void testSetReceiveBufferSizeZero() {
        try {
            SocketChannelImpl sc = new DummySocketChannelImpl();
            SocketAdaptor socket = SocketAdaptor.create(sc);
            socket.setReceiveBufferSize(0);
            throw new RuntimeException("Expected IllegalArgumentException for size=0");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for size=0", e);
        }
    }

    private static void testSetReceiveBufferSizeNegative() {
        try {
            SocketChannelImpl sc = new DummySocketChannelImpl();
            SocketAdaptor socket = SocketAdaptor.create(sc);
            socket.setReceiveBufferSize(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for negative size", e);
        }
    }

    // Dummy implementation to capture setIntOption calls
    private static class DummySocketChannelImpl extends SocketChannelImpl {
        volatile java.net.SocketOption<Integer> lastSetOption = null;
        volatile Integer lastSetValue = null;

        protected DummySocketChannelImpl() {
            super(null);
        }

        @Override
        public <T> void setOption(java.net.SocketOption<T> name, T value) throws java.io.IOException {
            if (name == StandardSocketOptions.SO_RCVBUF) {
                lastSetOption = (java.net.SocketOption<Integer>) name;
                lastSetValue = (Integer) value;
            }
        }

        // Implement abstract methods with minimal stubs
        @Override
        public boolean isConnected() { return false; }
        @Override
        public boolean isConnectionPending() { return false; }
        @Override
        public boolean connect(java.net.SocketAddress remote) { return false; }
        @Override
        public boolean finishConnect() { return false; }
        @Override
        public int read(java.nio.ByteBuffer dst) { return 0; }
        @Override
        public long read(java.nio.ByteBuffer[] dsts, int offset, int length) { return 0; }
        @Override
        public int write(java.nio.ByteBuffer src) { return 0; }
        @Override
        public long write(java.nio.ByteBuffer[] srcs, int offset, int length) { return 0; }
        @Override
        protected void implCloseSelectableChannel() { }
        @Override
        protected void implConfigureBlocking(boolean block) { }
    }
}
