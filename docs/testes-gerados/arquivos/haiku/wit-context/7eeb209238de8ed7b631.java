/*
 * @test
 * @summary getLocalAddress via public AsynchronousSocketChannel API: null when unbound, bound loopback address, ClosedChannelException when closed
 * @run main AsynchronousSocketChannelImplWitupTest
 */

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;

public class AsynchronousSocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        AsynchronousSocketChannel ch = AsynchronousSocketChannel.open();
        try {
            SocketAddress unbound = ch.getLocalAddress();
            if (unbound != null) {
                throw new RuntimeException("expected null local address for unbound channel but got " + unbound);
            }

            ch.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
            SocketAddress bound = ch.getLocalAddress();
            if (!(bound instanceof InetSocketAddress)) {
                throw new RuntimeException("expected InetSocketAddress but got " + bound);
            }
            InetSocketAddress isa = (InetSocketAddress) bound;
            if (isa.getPort() == 0) {
                throw new RuntimeException("expected bound port to be non-zero");
            }
            if (!isa.getAddress().isLoopbackAddress()) {
                throw new RuntimeException("expected loopback address but got " + isa.getAddress());
            }
        } finally {
            ch.close();
        }

        AsynchronousSocketChannel closed = AsynchronousSocketChannel.open();
        closed.close();
        try {
            closed.getLocalAddress();
            throw new RuntimeException("expected ClosedChannelException");
        } catch (ClosedChannelException expected) {
            // expected
        }
    }
}
