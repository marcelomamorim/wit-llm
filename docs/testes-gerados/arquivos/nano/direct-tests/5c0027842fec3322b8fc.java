/*
 * @test
 * @summary Tests the connect method of sun.nio.ch.DatagramChannelImpl.
 * @run main DatagramChannelImplWitupTest
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import sun.nio.ch.DatagramChannelImpl;

public class DatagramChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testConnectWithValidAddress();
        testConnectWithNullAddress();
        testConnectWhenAlreadyConnected();
    }

    static void testConnectWithValidAddress() throws IOException {
        InetAddress localAddr = InetAddress.getLoopbackAddress();
        InetSocketAddress isa = new InetSocketAddress(localAddr, 0);
        DatagramChannelImpl channel = createChannel();
        try {
            channel.connect(isa);
            assert channel.isConnected() : "Channel should be connected";
        } finally {
            channel.close();
        }
    }

    static void testConnectWithNullAddress() {
        try {
            DatagramChannelImpl channel = createChannel();
            try {
                channel.connect(null);
                assert false : "Expected NullPointerException";
            } catch (NullPointerException e) {
                // expected
            } finally {
                channel.close();
            }
        } catch (IOException e) {
            // ignore
        }
    }

    static void testConnectWhenAlreadyConnected() throws IOException {
        InetAddress localAddr = InetAddress.getLoopbackAddress();
        InetSocketAddress isa1 = new InetSocketAddress(localAddr, 0);
        InetSocketAddress isa2 = new InetSocketAddress(localAddr, 0);
        DatagramChannelImpl channel = createChannel();
        try {
            channel.connect(isa1);
            try {
                channel.connect(isa2);
                assert false : "Expected AlreadyConnectedException";
            } catch (IOException e) {
                assert e instanceof java.nio.channels.AlreadyConnectedException : "Expected AlreadyConnectedException";
            }
        } finally {
            channel.close();
        }
    }

    static DatagramChannelImpl createChannel() throws IOException {
        // Create a DatagramChannelImpl with a dummy SelectorProvider
        // Using null for SelectorProvider is not allowed, so we use the default
        return new DatagramChannelImpl(java.nio.channels.spi.SelectorProvider.provider());
    }
}
