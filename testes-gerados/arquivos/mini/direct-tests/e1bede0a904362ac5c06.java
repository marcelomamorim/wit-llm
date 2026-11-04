/*
 * @test
 * @summary Test DatagramChannelImpl.disconnect() behavior
 * @run main DatagramChannelImplWitupTest
 */

import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.spi.SelectorProvider;
import sun.nio.ch.DatagramChannelImpl;

public class DatagramChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testDisconnectOnUnconnectedChannel();
        testDisconnectOnConnectedChannel();
    }

    private static void testDisconnectOnUnconnectedChannel() throws IOException {
        DatagramChannelImpl channel = new DatagramChannelImpl(SelectorProvider.provider(), StandardProtocolFamily.INET);
        // Initially unconnected, disconnect should return this without exception
        DatagramChannel returned = channel.disconnect();
        if (returned != channel) {
            throw new AssertionError("disconnect() should return the same instance when unconnected");
        }
        if (!channel.isOpen()) {
            throw new AssertionError("Channel should remain open after disconnect on unconnected channel");
        }
    }

    private static void testDisconnectOnConnectedChannel() throws IOException {
        DatagramChannelImpl channel = new DatagramChannelImpl(SelectorProvider.provider(), StandardProtocolFamily.INET);
        // Connect to a local address to simulate connected state
        SocketAddress local = new java.net.InetSocketAddress("127.0.0.1", 0);
        channel.bind(local);
        SocketAddress remote = new java.net.InetSocketAddress("127.0.0.1", 7); // echo port
        channel.connect(remote);

        // Now disconnect should succeed and set state to unconnected
        DatagramChannel returned = channel.disconnect();
        if (returned != channel) {
            throw new AssertionError("disconnect() should return the same instance when connected");
        }
        if (!channel.isOpen()) {
            throw new AssertionError("Channel should remain open after disconnect");
        }
        if (channel.getRemoteAddress() != null) {
            throw new AssertionError("Remote address should be null after disconnect");
        }
    }
}
