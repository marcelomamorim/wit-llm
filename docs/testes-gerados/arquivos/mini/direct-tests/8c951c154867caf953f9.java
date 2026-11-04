/*
 * @test
 * @summary Test sun.nio.ch.DatagramSocketAdaptor connect(SocketAddress) method
 * @run main DatagramSocketAdaptorWitupTest
 */

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import sun.nio.ch.DatagramSocketAdaptor;
import sun.nio.ch.DatagramChannelImpl;

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
        } catch (NullPointerException e) {
            if (!"Address can't be null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected NullPointerException message: " + e.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }

    private static void testConnectWithValidAddress() throws Exception {
        DatagramChannelImpl dummyChannel = new DummyDatagramChannelImpl();
        DatagramSocketAdaptor dsa = DatagramSocketAdaptor.create(dummyChannel);
        InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 12345);
        dsa.connect(addr);
        if (!((DummyDatagramChannelImpl)dummyChannel).connectedAddress.equals(addr)) {
            throw new RuntimeException("connectInternal did not connect to the expected address");
        }
    }

    // Minimal dummy DatagramChannelImpl to test connectInternal call
    private static class DummyDatagramChannelImpl extends DatagramChannelImpl {
        SocketAddress connectedAddress = null;

        DummyDatagramChannelImpl() {
            super(null);
        }

        @Override
        public void connect(SocketAddress remote) {
            this.connectedAddress = remote;
        }

        @Override
        public void bind(SocketAddress local) {
            // no-op
        }

        @Override
        public boolean isConnected() {
            return connectedAddress != null;
        }

        @Override
        public void close() {
            // no-op
        }
    }
}
