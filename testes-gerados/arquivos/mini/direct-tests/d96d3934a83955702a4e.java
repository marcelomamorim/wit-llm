/*
 * @test
 * @summary Test sun.nio.ch.DatagramChannelImpl.bind(SocketAddress) method
 * @run main DatagramChannelImplWitupTest
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.spi.SelectorProvider;
import sun.nio.ch.DatagramChannelImpl;

public class DatagramChannelImplWitupTest {
    public static void main(String[] args) throws Exception {
        testBindSuccess();
        testBindAlreadyBound();
    }

    private static void testBindSuccess() throws IOException {
        SelectorProvider sp = SelectorProvider.provider();
        DatagramChannelImpl channel = new DatagramChannelImpl(sp);
        InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 0);
        DatagramChannel returned = channel.bind(addr);
        if (returned != channel) {
            throw new AssertionError("bind should return this");
        }
    }

    private static void testBindAlreadyBound() throws IOException {
        SelectorProvider sp = SelectorProvider.provider();
        DatagramChannelImpl channel = new DatagramChannelImpl(sp);
        InetSocketAddress addr1 = new InetSocketAddress("127.0.0.1", 0);
        channel.bind(addr1);
        InetSocketAddress addr2 = new InetSocketAddress("127.0.0.1", 0);
        try {
            channel.bind(addr2);
            throw new AssertionError("Expected AlreadyBoundException");
        } catch (AlreadyBoundException e) {
            // expected
        }
    }
}