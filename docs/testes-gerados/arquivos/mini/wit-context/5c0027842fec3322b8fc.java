/*
 * @test
 * @summary Test sun.nio.ch.DatagramChannelImpl.connect(SocketAddress) for expected exceptions and normal behavior
 * @run main DatagramChannelImplWitupTest
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardProtocolFamily;
import java.nio.channels.AlreadyConnectedException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.spi.SelectorProvider;
import sun.nio.ch.DatagramChannelImpl;

public class DatagramChannelImplWitupTest {
    public static void main(String[] args) throws Exception {
        testConnectNullAddress();
        testConnectAlreadyConnected();
        testConnectNormal();
    }

    private static void testConnectNullAddress() {
        DatagramChannelImpl dc = new DatagramChannelImpl(SelectorProvider.provider());
        try {
            dc.connect(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        } catch (IOException ioe) {
            // Net.checkAddress may throw IOException, accept it as well
        }
    }

    private static void testConnectAlreadyConnected() throws IOException {
        DatagramChannelImpl dc = new DatagramChannelImpl(SelectorProvider.provider());
        InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 0);
        dc.connect(addr); // first connect
        try {
            dc.connect(addr); // second connect should throw AlreadyConnectedException
            throw new RuntimeException("Expected AlreadyConnectedException not thrown");
        } catch (AlreadyConnectedException ace) {
            // expected
        }
    }

    private static void testConnectNormal() throws IOException {
        DatagramChannelImpl dc = new DatagramChannelImpl(SelectorProvider.provider());
        InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 0);
        DatagramChannel result = dc.connect(addr);
        if (result != dc) {
            throw new RuntimeException("connect() did not return this");
        }
    }
}
