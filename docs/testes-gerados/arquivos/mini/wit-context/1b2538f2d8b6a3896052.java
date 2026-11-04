/*
 * @test
 * @summary Test sun.nio.ch.ServerSocketAdaptor.setReceiveBufferSize(int) for valid and invalid inputs
 * @run main ServerSocketAdaptorWitupTest
 */

import sun.nio.ch.ServerSocketAdaptor;
import sun.nio.ch.ServerSocketChannelImpl;
import java.net.SocketException;
import java.net.StandardSocketOptions;
import java.io.IOException;

public class ServerSocketAdaptorWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy ServerSocketChannelImpl for testing
        ServerSocketChannelImpl ssc = new DummyServerSocketChannelImpl();
        ServerSocketAdaptor adaptor = ServerSocketAdaptor.create(ssc);

        // Test 1: setReceiveBufferSize with valid positive size
        try {
            adaptor.setReceiveBufferSize(1024);
            if (!((DummyServerSocketChannelImpl)ssc).lastSetOption.equals(StandardSocketOptions.SO_RCVBUF) ||
                ((DummyServerSocketChannelImpl)ssc).lastSetValue != 1024) {
                throw new RuntimeException("setReceiveBufferSize did not set the expected option/value");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for valid size", e);
        }

        // Test 2: setReceiveBufferSize with zero size should throw IllegalArgumentException
        try {
            adaptor.setReceiveBufferSize(0);
            throw new RuntimeException("Expected IllegalArgumentException for size=0");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test 3: setReceiveBufferSize with negative size should throw IllegalArgumentException
        try {
            adaptor.setReceiveBufferSize(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test 4: setReceiveBufferSize propagates SocketException on IO failure
        ((DummyServerSocketChannelImpl)ssc).failSetOption = true;
        try {
            adaptor.setReceiveBufferSize(2048);
            throw new RuntimeException("Expected SocketException due to simulated IO failure");
        } catch (SocketException expected) {
            // expected
        }

        System.out.println("All tests passed.");
    }

    // Dummy implementation to simulate ServerSocketChannelImpl behavior
    static class DummyServerSocketChannelImpl extends ServerSocketChannelImpl {
        volatile boolean failSetOption = false;
        volatile java.net.SocketOption<?> lastSetOption = null;
        volatile int lastSetValue = -1;

        DummyServerSocketChannelImpl() throws IOException {
            super(null);
        }

        @Override
        public <T> void setOption(java.net.SocketOption<T> name, T value) throws IOException {
            if (failSetOption) {
                throw new IOException("Simulated IO failure");
            }
            if (name == StandardSocketOptions.SO_RCVBUF && value instanceof Integer) {
                lastSetOption = name;
                lastSetValue = (Integer) value;
            }
        }
    }
}
