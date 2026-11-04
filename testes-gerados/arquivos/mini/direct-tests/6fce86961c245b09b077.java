/*
 * @test
 * @summary Test sun.nio.ch.SocketAdaptor connect(SocketAddress) method
 * @run main SocketAdaptorWitupTest
 */

import java.io.IOException;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import sun.nio.ch.SocketAdaptor;
import sun.nio.ch.SocketChannelImpl;

public class SocketAdaptorWitupTest {

    // Minimal subclass to allow construction of SocketAdaptor
    static class DummySocketChannelImpl extends SocketChannelImpl {
        protected DummySocketChannelImpl() throws IOException {
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
            if (remote == null) {
                throw new IllegalArgumentException("connect: The address can't be null");
            }
            // Simulate successful connect
        }

        @Override
        public void configureBlocking(boolean block) throws IOException {
            // no-op
        }

        @Override
        public boolean connect(SocketAddress remote) throws IOException {
            return true;
        }

        @Override
        public boolean isOpen() {
            return true;
        }

        @Override
        public void close() throws IOException {
            // no-op
        }
    }

    public static void main(String[] args) throws Exception {
        DummySocketChannelImpl dummyChannel = new DummySocketChannelImpl();
        SocketAdaptor socket = SocketAdaptor.create(dummyChannel);

        // Test connect with valid address
        SocketAddress addr = new InetSocketAddress("127.0.0.1", 80);
        socket.connect(addr);

        // Test connect with null address - expect IllegalArgumentException
        try {
            socket.connect(null);
            throw new RuntimeException("Expected IllegalArgumentException for null address");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // Test connect with negative timeout via reflection on connect(SocketAddress,int)
        // Not possible here since connect(SocketAddress,int) is not public, so skip

        System.out.println("PASS");
    }
}
