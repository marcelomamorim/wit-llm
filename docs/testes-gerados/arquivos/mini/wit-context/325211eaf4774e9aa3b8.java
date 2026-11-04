/*
 * @test
 * @summary Test sun.nio.ch.SocketChannelImpl.bind(SocketAddress) for expected exceptions and normal behavior
 * @run main SocketChannelImplWitupTest
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.ConnectionPendingException;
import sun.nio.ch.SocketChannelImpl;

public class SocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testBindThrowsConnectionPendingException();
        testBindThrowsAlreadyBoundException();
        testBindWithNullAddressSucceeds();
    }

    // Helper subclass to expose protected constructor for testing
    static class TestSocketChannelImpl extends SocketChannelImpl {
        protected TestSocketChannelImpl() throws IOException {
            super(null);
        }

        // Expose state manipulation for testing
        void setState(int state) {
            synchronized (stateLock()) {
                setStateField(state);
            }
        }

        void setLocalAddress(InetSocketAddress addr) {
            synchronized (stateLock()) {
                setLocalAddressField(addr);
            }
        }

        // Reflection-like access to private fields via package-private methods
        private Object stateLock() {
            try {
                java.lang.reflect.Field f = SocketChannelImpl.class.getDeclaredField("stateLock");
                f.setAccessible(true);
                return f.get(this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private void setStateField(int s) {
            try {
                java.lang.reflect.Field f = SocketChannelImpl.class.getDeclaredField("state");
                f.setAccessible(true);
                f.setInt(this, s);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private void setLocalAddressField(InetSocketAddress addr) {
            try {
                java.lang.reflect.Field f = SocketChannelImpl.class.getDeclaredField("localAddress");
                f.setAccessible(true);
                f.set(this, addr);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    private static void testBindThrowsConnectionPendingException() throws Exception {
        TestSocketChannelImpl ch = new TestSocketChannelImpl();
        ch.setState(1); // ST_CONNECTIONPENDING
        try {
            ch.bind(null);
            throw new RuntimeException("Expected ConnectionPendingException not thrown");
        } catch (ConnectionPendingException expected) {
            // success
        }
    }

    private static void testBindThrowsAlreadyBoundException() throws Exception {
        TestSocketChannelImpl ch = new TestSocketChannelImpl();
        ch.setState(0); // ST_UNCONNECTED
        ch.setLocalAddress(new InetSocketAddress(0));
        try {
            ch.bind(null);
            throw new RuntimeException("Expected AlreadyBoundException not thrown");
        } catch (AlreadyBoundException expected) {
            // success
        }
    }

    private static void testBindWithNullAddressSucceeds() throws Exception {
        TestSocketChannelImpl ch = new TestSocketChannelImpl();
        ch.setState(0); // ST_UNCONNECTED
        ch.setLocalAddress(null);
        SocketAddress result = ch.bind(null).getLocalAddress();
        if (result == null) {
            throw new RuntimeException("bind(null) did not set local address");
        }
    }

}
