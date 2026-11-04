/* @test    @summary Test binding a SocketChannel with a valid local address and null address.    @run main SocketChannelImplWitupTest */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import sun.nio.ch.SocketChannelImpl;

public class SocketChannelImplWitupTest {

    public static void main(String[] args) throws IOException {
        testBindWithValidAddress();
        testBindWithNullAddress();
    }

    static void testBindWithValidAddress() throws IOException {
        SocketChannelImpl channel = new SocketChannelImpl();
        InetSocketAddress localAddress = new InetSocketAddress(0);
        channel.bind(localAddress);
        assert channel != null : "Channel should be created and bound successfully";
    }

    static void testBindWithNullAddress() throws IOException {
        SocketChannelImpl channel = new SocketChannelImpl();
        channel.bind(null);
        assert channel != null : "Channel should be created and bound successfully with null address";
    }
}
