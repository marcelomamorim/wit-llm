/*
 * @test
 * @summary Test accept() method of ServerSocketChannelImpl for normal operation.
 * @run main ServerSocketChannelImplWitupTest
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import sun.nio.ch.ServerSocketChannelImpl;

public class ServerSocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testAcceptReturnsSocketChannel();
        testAcceptReturnsNullWhenClosed();
    }

    private static void testAcceptReturnsSocketChannel() throws Exception {
        ServerSocketChannel serverChannel = null;
        try {
            serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(0));
            // Simulate a client connection in a separate thread
            Thread clientThread = new Thread(() -> {
                try {
                    Thread.sleep(100);
                    try (java.net.Socket socket = new java.net.Socket()) {
                        socket.connect(serverChannel.getLocalAddress());
                    }
                } catch (Exception e) {
                    // ignore
                }
            });
            clientThread.start();
            SocketChannel sc = serverChannel.accept();
            assert sc != null : "accept() should return a SocketChannel";
            sc.close();
        } finally {
            if (serverChannel != null) {
                serverChannel.close();
            }
        }
    }

    private static void testAcceptReturnsNullWhenClosed() throws Exception {
        ServerSocketChannel serverChannel = null;
        try {
            serverChannel = ServerSocketChannel.open();
            serverChannel.close();
            SocketChannel sc = null;
            try {
                sc = serverChannel.accept();
            } catch (IOException e) {
                // Expected, as channel is closed
            }
            assert sc == null : "accept() on closed channel should return null or throw";
        } finally {
            if (serverChannel != null && serverChannel.isOpen()) {
                serverChannel.close();
            }
        }
    }
}
