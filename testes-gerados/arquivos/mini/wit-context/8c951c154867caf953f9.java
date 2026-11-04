/*
 * @test
 * @summary Test sun.nio.ch.DatagramSocketAdaptor connect(SocketAddress) method
 * @run main DatagramSocketAdaptorWitupTest
 */

import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.net.SocketException;
import sun.nio.ch.DatagramSocketAdaptor;
import sun.nio.ch.DatagramChannelImpl;
import java.util.Objects;

public class DatagramSocketAdaptorWitupTest {

    public static void main(String[] args) throws Exception {
        testConnectWithNullAddress();
        testConnectWithValidAddress();
    }

    private static void testConnectWithNullAddress() {
        DatagramChannelImpl dummyChannel = new DummyDatagramChannelImpl();
        DatagramSocketAdaptor dsa = DatagramSocketAdaptor.create(dummyChannel);
        try {
            dsa.connect(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            if (!"Address can't be null".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch", npe);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }

    private static void testConnectWithValidAddress() throws Exception {
        DatagramChannelImpl dummyChannel = new DummyDatagramChannelImpl();
        DatagramSocketAdaptor dsa = DatagramSocketAdaptor.create(dummyChannel);
        InetAddress loopback = InetAddress.getByName("127.0.0.1");
        InetSocketAddress isa = new InetSocketAddress(loopback, 12345);
        dsa.connect(isa);
        // No exception means success
    }

    // Minimal dummy DatagramChannelImpl to allow DatagramSocketAdaptor creation
    private static class DummyDatagramChannelImpl extends DatagramChannelImpl {
        DummyDatagramChannelImpl() {
            super(null);
        }

        @Override
        public DatagramChannelImpl bind(SocketAddress local) {
            return this;
        }

        @Override
        public DatagramChannelImpl connect(SocketAddress remote) {
            // Accept all connects without error
            return this;
        }

        @Override
        public boolean isOpen() {
            return true;
        }

        @Override
        public void close() {
        }
    }
}
