/*
 * @test
 * @summary Verifies getLocalAddress on an open and a closed AsynchronousServerSocketChannel
 * @run main AsynchronousServerSocketChannelImplWitupTest
 */

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.ClosedChannelException;

public class AsynchronousServerSocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        AsynchronousServerSocketChannel ch = AsynchronousServerSocketChannel.open();
        try {
            ch.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));

            SocketAddress sa = ch.getLocalAddress();
            if (!(sa instanceof InetSocketAddress)) {
                throw new RuntimeException("local address is not InetSocketAddress: " + sa);
            }
            InetSocketAddress isa = (InetSocketAddress) sa;
            if (isa.getPort() == 0) {
                throw new RuntimeException("bound port should be non-zero");
            }
            if (!isa.getAddress().isLoopbackAddress()) {
                throw new RuntimeException("expected loopback address but got " + isa.getAddress());
            }

            SocketAddress again = ch.getLocalAddress();
            if (!isa.equals(again)) {
                throw new RuntimeException("local address changed between calls");
            }

            ch.close();

            try {
                ch.getLocalAddress();
                throw new RuntimeException("expected ClosedChannelException after close");
            } catch (ClosedChannelException expected) {
                // expected
            }
        } finally {
            ch.close();
        }
    }
}
