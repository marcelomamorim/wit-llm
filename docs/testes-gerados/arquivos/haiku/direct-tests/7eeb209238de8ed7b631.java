/*
 * @test
 * @summary Verifies the public observable behavior of AsynchronousSocketChannelImpl.getLocalAddress() for bound and closed channels
 * @run main AsynchronousSocketChannelImplWitupTest
 */

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;

public class AsynchronousSocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testLocalAddressAfterBind();
        testLocalAddressOnClosedChannelThrows();
    }

    static void testLocalAddressAfterBind() throws Exception {
        try (AsynchronousSocketChannel ch = AsynchronousSocketChannel.open()) {
            InetSocketAddress requested =
                new InetSocketAddress(InetAddress.getLoopbackAddress(), 0);
            ch.bind(requested);
            SocketAddress local = ch.getLocalAddress();
            if (local == null) {
                throw new RuntimeException("getLocalAddress returned null after bind");
            }
            if (!(local instanceof InetSocketAddress)) {
                throw new RuntimeException("Unexpected address type: " + local.getClass());
            }
            InetSocketAddress isa = (InetSocketAddress) local;
            if (isa.getPort() == 0) {
                throw new RuntimeException("Expected an assigned ephemeral port, got 0");
            }
            if (!isa.getAddress().isLoopbackAddress()) {
                throw new RuntimeException("Expected loopback address, got " + isa.getAddress());
            }
            if (!isa.getAddress().equals(InetAddress.getLoopbackAddress())) {
                throw new RuntimeException("Local address does not match bound loopback address");
            }
        }
    }

    static void testLocalAddressOnClosedChannelThrows() throws Exception {
        AsynchronousSocketChannel ch = AsynchronousSocketChannel.open();
        ch.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
        ch.close();
        try {
            ch.getLocalAddress();
            throw new RuntimeException("Expected ClosedChannelException");
        } catch (ClosedChannelException expected) {
            // expected: getLocalAddress checks isOpen() first
        }
    }
}
