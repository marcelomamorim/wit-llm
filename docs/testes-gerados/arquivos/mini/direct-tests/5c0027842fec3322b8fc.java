/*
 * @test
 * @summary Test sun.nio.ch.DatagramChannelImpl connect method behavior
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
        testConnectSuccess();
        testConnectAlreadyConnected();
    }

    private static void testConnectSuccess() throws IOException {
        SelectorProvider sp = SelectorProvider.provider();
        DatagramChannelImpl channel = new DatagramChannelImpl(sp, StandardProtocolFamily.INET);

        InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 0);

        DatagramChannel returned = channel.connect(addr);

        if (returned != channel) {
            throw new AssertionError("connect should return this instance");
        }

        SocketAddress remote = channel.getRemoteAddress();
        if (remote == null || !remote.equals(addr)) {
            throw new AssertionError("Remote address not set correctly after connect");
        }

        SocketAddress local = channel.getLocalAddress();
        if (local == null) {
            throw new AssertionError("Local address should be set after connect");
        }

        channel.close();
    }

    private static void testConnectAlreadyConnected() throws IOException {
        SelectorProvider sp = SelectorProvider.provider();
        DatagramChannelImpl channel = new DatagramChannelImpl(sp, StandardProtocolFamily.INET);

        InetSocketAddress addr1 = new InetSocketAddress("127.0.0.1", 0);
        InetSocketAddress addr2 = new InetSocketAddress("127.0.0.1", 1);

        channel.connect(addr1);

        boolean threw = false;
        try {
            channel.connect(addr2);
        } catch (AlreadyConnectedException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected AlreadyConnectedException when connecting twice");
        }

        channel.close();
    }
}
