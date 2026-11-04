/*
 * @test
 * @summary Test DatagramChannelImpl.disconnect() method behavior
 * @run main DatagramChannelImplWitupTest
 */

import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.nio.channels.DatagramChannel;
import java.nio.channels.spi.SelectorProvider;
import sun.nio.ch.DatagramChannelImpl;

public class DatagramChannelImplWitupTest {
    public static void main(String[] args) throws IOException {
        SelectorProvider sp = SelectorProvider.provider();
        DatagramChannelImpl channel = new DatagramChannelImpl(sp, StandardProtocolFamily.INET);

        // Initially channel is open and unconnected
        if (!channel.isOpen()) {
            throw new RuntimeException("Channel should be open after construction");
        }

        // Disconnect on unconnected channel returns this without exception
        DatagramChannel result = channel.disconnect();
        if (result != channel) {
            throw new RuntimeException("disconnect() should return this when unconnected");
        }

        // Connect the channel to a local address to test disconnect effect
        channel.connect(new java.net.InetSocketAddress("127.0.0.1", 0));

        // After connect, disconnect should succeed and return this
        DatagramChannel disconnected = channel.disconnect();
        if (disconnected != channel) {
            throw new RuntimeException("disconnect() should return this after disconnecting");
        }

        // After disconnect, channel should no longer be connected
        if (channel.isConnected()) {
            throw new RuntimeException("Channel should not be connected after disconnect");
        }

        // Closing the channel and then calling disconnect returns this without exception
        channel.close();
        DatagramChannel afterClose = channel.disconnect();
        if (afterClose != channel) {
            throw new RuntimeException("disconnect() should return this after channel is closed");
        }
    }
}
