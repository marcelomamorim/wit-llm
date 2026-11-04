/*
 * @test
 * @summary Basic tests for ServerSocketChannelImpl.accept() method
 * @run main ServerSocketChannelImplWitupTest
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import sun.nio.ch.ServerSocketChannelImpl;

public class ServerSocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testAcceptReturnsNullWhenNotBound();
        testAcceptOnOpenBoundChannel();
    }

    // Test that accept() returns null if no connection is pending (simulate by not binding)
    private static void testAcceptReturnsNullWhenNotBound() throws IOException {
        SelectorProvider sp = SelectorProvider.provider();
        ServerSocketChannelImpl ssc = new ServerSocketChannelImpl(sp);
        // Do not bind the channel, accept should return null
        SocketChannel sc = ssc.accept();
        if (sc != null) {
            throw new AssertionError("accept() should return null when no connection is pending");
        }
        ssc.close();
    }

    // Test accept() on a bound ServerSocketChannelImpl
    private static void testAcceptOnOpenBoundChannel() throws IOException {
        SelectorProvider sp = SelectorProvider.provider();
        ServerSocketChannelImpl ssc = new ServerSocketChannelImpl(sp);
        ssc.bind(new InetSocketAddress(0)); // bind to any free port

        // Start a thread to connect to the server socket
        Thread connector = new Thread(new Runnable() {
            public void run() {
                try {
                    SocketAddress addr = ssc.getLocalAddress();
                    java.net.Socket socket = new java.net.Socket();
                    socket.connect(addr);
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        connector.start();

        SocketChannel sc = ssc.accept();
        if (sc == null) {
            throw new AssertionError("accept() should return a SocketChannel when connection is pending");
        }
        sc.close();
        ssc.close();

        try {
            connector.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
