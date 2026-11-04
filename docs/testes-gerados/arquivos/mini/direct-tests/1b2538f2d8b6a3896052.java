/*
 * @test
 * @summary Test sun.nio.ch.ServerSocketAdaptor.setReceiveBufferSize(int) method
 * @run main ServerSocketAdaptorWitupTest
 */

import sun.nio.ch.ServerSocketAdaptor;
import sun.nio.ch.ServerSocketChannelImpl;
import java.net.SocketException;
import java.net.StandardSocketOptions;
import java.io.IOException;

public class ServerSocketAdaptorWitupTest {

    // Minimal stub for ServerSocketChannelImpl to test setReceiveBufferSize
    static class StubServerSocketChannelImpl extends ServerSocketChannelImpl {
        private int lastSetOptionValue = -1;
        private boolean throwIOException = false;

        protected StubServerSocketChannelImpl() {
            super(null);
        }

        @Override
        public <T> ServerSocketChannelImpl setOption(java.net.SocketOption<T> name, T value) throws IOException {
            if (throwIOException) {
                throw new IOException("Simulated IO exception");
            }
            if (StandardSocketOptions.SO_RCVBUF.equals(name)) {
                if (value instanceof Integer) {
                    lastSetOptionValue = (Integer) value;
                }
            }
            return this;
        }
    }

    public static void main(String[] args) throws Exception {
        testSetReceiveBufferSizeValid();
        testSetReceiveBufferSizeZeroOrNegative();
        testSetReceiveBufferSizeIOException();
    }

    private static void testSetReceiveBufferSizeValid() throws Exception {
        StubServerSocketChannelImpl stub = new StubServerSocketChannelImpl();
        ServerSocketAdaptor adaptor = ServerSocketAdaptor.create(stub);

        int size = 1024;
        adaptor.setReceiveBufferSize(size);

        if (stub.lastSetOptionValue != size) {
            throw new RuntimeException("setReceiveBufferSize did not set the correct buffer size");
        }
    }

    private static void testSetReceiveBufferSizeZeroOrNegative() throws Exception {
        StubServerSocketChannelImpl stub = new StubServerSocketChannelImpl();
        ServerSocketAdaptor adaptor = ServerSocketAdaptor.create(stub);

        try {
            adaptor.setReceiveBufferSize(0);
            throw new RuntimeException("Expected IllegalArgumentException for size 0");
        } catch (IllegalArgumentException expected) {
        }

        try {
            adaptor.setReceiveBufferSize(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException expected) {
        }
    }

    private static void testSetReceiveBufferSizeIOException() throws Exception {
        StubServerSocketChannelImpl stub = new StubServerSocketChannelImpl();
        stub.throwIOException = true;
        ServerSocketAdaptor adaptor = ServerSocketAdaptor.create(stub);

        try {
            adaptor.setReceiveBufferSize(1024);
            throw new RuntimeException("Expected SocketException wrapping IOException");
        } catch (SocketException expected) {
        }
    }
}
